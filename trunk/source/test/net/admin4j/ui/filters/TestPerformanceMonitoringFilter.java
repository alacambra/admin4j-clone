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

import net.admin4j.util.notify.NotifierTestingMock;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.tidy.Tidy;

public class TestPerformanceMonitoringFilter extends BaseFilterTestSupport {
    
    private PerformanceMonitoringFilter filter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mockConfig.addInitParameter("notifier", NotifierTestingMock.class.getName());
        filter = new PerformanceMonitoringFilter();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testInit() throws Exception {        
        filter.init(this.mockConfig);
    }

    @Test
    public void test() throws Exception {
        this.mockConfig.addInitParameter("notification.threshold.in.millis", "100");
        filter.init(this.mockConfig);
        
        MockFilterChain mockChain = new MockFilterChain();
        mockChain.setSleepTimeInMillis(0L);
        filter.doFilter(this.mockRequest, null, mockChain);
        Assert.assertTrue("No notification", ((NotifierTestingMock)filter.notifier).getMessage() == null);
        
        mockChain.setSleepTimeInMillis(200L);
        filter.doFilter(this.mockRequest, null, mockChain);
        Assert.assertTrue("No notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        
        System.out.println( ((NotifierTestingMock)filter.notifier).getMessage() );
        
        ((NotifierTestingMock)filter.notifier).setSupportsHtml(true);
        filter.doFilter(this.mockRequest, null, mockChain);
        Assert.assertTrue("No notification", ((NotifierTestingMock)filter.notifier).getMessage() != null);
        
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setXHTML(true);
        tidy.parse(new ByteArrayInputStream( ((NotifierTestingMock)filter.notifier).getMessage().getBytes()), 
                new ByteArrayOutputStream());
        
        System.out.println( ((NotifierTestingMock)filter.notifier).getMessage() );
        Assert.assertTrue("Parse test", tidy.getParseErrors() == 0);
        Assert.assertTrue("Parse test", tidy.getParseWarnings() == 0);
    }

}
