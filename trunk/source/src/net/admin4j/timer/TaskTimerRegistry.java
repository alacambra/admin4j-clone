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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.dao.DAOFactory;
import net.admin4j.util.Daemon;
import net.admin4j.vo.DataMeasurementSummaryVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry for all performance timer metrics.
 * @author D. Ashmore
 *
 */
class TaskTimerRegistry  {
    
    private static final TaskTimerRegistry singleton = new TaskTimerRegistry();
    private Map<String, TaskTimer> registeredDataMeasures = new ConcurrentHashMap<String, TaskTimer>();
    private static Logger log = LoggerFactory.getLogger(TaskTimerRegistry.class);
    
    static {
        if (Admin4JConfiguration.isPerformanceInfoStored()) {
            try {
                for (TaskTimer timer: DAOFactory.getTaskTimerDAO().findAll()) {
                    register(timer);
                }
            }
            catch (Throwable t) {
                log.error("Error reading stored performance info", t);
            }
        }
    }
    
    @SuppressWarnings("unused")
    private static final Daemon CLEANUP_DAEMON = new Daemon(new TaskTimerCleanupTask(), "Admin4J-TaskTimerCleanupTask", 300000);
    static {
        Runtime.getRuntime().addShutdownHook(new TaskTimerShutdownHook());
    }
    
    private TaskTimerRegistry() {
        // Prevent unauthorized instantiation
    }
    
    public static void register(TaskTimer timer) {
        singleton.registeredDataMeasures.put(timer.getLabel(), timer);
    }
    
    public static TaskTimer findTaskTimer(String label) {
        return singleton.registeredDataMeasures.get(label);
    }
    
    public static void delete(String label) {
        singleton.registeredDataMeasures.remove(label);
        saveAll();
    }

    private static void saveAll() {
        if (Admin4JConfiguration.isPerformanceInfoStored()) {
            DAOFactory.getTaskTimerDAO().saveAll(
                    new HashSet<TaskTimer>(
                            TaskTimerRegistry.getRegisteredDataMeasures().values()));
        }
    }
    
    public static void clearAll() {
        singleton.registeredDataMeasures.clear();
        saveAll();
    }
    
    public static Map<String,Set<DataMeasurementSummaryVO>> getDataSummaryMap() {
        Map<String, Set<DataMeasurementSummaryVO>> summaryMap 
            = TaskTimerUtils.produceDataSummaryMap(singleton.registeredDataMeasures.values());
        
        return summaryMap;
    }

    public static Map<String, TaskTimer> getRegisteredDataMeasures() {
        return singleton.registeredDataMeasures;
    }

}
