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

public class TestFileExplorerServlet extends BaseServletTestSupport {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        servlet = new FileExplorerServlet();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        expect(mockConfig.getInitParameter("fileExplorer.restrict.to.base.dir")).andReturn("true");
        expect(mockConfig.getInitParameter("fileExplorer.restrict.from.exec")).andReturn("true");
        expect(mockConfig.getInitParameter("fileExplorer.restrict.from.write")).andReturn("true");
        expect(mockConfig.getInitParameter("fileExplorer.base.dir.name")).andReturn(null);
        expect(mockConfig.getInitParameter("restrict.to.base.dir")).andReturn("true");
        expect(mockConfig.getInitParameter("restrict.from.exec")).andReturn("true");
        expect(mockConfig.getInitParameter("restrict.from.write")).andReturn("true");
        expect(mockConfig.getInitParameter("base.dir.name")).andReturn(null);
        performBasicServletTest();
    }

}
