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

import java.lang.management.ThreadInfo;
import java.util.HashSet;
import java.util.Set;

import net.admin4j.util.Daemon;
import net.admin4j.util.notify.NotifierTestingMock;
import net.admin4j.util.threaddumper.ThreadDumperFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestBlockedThreadDetector {
    
    private static final int TEST_BLOCKED_THRESHOLD = 2;
    private BlockedThreadDetector detector;
    private NotifierTestingMock notifier;

    @Before
    public void setUp() throws Exception {
        
        notifier = new NotifierTestingMock();
        notifier.setSupportsHtml(true);
        detector = new BlockedThreadDetector(notifier, TEST_BLOCKED_THRESHOLD);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSendBlockedThreadNotice() {
        ThreadInfo[] tInfo = ThreadDumperFactory.getThreadDumper().dumpAllThreads();
        Set<ThreadInfo> threadSet = new HashSet<ThreadInfo>();
        
        for (ThreadInfo thread: tInfo) {
            threadSet.add(thread);
        }
        
     // Html format output
        notifier.setSupportsHtml(true);
        notifier.setSupportsSMS(false);
        detector.sendBlockedThreadNotice(threadSet, threadSet, threadSet);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        assertTrue("Html Format send test", notifier.getMessage() != null);
        
     // Text format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(false);
        detector.sendBlockedThreadNotice(threadSet, threadSet, threadSet);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        assertTrue("Text Format send test", notifier.getMessage() != null);
        
     // SMS format output
        notifier.setSupportsHtml(false);
        notifier.setSupportsSMS(true);
        detector.sendBlockedThreadNotice(threadSet, threadSet, threadSet);
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        assertTrue("SMS Format send test", notifier.getMessage() != null);
    }
    
    @Test
    public void testAbilitytoDetectBlock() throws Exception {
        ThreadProblem problem1 = new ThreadProblem();
        ThreadProblem problem2 = new ThreadProblem();
        ThreadProblem problem3 = new ThreadProblem();
        
        this.notifier.setSupportsHtml(false);
                
        problem1.start();
        problem2.start();
        problem3.start();
        
        Daemon testDetector = new Daemon( detector, "Admin4j Blocked Thread Detector", 500L);
        
        Thread.sleep(30000);
        
        problem1.interrupt();
        problem2.interrupt();
        problem3.interrupt();
        
        testDetector.interrupt();
        
        System.out.println("Subject: " + notifier.getSubject());
        System.out.println(notifier.getMessage());
        System.out.println("----------------------------------------");
        
        assertTrue("Detect block test 1", notifier.getMessage() != null);
    }
    
    static class ThreadProblem extends Thread {
        private static Object lockedObject = new Object();

        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            while (true) {
                synchronized (lockedObject) {
                    try {Thread.sleep(10000);}
                    catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }

}
