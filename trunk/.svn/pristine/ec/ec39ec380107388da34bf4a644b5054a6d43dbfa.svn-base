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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.timer.DataMeasure;
import net.admin4j.timer.TaskTimer;
import net.admin4j.vo.DataMeasurementSummaryVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tracks performance metrics for SQL Statements
 * @author D. Ashmore
 *
 */
class SqlStatementTimerRegistry {
    
    private static final SqlStatementTimerRegistry singleton = new SqlStatementTimerRegistry();
    private Map<String, SqlTaskTimer> registeredDataMeasures
        = new ConcurrentHashMap<String, SqlTaskTimer>();
    
    public static final Integer DEFAULT_NBR_SQL_STATEMENTS = 50; 
    private static Integer nbrRetainedSqlStatements = DEFAULT_NBR_SQL_STATEMENTS;
    
    public static final Long DEFAULT_SQL_RETENTION_TIME_IN_MILLIS = 60L * 60L * 6L * 1000L; // 6 hrs
    private static Long sqlRetentionTimeInMillis = DEFAULT_SQL_RETENTION_TIME_IN_MILLIS;
    
    static {
        if (Admin4JConfiguration.getSqlNbrRetainedSqlStatements() != null) {
            nbrRetainedSqlStatements = Admin4JConfiguration.getSqlNbrRetainedSqlStatements();
        }
        if (Admin4JConfiguration.getSqlRetentionTimeInMillis() != null) {
            sqlRetentionTimeInMillis = Admin4JConfiguration.getSqlRetentionTimeInMillis();
        }
    }
    
    public static void register(SqlTaskTimer timer) {
        singleton.registeredDataMeasures.put(timer.getLabel(), timer);
    }
    
    public static SqlTaskTimer findTaskTimer(String label) {
        Validate.notEmpty(label, "Null or blank label not allowed.");
        return singleton.registeredDataMeasures.get(label);
    }
    
    public static void delete(String label) {
        singleton.registeredDataMeasures.remove(label);
    }
    
    public static Map<String,Set<DataMeasurementSummaryVO>> getDataSummaryMap() {
        TreeSet<SqlTaskTimer> set = new TreeSet<SqlTaskTimer>(new TaskTimerComparable());
        set.addAll(singleton.registeredDataMeasures.values());
        HashSet<SqlTaskTimer> topSet = new HashSet<SqlTaskTimer>();
        for (SqlTaskTimer timer: set) {
            if (topSet.size() < nbrRetainedSqlStatements && extractDataMeasure(timer).getLastObservationTime() > createEarliestTime()) {
                topSet.add(timer);
            }
        }
        Map<String,Set<DataMeasurementSummaryVO>> summaryMap = new HashMap<String,Set<DataMeasurementSummaryVO>>();
        
        Set<DataMeasurementSummaryVO> summarySet;
        DataMeasurementSummaryVO summary;
        for (SqlTaskTimer timer :topSet) {
            summarySet = new HashSet<DataMeasurementSummaryVO>();
            summaryMap.put(timer.getDriverContext() + "-" + timer.getLabel(), summarySet);
            for (DataMeasure measure: timer.getDataMeasures()) {
                summary = measure.getDataMeasurementSummary();
                summary.setLabel(timer.getLabel());
                summarySet.add(summary);
            }
        }
        return summaryMap;
    }
    
    private static Long createEarliestTime() {
        return System.currentTimeMillis() - sqlRetentionTimeInMillis;
    }
    
    protected static void clearAll() {
        singleton.registeredDataMeasures.clear();
    }
    
    private static class TaskTimerComparable implements Comparator<TaskTimer> {

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(TaskTimer timer1, TaskTimer timer2) {
            SqlStatementSummaryDataMeasure measure1 = extractDataMeasure(timer1);
            SqlStatementSummaryDataMeasure measure2 = extractDataMeasure(timer2);
            
            // Intentionally reverse order.
            if (measure1.getTotal() < measure2.getTotal()) {
                return 1;
            }
            else if (measure1.getTotal() > measure2.getTotal()) {
                return -1;
            }
            else {
                return -1 * timer1.getLabel().compareTo(timer2.getLabel());
            }
        }
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static SqlStatementSummaryDataMeasure extractDataMeasure(
            TaskTimer timer1) {
        return (SqlStatementSummaryDataMeasure)new ArrayList(timer1.getDataMeasures()).get(0);
    }

    protected static void setNbrRetainedSqlStatements(Integer nbrSqlStatements) {
        SqlStatementTimerRegistry.nbrRetainedSqlStatements = nbrSqlStatements;
    }

    protected static void setSqlRetentionTimeInMillis(Long sqlRetentionTimeInMillis) {
        SqlStatementTimerRegistry.sqlRetentionTimeInMillis = sqlRetentionTimeInMillis;
    }
    
}
