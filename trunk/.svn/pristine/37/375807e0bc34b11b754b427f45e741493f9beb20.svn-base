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

import java.util.Collection;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.entity.ExecutionStack;
import net.admin4j.timer.BasicTaskTimer;
import net.admin4j.timer.DataMeasure;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Times SQL Statement executions.
 * @author D. Ashmore
 * @since 1.0.1
 */
public class SqlTaskTimer extends BasicTaskTimer {
    
    private static final long serialVersionUID = -1731339518240664632L;
    private ThreadLocal<ExecutionStack> executionStack = new ThreadLocal<ExecutionStack>();
    private DriverContext driverContext;

    public SqlTaskTimer(String sqlText
            , Collection<DataMeasure> dataMeasures
            , DriverContext driverContext
            , ExecutionStack executionStack) {
        super(sqlText, dataMeasures);
        Validate.notNull(executionStack, "Null executionstack not allowed.");
        Validate.notNull(driverContext, "Null driverContext not allowed.");
        this.executionStack.set(executionStack);
        this.driverContext = driverContext;
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.BasicTaskTimer#stop()
     */
    @Override
    public void stop() {
        Long bTime = this.getBeginTime();
        if (bTime == null) {
            throw new Admin4jRuntimeException("Can't call stop() on a timer that hasn't been started.");            
        }
        
        Long timing = System.currentTimeMillis() - bTime;
        for (DataMeasure measure: this.getDataMeasures()) {
            ((SqlStatementSummaryDataMeasure)measure).addNumber(timing, executionStack.get());
        }
    }

    public DriverContext getDriverContext() {
        return driverContext;
    }

}
