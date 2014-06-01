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

import net.admin4j.exception.Log4JExceptionAppender;
import net.admin4j.ui.servlets.LogLevelServlet;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.annotate.Product;
import net.admin4j.util.annotate.ProductDependencies;
import net.admin4j.vo.LoggerVO;

/**
 * Log4J Implementation of a Log Manager.
 * @author M. Lyons
 *
 */
@ProductDependencies( {Product.LOG4J} )
public class Log4JLogManager implements LogManager {

    @SuppressWarnings("rawtypes")
    public List<LoggerVO> findLoggers() {
        List<LoggerVO> loggerVOs = new ArrayList<LoggerVO>();
        
        String rootName = org.apache.log4j.LogManager.getRootLogger().getName();
        String rootLevel = org.apache.log4j.LogManager.getRootLogger().getLevel().toString();
        LoggerVO rootLoggerVO = new LoggerVO(rootName, rootLevel, this.getLoggerSoftwareProductName());
        
        org.apache.log4j.spi.LoggerRepository lr = org.apache.log4j.LogManager.getLoggerRepository();
        Enumeration loggers = lr.getCurrentLoggers();

        while (loggers.hasMoreElements())
        {
            Object o = loggers.nextElement();
            org.apache.log4j.Logger logger = (org.apache.log4j.Logger)o; 
            String loggerName = logger.getName();
            String loggerLevel = LogLevelServlet.NONE;
            org.apache.log4j.Level level = logger.getLevel();
            if (level != null)
            {
                loggerLevel = level.toString();
            }
            LoggerVO loggerVO = new LoggerVO(loggerName, loggerLevel, this.getLoggerSoftwareProductName());
            loggerVOs.add(loggerVO);
        }
        Collections.sort(loggerVOs);
        loggerVOs.add(0, rootLoggerVO);
        
        return loggerVOs;
    }

    public String performLogLevelChange(String requestedLoggerName,
            LogIncrementType logIncrementType) {
        org.apache.log4j.Logger l = org.apache.log4j.LogManager.getLoggerRepository().getLogger(requestedLoggerName);
        if (l==null)
        {
            throw new Admin4jRuntimeException("Logger not found")
            .addContextValue("requestedLoggerName", requestedLoggerName);
        }

        org.apache.log4j.Level lvl = l.getLevel();
        if (LogIncrementType.SHOW_MORE.equals(logIncrementType))
        {
            if (lvl==null)
            {   
                l.setLevel( org.apache.log4j.Level.INFO );
                return l.getLevel().toString();
            }
            else
            {
                l.setLevel( showMoreLog4JLogging(l.getLevel()) );
                return l.getLevel().toString();
            }
        }
        else if (LogIncrementType.SHOW_LESS.equals(logIncrementType))
        {
            if (lvl==null)
            {   
                l.setLevel( org.apache.log4j.Level.WARN );
                return l.getLevel().toString();
            }
            else
            {
                l.setLevel( showLessLog4JLogging(l.getLevel()) );
                return l.getLevel().toString();
            }
        }
        else if (LogIncrementType.CLEAR.equals(logIncrementType))
        {
            l.setLevel( null );
            return "";
        }
        else {
            throw new Admin4jRuntimeException("Unsupported Log Increment Type")
                .addContextValue("logIncrementType", logIncrementType);
        }
        
    }
    
    public void installExceptionTrackingLogAppender() {
        org.apache.log4j.LogManager.getRootLogger().addAppender(new Log4JExceptionAppender());
    }
    
    private static org.apache.log4j.Level showLessLog4JLogging(org.apache.log4j.Level lvl) {
        if (org.apache.log4j.Level.OFF.equals(lvl)) return org.apache.log4j.Level.OFF;
        if (org.apache.log4j.Level.FATAL.equals(lvl)) return org.apache.log4j.Level.OFF;
        if (org.apache.log4j.Level.ERROR.equals(lvl)) return org.apache.log4j.Level.FATAL;
        if (org.apache.log4j.Level.WARN.equals(lvl)) return org.apache.log4j.Level.ERROR;
        if (org.apache.log4j.Level.INFO.equals(lvl)) return org.apache.log4j.Level.WARN;
        if (org.apache.log4j.Level.DEBUG.equals(lvl)) return org.apache.log4j.Level.INFO;
        if (org.apache.log4j.Level.TRACE.equals(lvl)) return org.apache.log4j.Level.DEBUG;
        if (org.apache.log4j.Level.ALL.equals(lvl)) return org.apache.log4j.Level.TRACE;
        return org.apache.log4j.Level.INFO;
    }
    
    private static org.apache.log4j.Level showMoreLog4JLogging(org.apache.log4j.Level lvl) {
        if (org.apache.log4j.Level.ALL.equals(lvl)) return org.apache.log4j.Level.ALL;
        if (org.apache.log4j.Level.TRACE.equals(lvl)) return org.apache.log4j.Level.ALL;
        if (org.apache.log4j.Level.DEBUG.equals(lvl)) return org.apache.log4j.Level.TRACE;
        if (org.apache.log4j.Level.INFO.equals(lvl)) return org.apache.log4j.Level.DEBUG;
        if (org.apache.log4j.Level.WARN.equals(lvl)) return org.apache.log4j.Level.INFO;
        if (org.apache.log4j.Level.ERROR.equals(lvl)) return org.apache.log4j.Level.WARN;
        if (org.apache.log4j.Level.FATAL.equals(lvl)) return org.apache.log4j.Level.ERROR;
        if (org.apache.log4j.Level.OFF.equals(lvl)) return org.apache.log4j.Level.FATAL;
        return org.apache.log4j.Level.INFO;
    }

    /* (non-Javadoc)
     * @see net.admin4j.log.LogManager#getLoggerSoftwareProductName()
     */
    public String getLoggerSoftwareProductName() {
        return "Log4J";
    }

}
