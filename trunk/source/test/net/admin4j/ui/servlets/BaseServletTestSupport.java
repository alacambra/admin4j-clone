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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import junit.framework.Assert;
import net.admin4j.ui.MockRequest;
import net.admin4j.ui.MockResponse;

import org.junit.Before;

public class BaseServletTestSupport {
    
    protected HttpServlet servlet;
    protected ServletConfig mockConfig;
    protected MockRequest mockRequest;
    protected MockResponse mockResponse;
    
    @Before
    public void setUp() throws Exception {
        mockConfig = createMock(ServletConfig.class);
        mockRequest = new MockRequest();
        mockResponse = new MockResponse();
    }
    
    protected void performBasicServletTest() throws ServletException,
    IOException {
        replay(mockConfig);
        servlet.init(this.mockConfig);
        
        servlet.service(this.mockRequest, this.mockResponse);
        performBasicAsserts();
    }

    protected void performBasicAsserts() {
        Assert.assertTrue("content type check", "text/html".equals(this.mockResponse.getContentType()));
        Assert.assertTrue("content check", this.mockResponse.getMockServletOutputStream().getBytes() != null);
        
        System.out.println( new String(this.mockResponse.getMockServletOutputStream().getBytes()));
    }

}
