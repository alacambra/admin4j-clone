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
package net.admin4j.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import net.admin4j.exception.JdkLoggingExceptionHandler;
import net.admin4j.ui.servlets.LogLevelServlet;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.vo.LoggerVO;

/**
 * Jdk Implementation of a Log Manager.
 * @author M. Lyons
 *
 */
public class JdkLogManager implements LogManager {

    @SuppressWarnings("rawtypes")
    public List<LoggerVO> findLoggers() {
        List<LoggerVO> loggerVOs = new ArrayList<LoggerVO>();
        
        java.util.logging.LogManager lm = java.util.logging.LogManager.getLogManager();
        Enumeration loggerNames = lm.getLoggerNames();
        
        while (loggerNames.hasMoreElements())
        {
            String loggerName = (String)loggerNames.nextElement();
            java.util.logging.Logger logger = lm.getLogger(loggerName);
            String loggerLevel = LogLevelServlet.NONE;
            java.util.logging.Level level = logger.getLevel();
            if (level != null)
            {
                loggerLevel = level.toString();
            }
            LoggerVO loggerVO = new LoggerVO(loggerName, loggerLevel, this.getLoggerSoftwareProductName());
            loggerVOs.add(loggerVO);
        }
        Collections.sort(loggerVOs);
        
        return loggerVOs;
    }

    public String performLogLevelChange(String requestedLoggerName,
            LogIncrementType logIncrementType) {
        java.util.logging.LogManager lm = java.util.logging.LogManager.getLogManager();
        java.util.logging.Logger l = lm.getLogger(requestedLoggerName);
        if (l==null)
        {
            throw new Admin4jRuntimeException("Logger not found")
            .addContextValue("requestedLoggerName", requestedLoggerName);
        }

        java.util.logging.Level lvl = l.getLevel();
        if (LogIncrementType.SHOW_MORE.equals(logIncrementType))
        {
            if (lvl==null)
            {
                l.setLevel( java.util.logging.Level.INFO );
                return l.getLevel().toString();
            }
            else
            {
                l.setLevel( this.showMoreJdk14Logging(l.getLevel()) );
                return l.getLevel().toString();
            }
        }
        else if (LogIncrementType.SHOW_LESS.equals(logIncrementType))
        {
            if (lvl==null)
            {   
                l.setLevel( java.util.logging.Level.WARNING );
                return l.getLevel().toString();
            }
            else
            {
                l.setLevel( this.showLessJdk14Logging(l.getLevel()) );
                return l.getLevel().toString();
            }
        }
        else if (LogIncrementType.CLEAR.equals(logIncrementType))
        {
            l.setLevel( null );
            return "";
        }
        throw new Admin4jRuntimeException("Unsupported Log Increment Type")
        .addContextValue("logIncrementType", logIncrementType);
    }
    
    /**
     * The levels in descending order are:
     * <ul>
     * <li>OFF
     * <li>SEVERE (highest value)
     * <li>WARNING
     * <li>INFO
     * <li>CONFIG
     * <li>FINE
     * <li>FINER
     * <li>FINEST  (lowest value)
     * </ul>
     * @param lvl
     * @return
     */
    private java.util.logging.Level showLessJdk14Logging(java.util.logging.Level lvl) {
        if (java.util.logging.Level.OFF.equals(lvl)) return java.util.logging.Level.OFF;
        if (java.util.logging.Level.SEVERE.equals(lvl)) return java.util.logging.Level.OFF;
        if (java.util.logging.Level.WARNING.equals(lvl)) return java.util.logging.Level.SEVERE;
        if (java.util.logging.Level.INFO.equals(lvl)) return java.util.logging.Level.WARNING;
        if (java.util.logging.Level.CONFIG.equals(lvl)) return java.util.logging.Level.INFO;
        if (java.util.logging.Level.FINE.equals(lvl)) return java.util.logging.Level.CONFIG;
        if (java.util.logging.Level.FINER.equals(lvl)) return java.util.logging.Level.FINE;
        if (java.util.logging.Level.FINEST.equals(lvl)) return java.util.logging.Level.FINER;
        return java.util.logging.Level.INFO;
    }
    /**
     * The levels in descending order are:
     * <ul>
     * <li>OFF
     * <li>SEVERE (highest value)
     * <li>WARNING
     * <li>INFO
     * <li>CONFIG
     * <li>FINE
     * <li>FINER
     * <li>FINEST  (lowest value)
     * </ul>
     * @param lvl
     * @return
     */
    private java.util.logging.Level showMoreJdk14Logging(java.util.logging.Level lvl) {
        if (java.util.logging.Level.OFF.equals(lvl)) return java.util.logging.Level.SEVERE;
        if (java.util.logging.Level.SEVERE.equals(lvl)) return java.util.logging.Level.WARNING;
        if (java.util.logging.Level.WARNING.equals(lvl)) return java.util.logging.Level.INFO;
        if (java.util.logging.Level.INFO.equals(lvl)) return java.util.logging.Level.CONFIG;
        if (java.util.logging.Level.CONFIG.equals(lvl)) return java.util.logging.Level.FINE;
        if (java.util.logging.Level.FINE.equals(lvl)) return java.util.logging.Level.FINER;
        if (java.util.logging.Level.FINER.equals(lvl)) return java.util.logging.Level.FINEST;
        if (java.util.logging.Level.FINEST.equals(lvl)) return java.util.logging.Level.FINEST;
        return java.util.logging.Level.INFO;
    }

    public String getLoggerSoftwareProductName() {
        return "Jdk";
    }
    
    public void installExceptionTrackingLogAppender() {
        java.util.logging.LogManager.getLogManager().getLogger("").addHandler(new JdkLoggingExceptionHandler());
    }

}
