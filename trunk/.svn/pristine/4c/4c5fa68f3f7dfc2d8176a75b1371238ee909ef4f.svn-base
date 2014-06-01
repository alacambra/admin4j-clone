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

import java.util.List;

import net.admin4j.vo.LoggerVO;

/**
 * Log Managers are handle interactions with a specific logging software
 * product including listing available loggers and changing log levels at runtime.
 * @author D. Ashmore
 *
 */
public interface LogManager {
    
    public enum LogIncrementType {SHOW_MORE, SHOW_LESS, CLEAR};
    
    /**
     * Provides a list of available loggers pertaining to a specific logging
     * software product.
     * @return Log list
     */
    public List<LoggerVO> findLoggers();
    
    /**
     * Performs a runtime logger change returning the new logger state.
     * @param requestedLoggerName
     * @param type
     * @return
     */
    public String performLogLevelChange(String requestedLoggerName, LogIncrementType logIncrementType);
    
    /**
     * Returns a label used to describe the Logging software product.
     * @return
     */
    public String getLoggerSoftwareProductName();
    
    /**
     *  Will install a Log appender that will track any exceptions that are logged via the ExceptionTracker.
     *  @see net.admin4j.exception.ExceptionTracker
     */
    public void installExceptionTrackingLogAppender();

}
