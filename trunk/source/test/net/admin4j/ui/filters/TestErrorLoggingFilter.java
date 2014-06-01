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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestErrorLoggingFilter extends BaseFilterTestSupport {
    
    private ErrorLoggingFilter filter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        filter = new ErrorLoggingFilter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConfig() throws Exception {

        filter.init(mockConfig);
        Assert.assertTrue("positive test", Admin4JStandardFilterChain.getRegisteredFilter(ErrorLoggingFilter.class.getName()) == filter);
        Assert.assertTrue("positive test", filter.getLogger() != null && filter.getLogger().getName().indexOf("ErrorLoggingFilter") > 0);
 
    }
    
    @Test
    public void testDoFilter() throws Exception {
        filter.init(mockConfig);
        
        MockFilterChain mockChain = new MockFilterChain();
        
        filter.doFilter(null, null, mockChain);
        
        mockChain.setRuntimeException(new RuntimeException("Boo"));
        try {
            filter.doFilter(null, null, mockChain);
            Assert.fail("Should have blown up");
        }
        catch (Exception e) {
            // NoOp
        }

    }

}
