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
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.timer.TaskTimer;
import net.admin4j.timer.TaskTimerFactory;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.util.GuiUtils;
import net.admin4j.util.HostUtils;
import net.admin4j.util.ServletUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 * This filter allows you to enable performance monitoring.
 * Notification will be sent when a defined threshold limit is reached.
 * 
 * <p>Initialization parameters for this filter are as follows:</p>
 * <li>notification.threshold.in.millis - Optional.  If set, a notification will be issued 
 * should a page take longer than the specified number of milliseconds.  For this
 * option to work, values must be provided for notifier</li>
 * <li>notifier -- Optional.  Handles admin notification.  See documentation for the Notifier you're using
 * for any additional configuration requirements.  For example, 'net.admin4j.util.notify.EmailNotifier'.</li>
 * 
 * @author D. Ashmore
 * @since 1.0
 */
public class PerformanceMonitoringFilter extends BaseNotificationFilter implements Filter {
    
    private long notificationThresholdInMillis = Long.MAX_VALUE;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		TaskTimer perfMonitor = null;
		HttpServletRequest httpRequest = null;
		String serviceName  = null;
		long beginTime = System.currentTimeMillis();
		
		try {
			
		    /*
             * Under no circumstances should an Admin4J error 'mask' the underlying error for an application.
             * Hence, all Admin4J work is surrounded by a try/catch making sure to throw the underlying
             * exception up the chain.
             */
		    try {
    			httpRequest = (HttpServletRequest)request;
    			serviceName = httpRequest.getRequestURI();			
    			perfMonitor = TaskTimerFactory.start(serviceName) ;
		    }
		    catch (Throwable fubar) {
                logger.error("Error starting performance monitor.", fubar);
            }
			
			filterChain.doFilter(request, response);
			try {
    			long durationInMillis = System.currentTimeMillis() - beginTime;
    			double durationInSeconds = durationInMillis / 1000;
    			
    			if (durationInMillis > notificationThresholdInMillis) {
    			    Configuration cfg = new Configuration();
    	            cfg.setClassForTemplateLoading(ErrorNotificationFilter.class, "");
    	            
    	            Map<String,Object> variableMap = new HashMap<String,Object>();
    	            variableMap.put("request", httpRequest);
    	            variableMap.put("GuiUtils", new GuiUtils());
    	            variableMap.put("host", HostUtils.getHostName());
    	            variableMap.put("timeInSeconds", durationInSeconds);
    	            variableMap.put("sessionAttributeList", ServletUtils.listSessionAttributes(httpRequest.getSession()));
    	            variableMap.put("requestAttributeList", ServletUtils.listRequestAttributes(httpRequest));
    	            
    	            Template temp = null;
    	            if (this.notifier.supportsHtml()) {
    	                temp = FreemarkerUtils.createConfiguredTemplate(this.getClass(), "slowPerformanceNotificationMessageHtml.ftl");
    	            }
    	            else {
    	                temp = FreemarkerUtils.createConfiguredTemplate(this.getClass(), "slowPerformanceNotificationMessageText.ftl");
    	            }
    	            
    	            StringWriter message = new StringWriter();
    	            try {temp.process(variableMap, message);}
    	            catch (Exception e) {
    	                throw new Admin4jRuntimeException(e);
    	            }
    	            
    	            String subject = ServletUtils.getApplicationName(httpRequest) + 
    	                    "@" + HostUtils.getHostName() + ": " + 
    	                    httpRequest.getRequestURI() + ": " + "Response time over " + notificationThresholdInMillis + " ms";
    	                this.notifier.notify(subject, message.toString());
    			}
			}
			catch (Throwable fubar) {
			    logger.error("Error providing performance notification.", fubar);
			}

        } catch (ServletException se) {
        	throw se;
        } catch (Throwable ex) {
            throw new ServletException(ex);
        }
		finally {
			if (perfMonitor != null) perfMonitor.stop();
		}		
	}
	
	public void destroy() {	}

    /* (non-Javadoc)
     * @see net.admin4j.ui.filters.BaseNotificationFilter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        
        String notifyThresholdInMillisStr = config.getInitParameter("notification.threshold.in.millis");
        if (!StringUtils.isEmpty(notifyThresholdInMillisStr)) {
            try {
                this.notificationThresholdInMillis = Long.parseLong(notifyThresholdInMillisStr);
            } catch (NumberFormatException e) {
                this.notificationThresholdInMillis = Long.MAX_VALUE;
                throw new Admin4jRuntimeException("notification.threshold.in.millis secification not numeric", e)
                    .addContextValue("notification.threshold.in.millis", notifyThresholdInMillisStr);
                
            }
        }
        else if (Admin4JConfiguration.getWebTransactionPerformanceNotificationThresholdInMillis() != null) {
            this.notificationThresholdInMillis = Admin4JConfiguration.getWebTransactionPerformanceNotificationThresholdInMillis();
        }
    }	
	
}

