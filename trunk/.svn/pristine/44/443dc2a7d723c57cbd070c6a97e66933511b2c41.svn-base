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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.admin4j.util.NameValuePair;
import net.admin4j.util.notify.NotifierTestingMock;
import net.admin4j.vo.HttpRequestVO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.tidy.Tidy;

public class TestErrorNotificationFilter extends BaseFilterTestSupport {
    
    private ErrorNotificationFilter filter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mockConfig.addInitParameter("notifier", NotifierTestingMock.class.getName());
        filter = new ErrorNotificationFilter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() throws Exception {        
        filter.init(this.mockConfig);
    }

    @Test
    public void testDoFilter() throws Exception {
        filter.init(this.mockConfig);
        
        MockFilterChain mockChain = new MockFilterChain();
        filter.doFilter(this.mockRequest, null, mockChain);
        Assert.assertTrue("No notification", ((NotifierTestingMock)filter.notifier).getMessage() == null);
        
        // Text output test
        mockChain.setRuntimeException(new RuntimeException("Boo"));
        try {
            filter.doFilter(this.mockRequest, null, mockChain);
            Assert.fail("Should have blown up");
        }
        catch (Exception e) {
            // NoOp
        }
        
        Assert.assertTrue("Notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        System.out.println(((NotifierTestingMock)filter.notifier).getMessage());
        
        // HTML output test
        ((NotifierTestingMock)filter.notifier).setSupportsHtml(true);
        try {
            filter.doFilter(this.mockRequest, null, mockChain);
            Assert.fail("Should have blown up");
        }
        catch (Exception e) {
            // NoOp
        }
        
        Assert.assertTrue("Notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setXHTML(true);
        tidy.parse(new ByteArrayInputStream( ((NotifierTestingMock)filter.notifier).getMessage().getBytes()), 
                new ByteArrayOutputStream());
               
        System.out.println(((NotifierTestingMock)filter.notifier).getMessage());
        Assert.assertTrue("Parse test", tidy.getParseErrors() == 0);
        Assert.assertTrue("Parse test", tidy.getParseWarnings() == 0);
        
        List<HttpRequestVO> requestHistoryList = new ArrayList<HttpRequestVO>();
        List<NameValuePair> parmList = new ArrayList<NameValuePair>();
        requestHistoryList.add(new HttpRequestVO("/foo/request1", parmList));
        
        parmList = new ArrayList<NameValuePair>();
        parmList.add(new NameValuePair("parm1", "1"));
        parmList.add(new NameValuePair("parm2", "2"));
        requestHistoryList.add(new HttpRequestVO("/foo/request2", parmList));
        
        this.mockRequest.getSession().setAttribute(RequestTrackingFilter.REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME, requestHistoryList);
        
     // Text output test
        ((NotifierTestingMock)filter.notifier).setSupportsHtml(false);
        try {
            filter.doFilter(this.mockRequest, null, mockChain);
            Assert.fail("Should have blown up");
        }
        catch (Exception e) {
            // NoOp
        }
        Assert.assertTrue("Notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        System.out.println(((NotifierTestingMock)filter.notifier).getMessage());
        
     // HTML output test
        ((NotifierTestingMock)filter.notifier).setSupportsHtml(true);
        try {
            filter.doFilter(this.mockRequest, null, mockChain);
            Assert.fail("Should have blown up");
        }
        catch (Exception e) {
            // NoOp
        }
        
        Assert.assertTrue("Notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        
        tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setXHTML(true);
        tidy.parse(new ByteArrayInputStream( ((NotifierTestingMock)filter.notifier).getMessage().getBytes()), 
                new ByteArrayOutputStream());
       
        
       
        System.out.println(((NotifierTestingMock)filter.notifier).getMessage());
        Assert.assertTrue("Parse test", tidy.getParseErrors() == 0);
        Assert.assertTrue("Parse test", tidy.getParseWarnings() == 0);
    }

}
