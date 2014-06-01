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
package net.admin4j.util.threaddumper;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Dumps all threads with all available Jdk 1.6 features for including locked 
 * monitors or synchronizers. 
 * @author D. Ashmore
 * @since 1.0
 */
public class ThreadDumperV1_6 implements ThreadDumper {

    public ThreadInfo[] dumpAllThreads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        
        // If Monitor and Sychronizer info is supported by JVM, gather
        // the info.  These are 1.6 features and won't load under 1.5.
        return threadMXBean.dumpAllThreads(
                threadMXBean.isObjectMonitorUsageSupported(), 
                threadMXBean.isSynchronizerUsageSupported());
    }

}
