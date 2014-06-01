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

import java.io.Serializable;
import java.util.Collection;


/**
 * Used to time tasks so that performance measurements can be reported.
 * @author D. Ashmore
 * @since 1.0
 */
public interface TaskTimer extends Serializable {
    
    /**
     * Stops the timer.  This will except if the timer hasn't been started yet.
     */
    public void stop();
    
    /**
     * Starts the timer.
     */
    public void start();
    
    /**
     * Provides a label for the timer.
     * @return Label Meaningful label describing the activity timed.
     */
    public String getLabel();
    
    /**
     * Provides data measures from all timers that have complete timings.
     * @return
     */
    public Collection<DataMeasure> getDataMeasures();
}
