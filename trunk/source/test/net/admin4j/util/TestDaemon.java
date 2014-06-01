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
package net.admin4j.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDaemon {

//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

    @Test
    public void testRun() throws Exception {
        TestTask task = new TestTask();
        Daemon d = new Daemon(task, "Test Daemon", 100);
        Thread.sleep(220);
        
        assertTrue("Daemon test basic.  task.runCount=" + task.runCount, task.runCount == 2);

    }
    
    static class TestTask implements Runnable {
        
        private int runCount = 0;

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run() {
            runCount++;
            
        }

    }

}
