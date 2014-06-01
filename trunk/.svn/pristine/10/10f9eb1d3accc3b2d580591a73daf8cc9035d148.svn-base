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
package net.admin4j.timer;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTaskTimerFactory {

    @Before
    public void setUp() throws Exception {
        TaskTimerFactory.clearAll();
    }

    @After
    public void tearDown() throws Exception {
        TaskTimerFactory.clearAll();
    }
    
    @Test
    public void testBasic() throws Exception {
        TaskTimer timer = TaskTimerFactory.start("foo");
        assertTrue("basic start test", timer != null);
        assertTrue("basic start test 2", TaskTimerFactory.getDataSummaryMap().size() == 1);
        
        TaskTimerCleanupTask task = new TaskTimerCleanupTask ();
        task.run();
        
        TaskTimerFactory.delete("foo");
        assertTrue("basic start test 3", TaskTimerFactory.getDataSummaryMap().size() == 0);
    }

}
