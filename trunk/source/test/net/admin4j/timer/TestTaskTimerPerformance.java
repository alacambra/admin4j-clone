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



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTaskTimerPerformance {
    
    long nbrIterations = 500000;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testNoTimer() throws Exception {
        long startTime=System.currentTimeMillis();
        long temp;
        for (int i=0; i<nbrIterations; i++) {
            temp = System.currentTimeMillis() - System.currentTimeMillis() - startTime;
        }
        
        System.out.println("No timer test: " + (System.currentTimeMillis() - startTime) + " ms.");
    }
    
    @Test
    public void testDefaultTimer() throws Exception {
        long startTime=System.currentTimeMillis();
        TaskTimer timer;
        for (int i=0; i<nbrIterations; i++) {
            timer = TaskTimerFactory.start("default");
            
            timer.stop();
        }
        
        System.out.println("Default timer test: " + (System.currentTimeMillis() - startTime) + " ms.");
    }
    
    @Test
    public void testSummaryTimer() throws Exception {
        long startTime=System.currentTimeMillis();
        TaskTimer timer;
        for (int i=0; i<nbrIterations; i++) {
            timer = TaskTimerFactory.start("basic", new SummaryDataMeasure());
            
            timer.stop();
        }
        
        System.out.println("Summary timer test: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

}
