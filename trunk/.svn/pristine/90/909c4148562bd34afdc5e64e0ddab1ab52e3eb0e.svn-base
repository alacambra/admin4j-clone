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
package net.admin4j.monitor;

import static org.junit.Assert.assertTrue;
import net.admin4j.util.notify.NotifierTestingMock;
import net.admin4j.vo.MemoryUtilizationVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLowMemoryDetector {
    
    private LowMemoryDetector detector;
    private MemoryUtilizationVO memoryVo;
    private NotifierTestingMock notifier;

    @Before
    public void setUp() throws Exception {
        
        
        notifier = new NotifierTestingMock();
        notifier.setSupportsHtml(true);
        detector = new LowMemoryDetector(notifier);
        
        detector.setStartupPeriodInMillis(0);
        detector.setMemoryThresholdPct(1);
        detector.setNbrIntervalsBetweenWarnings(0);
        detector.setLowWatermarkMonitorIntervalInMillis(5);
        
        memoryVo = new MemoryUtilizationVO(5177344, 3374784, 66650112);
        
        for (int i = 0; i < 10; i++) {
            detector.watchLowWatermark(memoryVo);
            Thread.sleep(10);
        }
 
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSendLowMemoryNotice() {
        // Html format output
        detector.sendLowMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        // Text format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(false);
        detector.sendLowMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        // SMS format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(true);
        detector.sendLowMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
    }

    @Test
    public void testSendAllClearMemoryNotice() {
        // Html format output
        detector.sendAllClearMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        // Text format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(false);
        detector.sendAllClearMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        // SMS format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(true);
        detector.sendAllClearMemoryNotice(memoryVo);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
    }
    
    @Test
    public void testAbilityToDetectLowMemory() throws Exception {
        
        notifier.setSupportsHtml(false);
        
        detector.checkMemory(memoryVo);
        
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        assertTrue("Detect memory test 1", notifier.getMessage() != null);
        
    }

}
