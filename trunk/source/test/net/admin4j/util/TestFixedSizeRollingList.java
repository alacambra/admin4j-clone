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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFixedSizeRollingList {
    
    private FixedSizeRollingList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new FixedSizeRollingList<Integer>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAdd() {
        for (int i = 0; i < list.getMaxSize() * 2; i++) {
            list.add(Integer.valueOf(i));
        }
        
        Assert.assertTrue("add size test", list.size() == FixedSizeRollingList.DEFAULT_MAX_SIZE);
        for (Integer i: list) {
            if (i < 10) {
                Assert.fail("Incorrect element left in list.  value=" + i);
            }
        }
        
        Integer oldValue = list.get(7);
        Integer lastValue = list.get(9);
        Integer newLastValue = list.get(8);
        list.add(7, Integer.valueOf(-1));
        Assert.assertTrue("Added in correct place", list.get(7) == -1);
        Assert.assertTrue("Added in correct place 2", list.get(8) == oldValue);
        Assert.assertTrue("Added in correct place 3", list.get(9) == newLastValue);
        
        for (Integer i: list) {
            if (i.equals(lastValue)) {
                Assert.fail("Incorrect element left in list.  value=" + i);
            }
        }
        
        list.set(5, Integer.valueOf(-2));
        Assert.assertTrue("Set in correct place", list.get(5) == -2);
        
    }
    
    @Test
    public void testAddAll() {
        for (int i = 0; i < list.getMaxSize() * 2; i++) {
            list.add(Integer.valueOf(i));
        }
        
        list.addAll(list);
        
        boolean exceptionCaught = false;
        try {list.addAll(3, list);}
        catch (Exception e) {
            exceptionCaught = true;
        }
        
        Assert.assertTrue("Exception from large list added", exceptionCaught);
    }

}
