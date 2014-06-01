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
import static org.easymock.EasyMock.replay;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for MemoryMonitorStartupServlet
 * @author D. Ashmore
 *
 */
public class TestMemoryMonitorStartupServlet extends BaseServletTestSupport {
    
    private MemoryMonitorStartupServlet servlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        servlet = new MemoryMonitorStartupServlet();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConfigSpec() throws Exception {
        expect(mockConfig.getInitParameter("memory.sleep.interval.millis")).andReturn("110000");
        expect(mockConfig.getInitParameter("sleep.interval.millis")).andReturn("110000");
        
        expect(mockConfig.getInitParameter("memory.threshold.pct")).andReturn("95");
        expect(mockConfig.getInitParameter("memory.nbr.intervals.between.warnings")).andReturn("250");
        expect(mockConfig.getInitParameter("nbr.intervals.between.warnings")).andReturn("250");
        expect(mockConfig.getInitParameter("memory.low.watermark.monitor.interval.millis")).andReturn("120000");
        expect(mockConfig.getInitParameter("memory.nbr.low.watermark.intervals")).andReturn("500");
        expect(mockConfig.getInitParameter("notifier")).andReturn("net.admin4j.util.notify.LogNotifier");
        
        replay(mockConfig);
        servlet.init(mockConfig);
        
        Assert.assertTrue("Low", servlet.getLowWatermarkMonitorIntervalInMillis() == 120000);
        Assert.assertTrue("Low", servlet.getMemoryThresholdPct() == 95);
        Assert.assertTrue("Low", servlet.getNbrIntervalsBetweenWarnings() == 250);
        Assert.assertTrue("Low", servlet.getNbrLowWatermarkIntervals() == 500);
                
        //fail("Not yet implemented");
    }

}
