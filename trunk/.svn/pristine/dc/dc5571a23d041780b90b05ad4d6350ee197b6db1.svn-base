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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.monitor.ConcurrentUsageDetector;
import net.admin4j.util.Daemon;

/**
 * Will measure concurrent usage.
 * <p>Init parameters for this filter are as follows:</p>
 * <li>sleep.interval.millis -- Optional.  Default 30000 (30 sec).  Amount of time in millis monitor will sleep between checks.</li>
 * <li>alert.levels -- Optional.  Default 100. Comma-delimited list of threshold levels for which notices will be sent.  Example: 10,50,100</li>
 * <li>notifier -- Required.  Handles admin notification.  See documentation for the Notifier you're using
 * @author D. Ashmore
 * @since 1.0.1
 */
public class ConcurrentUsageFilter extends BaseNotificationFilter {
    
    @SuppressWarnings("unused")
    private Daemon concurrentUsageDaemon = null;
    private ConcurrentUsageDetector concurrentUsageDetector;

    public void destroy() {
        // NoOp

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        concurrentUsageDetector.increment();
        try {chain.doFilter(request, response);}
        finally {
            concurrentUsageDetector.decrement();
        }

    }

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        
        long sleepIntervalMillis = ConcurrentUsageDetector.DEFAULT_SLEEP_INTERVAL;
        Integer[] thresholdLevels = ConcurrentUsageDetector.DEFAULT_ALERT_LEVELS;
        
        String sleepIntervalMillisStr = config.getInitParameter("sleep.interval.millis");
        if (sleepIntervalMillisStr != null) {
            try {
                sleepIntervalMillis = Long.parseLong(sleepIntervalMillisStr);
            } catch (Exception e) {
                sleepIntervalMillis = ConcurrentUsageDetector.DEFAULT_SLEEP_INTERVAL;
                this.logger.error("Invalid sleep.interval.millis parameter for ConcurrentUsageFilter.  Default used.  sleep.interval.millis="+ sleepIntervalMillisStr, 
                        e);
            }
        }
        else if ( Admin4JConfiguration.getConcurrentUsageSleepIntervalMillis() != null ) {
            sleepIntervalMillis = Admin4JConfiguration.getConcurrentUsageSleepIntervalMillis();
        }
        
        String alertLevelsStr = config.getInitParameter("alert.levels");
        if (alertLevelsStr != null) {
            try {
                StringTokenizer token = new StringTokenizer(alertLevelsStr, ",");
                List<Integer> list = new ArrayList<Integer>();
                while (token.hasMoreTokens()) {
                    list.add(Integer.valueOf(token.nextToken().trim()));
                }
                thresholdLevels = list.toArray(new Integer[0]);
            }
            catch (Exception e) {
                thresholdLevels = ConcurrentUsageDetector.DEFAULT_ALERT_LEVELS;
                this.logger.error("Invalid alert.levels parameter for ConcurrentUsageFilter.  Default used.  alert.levels="+ alertLevelsStr, 
                        e);
            }
            
        }
        else if ( Admin4JConfiguration.getConcurrentUsageAlertLevels() != null ) {
            thresholdLevels = Admin4JConfiguration.getConcurrentUsageAlertLevels();
        }
        
        concurrentUsageDetector = new ConcurrentUsageDetector(this.notifier, thresholdLevels);
        concurrentUsageDaemon = new Daemon(concurrentUsageDetector, "Admin4j Concurrent Usage Detector", sleepIntervalMillis);

    }

}
