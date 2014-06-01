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
package net.admin4j.jdbc.driver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Registry for JDBC drivers that are currently being proxied.
 * @author D. Ashmore
 * @since 1.0.1
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public class DriverContextRegistry {
    
    private static Map<DriverContext, DriverSettings> registryMap = new ConcurrentHashMap<DriverContext, DriverSettings>();
    
    public static boolean hasSettings(DriverContext context) {
        Validate.notNull(context, "Null DriverContext not allowed.");
        return registryMap.containsKey(context);
    }
    
    public static boolean isTrackExecutionStacks(DriverContext context) {
        Validate.notNull(context, "Null DriverContext not allowed.");
        boolean answer = true;
        
        DriverSettings settings = registryMap.get(context);
        if (settings != null && !settings.trackExecutionStacks.get()) {
            answer = false;
        }
        
        return answer;
    }
    
    public static void setTrackExecutionStacks(DriverContext context, boolean setting) {
        Validate.notNull(context, "Null DriverContext not allowed.");
        
        synchronized (registryMap) {
            DriverSettings settings = registryMap.get(context);
            if (settings == null) {
                settings = new DriverSettings();
                registryMap.put(context, settings);
            }
            settings.trackExecutionStacks.set(setting);
        }
    }
    
    private static class DriverSettings {
        public AtomicBoolean trackExecutionStacks = new AtomicBoolean(true);
    }

}
