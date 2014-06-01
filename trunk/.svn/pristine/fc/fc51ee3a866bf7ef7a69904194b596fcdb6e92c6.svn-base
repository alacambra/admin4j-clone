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

import java.util.HashSet;
import java.util.Set;

import net.admin4j.config.Admin4JConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides all available LogManagers.
 * @author D. Ashmore
 *
 */
@SuppressWarnings("rawtypes")
public class LogManagerRegistry {
    
    private static Logger logger = LoggerFactory.getLogger(LogManagerRegistry.class);
    private static final Set<LogManager> availableLogManagerSet = new HashSet<LogManager>();
    
    static {
        try {
            Class log4JLogManager = Class.forName("net.admin4j.log.Log4JLogManager");
            availableLogManagerSet.add( (LogManager)log4JLogManager.newInstance());
        } catch (ClassNotFoundException e) {
            logger.info("Log4J Not Found in classpath -- Log4J exceptions not viewable within Admin4J.");
        } catch (Throwable e) {
            logger.error("Error instantiating Log4JLogManager", e);
        }
        
        availableLogManagerSet.add(new JdkLogManager());
        
        for (String logManagerClassName: Admin4JConfiguration.getAdditionalLogManagerClassNames()) {
            try {
                Class logManager = Class.forName(logManagerClassName);
                availableLogManagerSet.add( (LogManager)logManager.newInstance());
            } catch (ClassNotFoundException e) {
                logger.info("Additional LogManager Not Found in classpath -- these exceptions not viewable within Admin4J.");
            } catch (Throwable e) {
                logger.error("Error instantiating additional LogManager", e);
            }
        }
    }

    public static Set<LogManager> getAvailableLogManagerSet() {
        return availableLogManagerSet;
    }

}
