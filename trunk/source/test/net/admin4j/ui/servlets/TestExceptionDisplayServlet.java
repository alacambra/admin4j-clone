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
package net.admin4j.ui.servlets;

import static org.easymock.EasyMock.expect;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestExceptionDisplayServlet extends BaseServletTestSupport {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        servlet = new ExceptionDisplayServlet();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBasic() throws Exception {
        expect(mockConfig.getInitParameter("exception.tracking.time.in.days")).andReturn("25");
        expect(mockConfig.getInitParameter("tracking.time.in.days")).andReturn("25");
        expect(mockConfig.getInitParameter("exception.exempted.exception.types")).andReturn(null);
        expect(mockConfig.getInitParameter("exempted.exception.types")).andReturn(null);
        
        performBasicServletTest();
    }

    

}
