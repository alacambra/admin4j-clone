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

import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.ExpiringCache;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Monitors performance for Quartz batch jobs.
 * 
 * <p>To install, place the following line in your quartz.properties file.</p>
 * <code>org.quartz.jobListener.Admin4JPerformanceMonitor.class = net.admin4j.timer.QuartzPerformanceMonitoringJobListener</code>
 * @author D. Ashmore
 * @since 1.0
 */
public class QuartzPerformanceMonitoringJobListener implements JobListener {
    
    private static final ExpiringCache TIMER_CACHE = new ExpiringCache(60000 * 60 * 24, 60000 * 60 * 24);
    private static final Logger logger = LoggerFactory.getLogger(QuartzPerformanceMonitoringJobListener.class);
    private String name = "Admin4JBatchJobPerformanceMonitoring";

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void jobExecutionVetoed(JobExecutionContext jobContext) {
        this.timerStop(jobContext);

    }

    public void jobToBeExecuted(JobExecutionContext jobContext) {
        try {
            String contextKey = this.createContextKey(jobContext);
            String timerLabel = this.createTimerLabel(jobContext);
            TaskTimer perfMonitor = TaskTimerFactory.start(timerLabel);
            TIMER_CACHE.put(contextKey, perfMonitor);
        }
        catch (Throwable t) {
            logger.error("Error starting performance time on Quartz Batch job", 
                    new Admin4jRuntimeException(t).addContextValue("jobContext", jobContext));
        }
    }

    public void jobWasExecuted(JobExecutionContext jobContext,
            JobExecutionException jobException) {
        this.timerStop(jobContext);

    }
    
    private void timerStop(JobExecutionContext jobContext) {
        try {
            String contextKey = this.createContextKey(jobContext);
            TaskTimer perfMonitor = (TaskTimer)TIMER_CACHE.get(contextKey);;
            if (perfMonitor != null) {
                perfMonitor.stop();
            }
        }
        catch (Throwable t) {
            logger.error("Error starting performance time on Quartz Batch job", 
                    new Admin4jRuntimeException(t).addContextValue("jobContext", jobContext));
        }
    }
    
    private String createContextKey(JobExecutionContext jobContext) {
        return createTimerLabel(jobContext) + System.identityHashCode(jobContext);
    }
    
    private String createTimerLabel(JobExecutionContext jobContext) {
        return "batch-" + jobContext.getJobDetail().getFullName();
    }

}
