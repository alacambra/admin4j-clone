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
package net.admin4j.hotspot;


import static org.junit.Assert.assertTrue;

import java.util.Map;

import net.admin4j.entity.ExecutionPoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestExecutionTracker {

    @Before
    public void setUp() throws Exception {
        ExecutionTracker.reset();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testMe() throws Exception {
        ExecutionTracker.trackAll();
        Map<StackTraceElement, ExecutionPoint> map = ExecutionTracker.getExecutionMap();
        System.out.println(map);
        
        assertTrue("map test", map != null && map.size() > 0);
        assertTrue("map test", ExecutionTracker.getHotSpotMap().size() > 0);
        assertTrue("map test", ExecutionTracker.getBlockedExecutionMap().size() == 0);
        
        ExecutionTracker.reset();
        map = ExecutionTracker.getExecutionMap();
        assertTrue("map test", map != null && map.size() == 0);
        
        ExecutionTrackingTask task = new ExecutionTrackingTask();
        task.run();
    }

}
