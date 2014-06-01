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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNumberUtils {
    
    private List<Number> numberList;

    @Before
    public void setUp() throws Exception {
        numberList = new ArrayList<Number>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMin() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        assertTrue(NumberUtils.min(numberList).equals(Integer.valueOf(1)));
        
        numberList.add(new Float(0.5));
        assertTrue(NumberUtils.min(numberList).equals(new Float(0.5)));
    }

    @Test
    public void testMax() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        assertTrue(NumberUtils.max(numberList).equals(Integer.valueOf(5)));
        
        numberList.add(new Float(5.5));
        assertTrue(NumberUtils.max(numberList).equals(new Float(5.5)));
    }

    @Test
    public void testSum() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        System.out.println(NumberUtils.sum(numberList));
        assertTrue(NumberUtils.sum(numberList).equals(Long.valueOf(8)));
    }

    @Test
    public void testAverage() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        System.out.println("Average = " + NumberUtils.average(numberList));
    }

    @Test
    public void testStandardDeviation() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        System.out.println("Std Dev. = " + NumberUtils.standardDeviation(numberList));
        
        numberList.clear();
        numberList.add(2);
        numberList.add(4);
        numberList.add(4);
        numberList.add(4);
        numberList.add(5);
        numberList.add(5);
        numberList.add(7);
        numberList.add(9);
        
        System.out.println("Std Dev. example = " + NumberUtils.standardDeviation(numberList));
    }

    @Test
    public void testVariance() {
        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        
        System.out.println("Variance = " + NumberUtils.variance(numberList));
    }

}
