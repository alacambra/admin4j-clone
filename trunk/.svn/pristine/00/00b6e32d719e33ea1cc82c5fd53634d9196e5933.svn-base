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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.vo.DataMeasurementSummaryVO;

/**
 * Used to start task timings and obtain a TaskTimer.
 * @author D. Ashmore
 * @since 1.0
 */
public class TaskTimerFactory {
    
    /**
     * Will return a started timer using the requested label.
     * 
     * <p>By default, timers are configured with a SmmaryDataMeasure and RollingTimePeriodDataMeasure.</p>
     * 
     * @see SummaryDataMeasure
     * @see RollingNbrObservationsDataMeasure
     * @param label
     * @param dataMeasures
     * @return
     * @since 1.0
     */
    public static TaskTimer start(String label, DataMeasure...dataMeasures ) {
        TaskTimer timer = TaskTimerRegistry.findTaskTimer(label);
        
        if (timer != null)  {
            timer.start();
            return timer;
        }
        
        List<DataMeasure> list = new ArrayList<DataMeasure>();
        if (dataMeasures.length == 0) {
            list.add(new SummaryDataMeasure(System.currentTimeMillis()));
            list.add(new RollingNbrObservationsDataMeasure());
        }
        else list.addAll(Arrays.asList(dataMeasures));
        
        
        timer = new BasicTaskTimer(label, list);
        TaskTimerRegistry.register(timer);
        
        timer.start();
        return timer;
    }
    
    public static void delete(String label) {
        Validate.notEmpty(label,"Null or blank label not allowed.");
        TaskTimerRegistry.delete(label);
    }
    
    protected static void clearAll() {
        TaskTimerRegistry.clearAll();
    }
    
    public static Map<String,Set<DataMeasurementSummaryVO>> getDataSummaryMap() {
        return TaskTimerRegistry.getDataSummaryMap();
    }

}
