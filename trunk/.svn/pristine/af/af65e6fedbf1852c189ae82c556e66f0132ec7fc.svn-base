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

import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Ensures that the proper ThreadDumper is used given the runtime JVM version.
 * @author D. Ashmore
 * @since 1.0
 */
public class ThreadDumperFactory {
    
    public static ThreadDumper getThreadDumper() {
        if (SystemUtils.IS_JAVA_1_5) {
            return new ThreadDumperV1_5();
        }
        
        try {
            return (ThreadDumper)Class.forName("net.admin4j.util.threaddumper.ThreadDumperV1_6").newInstance();
        } catch (Exception e) {
            throw new Admin4jRuntimeException(e);
        } 
    }

}
