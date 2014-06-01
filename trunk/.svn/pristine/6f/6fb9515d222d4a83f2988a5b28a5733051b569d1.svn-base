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

import java.util.Collection;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Basic TaskTimer implementation.
 * @author D. Ashmore
 * @since 1.0
 */
public class BasicTaskTimer implements TaskTimer {
    
    private static final long serialVersionUID = -6898638441458870161L;
    private Collection<DataMeasure> dataMeasures;
    private ThreadLocal<Long> beginTime = new ThreadLocal<Long>();
    private String label;
    
    public BasicTaskTimer(String label, Collection<DataMeasure> dataMeasures) {
        Validate.notNull(dataMeasures, "Null data measure collection not allowed.");
        Validate.notEmpty(label, "Null or blank label not allowed.");
        this.dataMeasures = dataMeasures;
        this.label = label;
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.TaskTimer#stop()
     */
    public void stop() {
        Long bTime = this.beginTime.get();
        if (bTime == null) {
            throw new Admin4jRuntimeException("Can't call stop() on a timer that hasn't been started.");            
        }
        
        Long timing = System.currentTimeMillis() - bTime;
        for (DataMeasure measure: this.dataMeasures) {
            measure.addNumber(timing);
        }

    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.TaskTimer#start()
     */
    public void start() {
       this.beginTime.set(System.currentTimeMillis());
        
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.TaskTimer#getLabel()
     */
    public String getLabel() {
        return label;
    }

    public Collection<DataMeasure> getDataMeasures() {
        return dataMeasures;
    }
    
    protected Long getBeginTime() {
        return this.beginTime.get();
    }

}
