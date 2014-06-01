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

import junit.framework.Assert;
import net.admin4j.util.notify.NotifierTestingMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConcurrentUsageDetector {
    
    private ConcurrentUsageDetector detector;
    private NotifierTestingMock notifier;

    @Before
    public void setUp() throws Exception {
        notifier = new NotifierTestingMock();
        notifier.setSupportsHtml(true);
        
        detector = new ConcurrentUsageDetector(notifier, new Integer[]{3,2,1});
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testHtml() throws Exception {
        notifier.setSupportsHtml(true);
        this.testLocal();
    }
    
    @Test
    public void testText() throws Exception {
        notifier.setSupportsHtml(false);
        this.testLocal();
    }
    
    @Test
    public void testSMS() throws Exception {
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(true);
        this.testLocal();
    }

    public void testLocal() throws Exception {
        detector.run();
        
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.increment();  // Usage = 1
        Assert.assertTrue("Usage level 1", detector.getCurrentUsageLevel() == 1);
        detector.run();
        Assert.assertTrue("Notification test", notifier.getMessage() != null && notifier.getSubject() != null);
        System.out.println(notifier.getSubject());
        System.out.println(notifier.getMessage());
        notifier.reset();
        
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.increment();  // Usage = 2
        Assert.assertTrue("Usage level 2", detector.getCurrentUsageLevel() == 2);
        detector.run();
        Assert.assertTrue("Notification test", notifier.getMessage() != null && notifier.getSubject() != null);
        notifier.reset();
        
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.increment();  // Usage = 3
        Assert.assertTrue("Usage level 3", detector.getCurrentUsageLevel() == 3);
        detector.run();
        Assert.assertTrue("Notification test", notifier.getMessage() != null && notifier.getSubject() != null);
        notifier.reset();
        
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.decrement();  // Usage = 2
        Assert.assertTrue("Usage level 2", detector.getCurrentUsageLevel() == 2);
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.decrement();  // Usage = 1
        Assert.assertTrue("Usage level 1", detector.getCurrentUsageLevel() == 1);
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
        
        detector.decrement();  // Usage = 0
        Assert.assertTrue("Usage level 0", detector.getCurrentUsageLevel() == 0);
        detector.run();
        Assert.assertTrue("Notification test", notifier.getMessage() != null && notifier.getSubject() != null);
        notifier.reset();
        
        detector.run();
        Assert.assertTrue("No notification test", notifier.getMessage() == null && notifier.getSubject() == null);
    }

}
