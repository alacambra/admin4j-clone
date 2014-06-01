/*
 * This software is licensed under the Apache License, Version 2.0
 * (the "License") agreement; you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.admin4j.ui.filters;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.deps.commons.lang3.exception.ExceptionUtils;
import net.admin4j.entity.ExceptionInfo;
import net.admin4j.entity.ExceptionInfoBase;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.ExpiringCache;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.util.GuiUtils;
import net.admin4j.util.HostUtils;
import net.admin4j.util.ServletUtils;
import net.admin4j.util.notify.LogNotifier;
import net.admin4j.vo.HttpRequestVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;

/**
 * Emails exceptions to a support email group.  
 * 
 * <p>This filter should be defined before all other filters (so it executes first/last).</p>
 * <p>Init parameters for this filter are as follows:</p>
 * <li>notifier -- Required.  Handles admin notification.  Can use the default Notifier setting.  
 * See documentation for the Notifier you're using
 * for any additional configuration requirements.  
 * For example, 'net.admin4j.util.notify.EmailNotifier'.</li>
 * <li>notification.time.interval.millis -- Optional.  If specified, notification 
 * will be provided for <i>like</i> errors once per specified time period.  For 
 * instance, if the interval is set to 10 minutes (600000), administrators will be notified 
 * once every 10 minutes for a Null Pointer Exception occurring on line 67 of class Foo for an http request.</li>
 * <li>exempted.exception.types -- Optional.  Comma delimited list of exception class names for which notification will *not* occur.</li>
 * <p>By default, all exceptions will be notified.</p>
 * @author D. Ashmore
 * @since 1.0
 */
public class ErrorNotificationFilter extends BaseNotificationFilter implements Filter {
    
    private ExpiringCache exceptionCache = null;
    private static Map<String,String> exemptedExceptionClassNames = new ConcurrentHashMap<String, String>();
    private Logger configuredLogger = null;
	
	public void destroy() {
		// NoOp

	}
	
    /* (non-Javadoc)
     * @see net.admin4j.ui.filters.BaseNotificationFilter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        
        if (StringUtils.isEmpty(Admin4JConfiguration.getWebTransactionErrorLoggerName())) {
            configuredLogger = logger;
        }
        else configuredLogger = LoggerFactory.getLogger(Admin4JConfiguration.getWebTransactionErrorLoggerName());
        
        Long timeInterval = null;
        String timeIntervalStr = config.getInitParameter("notification.time.interval.millis");      
        if ( !StringUtils.isEmpty(timeIntervalStr)) {            
            try {
                timeInterval = Long.parseLong(timeIntervalStr);
            } catch (NumberFormatException e) {
                logger.warn("notification.time.interval.millis not numeric.  All errors will result in notification.", e);
            }            
        }
        else timeInterval = Admin4JConfiguration.getErrorNotificationTimeIntervalMillis();
        
        if (timeInterval != null) {
            exceptionCache = new ExpiringCache(timeInterval, timeInterval * 2);
        }
        else {
            logger.info("All web application errors will result in notification.");
        }
        
        if (exceptionCache != null) {
            logger.info("Error Notification for Http Requests will occur once every {} ms for each type of error received.", exceptionCache.getExpirationTimeInMillis());
        }
        else logger.info("Error Notification for Http Requests enabled.");
        
        String exemptedExceptionStr = config.getInitParameter("exempted.exception.types");
        String errorMessage = "Invalid exempted.exception.types parameter for ErrorNotificationFilter.  parm=";
        if (StringUtils.isEmpty(exemptedExceptionStr)) {
            exemptedExceptionStr = Admin4JConfiguration.getErrorExemptedExceptionTypes();
            errorMessage = "Configuration item error.exempted.exception.types invalid.  item=";
        }
        
        if ( !StringUtils.isEmpty(exemptedExceptionStr)) {
            try {
                StringTokenizer tok = new StringTokenizer(exemptedExceptionStr, ",");
                String exemptedClassName;
                while (tok.hasMoreTokens()) {
                    exemptedClassName = tok.nextToken();
                    if (!StringUtils.isEmpty(exemptedClassName)) {
                        exemptedExceptionClassNames.put(exemptedClassName, exemptedClassName);
                        this.logger.info("Exceptions of type {} will not be notified.", exemptedClassName);
                    }
                }
            }
            catch (Throwable t) {
                this.logger.error( errorMessage + exemptedExceptionStr, 
                        t);
            }
        }
    }

	@SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} 
		catch (Throwable t) {
		    
		    boolean causeRethrownIntentionally = false;
		    boolean notificationProvided = false;
			
		    /*
		     * Under no circumstances should an Admin4J error 'mask' the underlying error for an application.
		     * Hence, all Admin4J work is surrounded by a try/catch making sure to throw the underlying
		     * exception up the chain.
		     */
		    try {
    			Throwable rootCause = ExceptionUtils.getRootCause(t);
    			if (rootCause == null) {
    			    rootCause = t;
    			}
    			Throwable reportedCause = t;
    			
    			logger.debug("Root Cause in Error Notification Filter", rootCause);
    
    			if (exemptedExceptionClassNames.containsKey(rootCause.getClass().getName()))
    			{
    				logger.debug( "Exception type exempted.  No notification given", rootCause );
    				causeRethrownIntentionally = true;
    				ServletUtils.reThrowServletFilterException(t);
    			}
    			
    			if (exceptionCache != null) {
    			    synchronized (exceptionCache) {
        			    ExceptionInfoBase eInfo = new ExceptionInfo(rootCause.getClass().getName(), rootCause.getStackTrace());
        			    Long count = (Long) exceptionCache.get(eInfo);
        			    if (count == null) {
        			        exceptionCache.put(eInfo, Long.valueOf(1L));
        			    }
        			    else { // Notify already happened --> return.
        			        exceptionCache.put(eInfo, Long.valueOf(count + 1));
        			        causeRethrownIntentionally = true;
        			        ServletUtils.reThrowServletFilterException(t);
        			    }
    			    }
    			}
    			
    			HttpServletRequest httpRequest = (HttpServletRequest)request;
    			 	        
    	        Map<String,Object> variableMap = new HashMap<String,Object>();
    	        variableMap.put("request", httpRequest);
    	        variableMap.put("errorType", rootCause.getClass().getName());
    	        if (rootCause.getMessage() == null) {
    	            variableMap.put("errorMessage", "null");
    	        }
    	        else variableMap.put("errorMessage", "'" + rootCause.getMessage() + "'");
    	        
    	        if (exceptionCache == null) {
    	            variableMap.put("suppressionIntervalMillis", Long.valueOf(0));
    	        }
    	        else variableMap.put("suppressionIntervalMillis", Long.valueOf(this.exceptionCache.getExpirationTimeInMillis()));
    	        
    	        List<HttpRequestVO> requestHistoryList = 
    	                (List<HttpRequestVO>)httpRequest.getSession().getAttribute(
                        RequestTrackingFilter.REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME);

    	        variableMap.put("GuiUtils", new GuiUtils());
    	        variableMap.put("host", HostUtils.getHostName());
    	        variableMap.put("requestHistory", requestHistoryList);
    	        variableMap.put("requestHistoryAttribute", 
    	                RequestTrackingFilter.REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME);
    	        variableMap.put("rootCauseTrace", ExceptionUtils.getStackTrace(rootCause));
    	        variableMap.put("reportedCauseTrace", ExceptionUtils.getStackTrace(reportedCause));
    	        variableMap.put("sessionAttributeList", ServletUtils.listSessionAttributes(httpRequest.getSession()));
    	        variableMap.put("requestAttributeList", ServletUtils.listRequestAttributes(httpRequest));
    	        
    	        Template temp = null;
    	        if (this.notifier.supportsHtml()) {
    	            temp = FreemarkerUtils.createConfiguredTemplate(this.getClass(), "errorNotificationFilterHtml.ftl");
    	        }
    	        else {
    	            temp = FreemarkerUtils.createConfiguredTemplate(this.getClass(), "errorNotificationFilterText.ftl");
    	        }
    	        
    	        StringWriter message = new StringWriter();
    	        try {temp.process(variableMap, message);}
    	        catch (Exception e) {
    	            throw new Admin4jRuntimeException(e);
    	        }
    
    			String errorMsg = null;
    			if (rootCause.getMessage() != null) {
    				errorMsg = rootCause.getMessage().replace('\n', '-');
    			}
    			
    			String subject = ServletUtils.getApplicationName(httpRequest) + 
    				"@" + HostUtils.getHostName() + ": " + 
    				rootCause.getClass() + ": " + errorMsg;
    			this.notifier.notify(subject, message.toString());
    			notificationProvided = true;
		    }
		    catch (Throwable c) {
		        if (!causeRethrownIntentionally) {
		            logger.error("Error occured while providing error notification", c);
		        }
		    }
		    finally {
		        if (!notificationProvided || (notificationProvided && !(this.notifier instanceof LogNotifier))) {
		            configuredLogger.error("Error occured with web transaction", t);
		        }
		        ServletUtils.reThrowServletFilterException(t);
		    }
		}

	}

}
