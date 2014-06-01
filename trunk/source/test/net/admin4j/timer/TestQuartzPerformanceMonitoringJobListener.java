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
package net.admin4j.timer;

import java.util.Date;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;

public class TestQuartzPerformanceMonitoringJobListener {
    
    private QuartzPerformanceMonitoringJobListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new QuartzPerformanceMonitoringJobListener();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        listener.setName("foo");
        Assert.assertTrue("name test", "foo".equals(listener.getName()));
        Assert.assertTrue(TaskTimerFactory.getDataSummaryMap().size() == 0);
        
        Scheduler scheduler = EasyMock.createMock(Scheduler.class);
        JobDetail jobDetail = new JobDetail();
        Trigger trigger = new SimpleTrigger();
        TriggerFiredBundle bundle = new TriggerFiredBundle(jobDetail, trigger, null
                , false, new Date(), new Date(), new Date(), new Date()) ;
        Job job = EasyMock.createMock(Job.class);
        JobExecutionContext jobContext = new JobExecutionContext (scheduler, bundle, job);
        
        listener.jobToBeExecuted(jobContext);
        listener.jobWasExecuted(jobContext, null);
        
        listener.jobToBeExecuted(jobContext);
        listener.jobExecutionVetoed(jobContext);
        
        Assert.assertTrue(TaskTimerFactory.getDataSummaryMap().size() > 0);
    }

}
