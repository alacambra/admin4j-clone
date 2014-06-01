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
import junit.framework.Assert;
import net.admin4j.util.HttpServletRequestMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin4JHomePageServlet extends BaseServletTestSupport {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        servlet = new Admin4JHomePageServlet();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDetermineBaseUri() {
        HttpServletRequestMock request = new HttpServletRequestMock();
        
        request.setRequestURI("/admin4j/index/" + HotSpotDisplayServlet.PUBLIC_HANDLE);
        Assert.assertTrue("/admin4j/index/sql test", "/admin4j/index".equals(((Admin4JHomePageServlet)servlet).determineBaseUri(request)));
        
        request.setRequestURI("/admin4j/index");
        Assert.assertTrue("/admin4j/index/sql test", "/admin4j/index".equals(((Admin4JHomePageServlet)servlet).determineBaseUri(request)));
        
        Assert.assertTrue("Label test", ((Admin4JHomePageServlet)servlet).getServletLabel() != null);
    }
    
    @Test
    public void testInit() throws Exception {
        expect(mockConfig.getInitParameter("hotSpot.sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("exception.tracking.time.in.days")).andReturn(null);
        expect(mockConfig.getInitParameter("tracking.time.in.days")).andReturn(null);
        expect(mockConfig.getInitParameter("exception.exempted.exception.types")).andReturn(null);
        expect(mockConfig.getInitParameter("exempted.exception.types")).andReturn(null);
        expect(mockConfig.getInitParameter("fileExplorer.restrict.to.base.dir")).andReturn(null);
        expect(mockConfig.getInitParameter("fileExplorer.restrict.from.exec")).andReturn(null);
        expect(mockConfig.getInitParameter("fileExplorer.restrict.from.write")).andReturn(null);
        expect(mockConfig.getInitParameter("fileExplorer.base.dir.name")).andReturn(null);
        expect(mockConfig.getInitParameter("restrict.to.base.dir")).andReturn(null);
        expect(mockConfig.getInitParameter("restrict.from.exec")).andReturn(null);
        expect(mockConfig.getInitParameter("restrict.from.write")).andReturn(null);
        expect(mockConfig.getInitParameter("base.dir.name")).andReturn(null);
        expect(mockConfig.getInitParameter("memory.sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("memory.threshold.pct")).andReturn(null);
        expect(mockConfig.getInitParameter("memory.nbr.intervals.between.warnings")).andReturn(null);
        expect(mockConfig.getInitParameter("nbr.intervals.between.warnings")).andReturn(null);
        expect(mockConfig.getInitParameter("memory.low.watermark.monitor.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("memory.nbr.low.watermark.intervals")).andReturn(null);
        expect(mockConfig.getInitParameter("notifier")).andReturn("net.admin4j.util.notify.LogNotifier");
        expect(mockConfig.getInitParameter("thread.sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("sleep.interval.millis")).andReturn(null);
        expect(mockConfig.getInitParameter("thread.max.blocked.threads")).andReturn(null);
        expect(mockConfig.getInitParameter("max.blocked.threads")).andReturn(null);
        expect(mockConfig.getInitParameter("notifier")).andReturn("net.admin4j.util.notify.LogNotifier");
        this.performBasicServletTest();
    }

}
