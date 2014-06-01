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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.monitor.BlockedThreadDetector;
import net.admin4j.util.Daemon;
import net.admin4j.util.ServletUtils;
import net.admin4j.util.notify.NotifierUtils;


/**
 * This servlet will start a thread monitor that will notify administrators of threading issues.
 * <p>Init parameters for this servlet are as follows:</p>
 * <li>sleep.interval.millis -- Optional.  Default 30000 (30 sec).  Amount of time in millis monitor will sleep between checks.</li>
 * <li>max.blocked.threads -- Optional.  Default 2. Number of blocked threads monitor will tolerate before notification.</li>
 * <li>notifier -- Required.  Handles admin notification.  See documentation for the Notifier you're using
 * for any additional configuration requirements.  For example, 'net.admin4j.util.notify.EmailNotifier'.</li>
 * @author D. Ashmore
 * @since 1.0
 */
public class ThreadMonitorStartupServlet extends Admin4JServlet {

	private static final long serialVersionUID = 591261515058479941L;
	public static final String PUBLIC_HANDLE="thread";
	
	@SuppressWarnings("unused")
    private static Daemon threadMonitorDaemon = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
		try {			
			long sleepIntervalMillis = BlockedThreadDetector.DEFAULT_SLEEP_INTERVAL;
			int nbrBlockedThreads = BlockedThreadDetector.DEFAULT_BLOCKED_THREAD_THRESHOLD;
			
			String sleepIntervalMillisStr = ServletUtils.getConfigurationSetting(
                    new String[]{PUBLIC_HANDLE + ".sleep.interval.millis", 
                    "sleep.interval.millis"}, config);
			if (sleepIntervalMillisStr != null) {
				try {
                    sleepIntervalMillis = Long.parseLong(sleepIntervalMillisStr);
                } catch (Exception e) {
                    sleepIntervalMillis = BlockedThreadDetector.DEFAULT_SLEEP_INTERVAL;
                    this.logger.error("Invalid sleep.interval.millis parameter for ThreadMonitorStartupServlet.  Default used.  sleep.interval.millis="+ sleepIntervalMillisStr, 
                            e);
                }
			}
			else if ( Admin4JConfiguration.getThreadSleepIntervalMillis() != null ) {
			    sleepIntervalMillis = Admin4JConfiguration.getThreadSleepIntervalMillis();
            }
			
			String blockedThreadThresholdStr = ServletUtils.getConfigurationSetting(
                    new String[]{PUBLIC_HANDLE + ".max.blocked.threads", 
                    "max.blocked.threads"}, config);
			if (blockedThreadThresholdStr != null) {
				try {
                    nbrBlockedThreads = Integer.parseInt(blockedThreadThresholdStr);
                } catch (Exception e) {
                    nbrBlockedThreads = BlockedThreadDetector.DEFAULT_BLOCKED_THREAD_THRESHOLD;
                    this.logger.error("Invalid max.blocked.threads parameter for ThreadMonitorStartupServlet.  Default used.  max.blocked.threads="+ blockedThreadThresholdStr, 
                            e);
                }
			}
			else if ( Admin4JConfiguration.getThreadMaxBlockedThreads() != null ) {
			    nbrBlockedThreads = Admin4JConfiguration.getThreadMaxBlockedThreads();
            }
			
			
			
			String notifierClassName = config.getInitParameter("notifier");
			BlockedThreadDetector blockedThreadDetector = new BlockedThreadDetector(
			        NotifierUtils.configure(config, notifierClassName), 
			        nbrBlockedThreads);
			threadMonitorDaemon = new Daemon( blockedThreadDetector, "Admin4j Blocked Thread Detector", sleepIntervalMillis);
		}
		catch (Throwable t) {
			logger.error("Error in boot sequence", t);
			throw new ServletException(t);
		}
	}
	
    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#hasDisplay()
     */
    @Override
    public boolean hasDisplay() {
        return false;
    }
}
