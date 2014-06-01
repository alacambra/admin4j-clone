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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.entity.ExecutionStack;
import net.admin4j.timer.SummaryDataMeasure;

/**
 * Tracks timing statistics for a SQL statement
 * @author D. Ashmore
 * @since 1.0.1
 */
public class SqlStatementSummaryDataMeasure extends SummaryDataMeasure {

    private static final long serialVersionUID = 2697140795168204384L;
    private Map<ExecutionStack,ExecutionStack> executionStackSet 
        = new ConcurrentHashMap<ExecutionStack,ExecutionStack>();

    public SqlStatementSummaryDataMeasure(long firstObservationTime, ExecutionStack executionStack) {
        super(firstObservationTime);
        if (executionStack != null) {
            executionStackSet.put(executionStack, executionStack);
        }
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.SummaryDataMeasure#addNumber(java.lang.Number)
     */
    @Override
    public synchronized void addNumber(Number number) {
        this.addNumber(number, null);
    }
    
    public synchronized void addNumber(Number number, ExecutionStack executionStack) {
        super.addNumber(number);
        
        if ( executionStack != null && !executionStackSet.containsKey(executionStack)) {
            executionStackSet.put(executionStack, executionStack);
        }
    }
    
    public synchronized Set<ExecutionStack> getExecutionStackSet() {
        return new HashSet<ExecutionStack>(executionStackSet.keySet());
    }

}
