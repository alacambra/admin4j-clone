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
package net.admin4j.hotspot;

import java.util.List;
import java.util.Map;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.entity.ExecutionPoint;

/**
 * General utilities needed for the HotSpot display. 
 * @author D. Ashmore
 *
 */
public class HotSpotUtils {
    
    private Map<StackTraceElement, ExecutionPoint> executionMap;
    
    public HotSpotUtils(Map<StackTraceElement, ExecutionPoint> executionMap) {
        Validate.notNull(executionMap, "Null executionMap not allowed.");
        this.executionMap = executionMap;
    }
    
    public double computeExecutionPct(List<ExecutionPoint> pointList, ExecutionPoint point) {
        long total = 0;
        for (ExecutionPoint p: pointList) {
            total += p.getNbrExecutions();
        }
        
        return ((double)point.getNbrExecutions() / (double)total) * (double)100.000;
    }
    
    public ExecutionPoint findExecutionPoint(StackTraceElement element) {
        return this.executionMap.get(element);
    }

}
