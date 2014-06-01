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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.admin4j.entity.ExecutionStack;
import net.admin4j.timer.DataMeasure;
import net.admin4j.timer.TaskTimer;
import net.admin4j.vo.DataMeasurementSummaryVO;

/**
 * Tracks SQL Statement timers that have been issued.
 * @author D. Ashmore
 * @since 1.0.1
 */
public class SqlStatementTimerFactory {
    
    public static TaskTimer start(String sqlText, DriverContext driverContext, StackTraceElement[] executionStack) {
        return start(sqlText, driverContext, new ExecutionStack(executionStack));
    }
    
    public static TaskTimer start(String sqlText, DriverContext driverContext, ExecutionStack executionStack) {
        String key = deriveKey(sqlText, driverContext);
        SqlTaskTimer timer = SqlStatementTimerRegistry.findTaskTimer(key);
        if (timer != null)  {
            timer.start();
            return timer;
        }
        
        List<DataMeasure> list = new ArrayList<DataMeasure>();
        list.add(new SqlStatementSummaryDataMeasure(System.currentTimeMillis(), executionStack));
        timer = new SqlTaskTimer(key, list, driverContext, executionStack);
        SqlStatementTimerRegistry.register(timer);
        
        timer.start();
        return timer;
    }
    
    public static Map<String,Set<DataMeasurementSummaryVO>> getDataSummaryMap() {
        return SqlStatementTimerRegistry.getDataSummaryMap();
    }
    
    public void clearAll() {
        SqlStatementTimerRegistry.clearAll();
    }
    
    private static String deriveKey(String sqlText, DriverContext driverContext) {
        return driverContext + "-" + sqlText;
    }

}
