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
package net.admin4j.ui.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.beanutils.BeanComparator;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.entity.ExceptionInfo;
import net.admin4j.exception.ExceptionTracker;
import net.admin4j.log.LogManager;
import net.admin4j.log.LogManagerRegistry;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.util.GuiUtils;
import net.admin4j.util.HostUtils;
import net.admin4j.util.ServletUtils;
import net.admin4j.vo.ExceptionStatisticVO;

/**
 * Provides a browser for exception statistics intercepted by the Exception Tracker.  By default,
 * exceptions logged by Java Util Logging and Log4J are automatically tracked if they are present in the classpath.
 * 
 *  <p>Should you use other logging frameworks, you can have those exceptions tracked by writingand installing a custom
 *  appender for that framework.  See <a href="../../exception/Log4JExceptionAppender.html">Log4JExceptionAppender</a>
 *  for an example.</p>
 *  
 *  <p>You will need to map this servlet to an url pattern (e.g. '/admin4j/error').</p>
 *  <p>This servlet does <b>not</b> require other web resources.</p>
 *  
 *  <p>Init parameters for this servlet are as follows:</p>
 *  <li>tracking.time.in.days -- Optional.  Default 30 days.  Amount of time an exception will be tracked and reportable.</li>
 *  <li>exempted.exception.types -- Optional.  Comma delimited list of exception class names that will *not* be tracked.</li>
 * @author D. Ashmore
 * @see net.admin4j.exception.ExceptionTracker
 */
public class ExceptionDisplayServlet extends AdminDisplayServlet {

    private static final long serialVersionUID = -2262926722396897223L;
    
    public static final String PUBLIC_HANDLE="exception";

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       for (LogManager logManager: LogManagerRegistry.getAvailableLogManagerSet()) {
           try {
               logManager.installExceptionTrackingLogAppender();
               logger.info("Exception tracking for {} installed.", logManager.getLoggerSoftwareProductName());
           }
           catch (Throwable t) {
               logger.error("Exception tracking for {} installed.", 
                       new Admin4jRuntimeException(t).addContextValue("log manager", logManager.getLoggerSoftwareProductName()));
           }
       }
       
       String trackingTimeStr = ServletUtils.getConfigurationSetting(
               new String[]{PUBLIC_HANDLE + ".tracking.time.in.days", 
               "tracking.time.in.days"}, config);
       Integer trackingTimeInDays;
       if ( !StringUtils.isEmpty(trackingTimeStr)) {
           try {
               trackingTimeInDays = new Integer(trackingTimeStr.trim());
               ExceptionTracker.setPurgeThresholdInDays(trackingTimeInDays);
           }
           catch (Throwable t) {
               this.logger.error("Invalid tracking.time.in.millis parameter for ExceptionDisplayServlet.  Default used.  tracking.time.in.millis="+ trackingTimeStr, 
                       t);
           }
       }
       else if (Admin4JConfiguration.getExceptionTimeTrackingInDays() != null) {
           ExceptionTracker.setPurgeThresholdInDays(Admin4JConfiguration.getExceptionTimeTrackingInDays());
       }
       
       String exemptedExceptionStr = ServletUtils.getConfigurationSetting(
               new String[]{PUBLIC_HANDLE + ".exempted.exception.types", 
               "exempted.exception.types"}, config);
       if ( !StringUtils.isEmpty(exemptedExceptionStr)) {
           registerExceptedExceptionTypes(exemptedExceptionStr);
       }
       else if ( !StringUtils.isEmpty(Admin4JConfiguration.getExceptionExemptedExceptionTypes() )) {
           registerExceptedExceptionTypes(Admin4JConfiguration.getExceptionExemptedExceptionTypes());
       }
    }

    private void registerExceptedExceptionTypes(String exemptedExceptionStr) {
        try {
               StringTokenizer tok = new StringTokenizer(exemptedExceptionStr, ",");
               String exemptedClassName;
               while (tok.hasMoreTokens()) {
                   exemptedClassName = tok.nextToken();
                   if (!StringUtils.isEmpty(exemptedClassName)) {
                       ExceptionTracker.addExemptedExceptionClassName(exemptedClassName);
                       this.logger.info("Exceptions of type {} will not be tracked.", exemptedClassName);
                   }
               }
           }
           catch (Throwable t) {
               this.logger.error("Invalid exempted.exception.types parameter for ExceptionDisplayServlet.  parm=" + exemptedExceptionStr, 
                       t);
           }
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String actionStr = request.getParameter("action");
        if ( !StringUtils.isEmpty(actionStr)) {
//            if ("save".equalsIgnoreCase(actionStr)) {
//                ExceptionTracker.recordUrl(request.getParameter("exceptionId"), request.getParameter("exceptionUrl"));
//            }
            if ("delete".equalsIgnoreCase(actionStr)) {
                ExceptionTracker.deleteException(request.getParameter("exceptionId"));
            }
            if ("text".equalsIgnoreCase(actionStr)) {
                ExceptionInfo eInfo = ExceptionTracker.lookupException(request.getParameter("exceptionId"));
                
                response.setContentType("application/octet-stream");                 
                response.setHeader("Content-disposition", "attachment; filename=\"exception.txt\"");
                
                Map<String,Object> variableMap = new HashMap<String,Object>();
                variableMap.put("exception", eInfo);
                variableMap.put("host", HostUtils.getHostName());
                variableMap.put("GuiUtils", new GuiUtils());
                FreemarkerUtils.addConfiguration(variableMap);
                this.displayFreeMarkerResponse(request, response, "exceptionDownload.ftl", variableMap);
                
                return;
            }
        }
        
        Set<ExceptionStatisticVO> exceptionSet = ExceptionTracker.getExceptionStatisticSet();
        
        List<ExceptionStatisticVO> exceptionList = new ArrayList<ExceptionStatisticVO>(exceptionSet);
        Collections.sort(exceptionList, new BeanComparator("totalNbrExceptions"));
        Collections.reverse(exceptionList);
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("exceptionList", exceptionList);
        variableMap.put("GuiUtils", new GuiUtils());
        FreemarkerUtils.addConfiguration(variableMap);
        
        displayFreeMarkerPage(request, response, "exceptionDisplayServletDisplay.ftl", variableMap);
    }

    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "Exception Summary";
    }


}
