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
package net.admin4j.ui.filters;


import static org.junit.Assert.assertTrue;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin4JStandardFilterChain {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testFilterRegistration() throws Exception {
        Filter filterOrig = new ErrorLoggingFilter();
        Filter filterSecond = new ErrorLoggingFilter();
        
        Admin4JStandardFilterChain.clearFilterMap();
        
        assertTrue("negative test", !Admin4JStandardFilterChain.isRegistered(ErrorLoggingFilter.class.getName()));
        Admin4JStandardFilterChain.registerFilter(filterOrig);
        assertTrue("positive test", Admin4JStandardFilterChain.isRegistered(ErrorLoggingFilter.class.getName()));
        assertTrue("positive test", Admin4JStandardFilterChain.getRegisteredFilter(ErrorLoggingFilter.class.getName()) == filterOrig);
        
        Admin4JStandardFilterChain.registerFilter(filterSecond, true);
        assertTrue("positive test", Admin4JStandardFilterChain.isRegistered(ErrorLoggingFilter.class.getName()));
        assertTrue("positive test", Admin4JStandardFilterChain.getRegisteredFilter(ErrorLoggingFilter.class.getName()) == filterOrig);
        
        Admin4JStandardFilterChain.registerFilter(filterSecond, false);
        assertTrue("positive test", Admin4JStandardFilterChain.isRegistered(ErrorLoggingFilter.class.getName()));
        assertTrue("positive test", Admin4JStandardFilterChain.getRegisteredFilter(ErrorLoggingFilter.class.getName()) == filterSecond);
    }

}
