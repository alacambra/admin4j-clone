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


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestExpiringCache {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testBasic() throws Exception {
        ExpiringCache cache = new ExpiringCache(100, 100);
        
        cache.put("Test", "TestValue");
        
        Object obj = cache.get("Test");
        assertTrue("Cache storage failed", "TestValue".equals(obj));
        
        Thread.sleep(110);
        obj = cache.get("Test");
        assertTrue("Cache not properly expired.", obj == null);
    }

}
