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
import java.util.Set;

import junit.framework.Assert;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.log.LogManager.LogIncrementType;
import net.admin4j.vo.LoggerVO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogManager {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        Set<LogManager> managerSet = LogManagerRegistry.getAvailableLogManagerSet();
        Assert.assertTrue("Log Manager existence test", managerSet != null && managerSet.size() >= 2);
        
        for (LogManager manager: managerSet) {
            Assert.assertTrue("Log Manager existence test", !StringUtils.isEmpty(manager.getLoggerSoftwareProductName()));
            
            manager.installExceptionTrackingLogAppender(); // Make sure it doesn't blow up.
            
            List<LoggerVO> loggerList = manager.findLoggers();
            Assert.assertTrue("Logger existence test", loggerList != null && loggerList.size() > 0);
            
            boolean expectBlowUp = manager instanceof JdkLogManager;
            try {
                manager.performLogLevelChange("Doesn't exist", LogIncrementType.CLEAR);
                
                if (expectBlowUp) {
                    Assert.fail("should have blown up.");
                }
            }
            catch (Exception e) {
                if (!expectBlowUp) {
                    Assert.fail("should not have blown up.");
                }
            }
            
            String logLevel = manager.performLogLevelChange(loggerList.get(0).getLoggerName(), LogIncrementType.SHOW_MORE);
            Assert.assertTrue("logLevel after show more", !StringUtils.isEmpty(logLevel));
            
            logLevel = manager.performLogLevelChange(loggerList.get(0).getLoggerName(), LogIncrementType.SHOW_LESS);
            Assert.assertTrue("logLevel after show more", !StringUtils.isEmpty(logLevel));
            
            logLevel = manager.performLogLevelChange(loggerList.get(0).getLoggerName(), LogIncrementType.CLEAR);
            Assert.assertTrue("logLevel after show more", StringUtils.isEmpty(logLevel));
        }
    }

}
