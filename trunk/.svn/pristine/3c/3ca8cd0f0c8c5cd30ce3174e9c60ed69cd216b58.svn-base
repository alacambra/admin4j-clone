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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJmxServlet extends BaseServletTestSupport {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        servlet = new JmxServlet();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception  {
        performBasicServletTest(); 
        
        this.mockRequest.setAttribute("jmxBean", "java.lang:type=OperatingSystem");
        servlet.service(this.mockRequest, this.mockResponse);
        this.performBasicAsserts();
    }

}
