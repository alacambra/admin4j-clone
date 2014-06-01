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
package net.admin4j.util;

import java.util.Comparator;

import net.admin4j.deps.commons.beanutils.BeanComparator;
import net.admin4j.deps.commons.lang3.Validate;

@SuppressWarnings("rawtypes")
public class CompositeBeanComparator implements Comparator {
    
    private BeanComparator[] comparator;
    
    public CompositeBeanComparator(String[] properties) {
        Validate.notEmpty("Null or empty properties not allowed.");
        comparator = new BeanComparator[properties.length];
        
        for (int i = 0; i < properties.length; i++) {
            comparator[i] = new BeanComparator(properties[i]);
        }
    }

    public int compare(Object obj1, Object obj2) {
        int answer = 0;
        for (int i = 0; i < comparator.length; i++) {
            answer = comparator[i].compare(obj1, obj2);
            
            if (answer != 0) {
                return answer;
            }
        }
        
        return answer;
    }

}
