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

import net.admin4j.ui.MockResponse;
import net.admin4j.util.notify.NotifierTestingMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin4JStandardFilter extends BaseFilterTestSupport {
    
    private Admin4JStandardFilter filter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mockConfig.addInitParameter("notifier", NotifierTestingMock.class.getName());
        filter = new Admin4JStandardFilter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConfig() throws Exception {
        this.mockConfig.addInitParameter("include.serverside.timestamp", "true");
        filter.init(this.mockConfig);
    }
    
    @Test
    public void testFilter() throws Exception {
        filter.init(this.mockConfig);
        MockFilterChain mockChain = new MockFilterChain();
        MockResponse mockResponse = new MockResponse();
        filter.doFilter(this.mockRequest, mockResponse, mockChain);
    }

}
