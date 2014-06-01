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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Provides generic utilities for Properties
 * @author D. Ashmore
 *
 */
public class PropertyUtils {
    
    /**
     * Supports property values having variable references.
     * 
     * <p>Properties can have variables of the syntax ${variable-name}.  Variables are resolved in the following sequence:</p>
     * <li>Variables are resolved against the property file itself.</li>
     * <li>Variables are resolved against system properties.</li>
     * 
     * <p>Example:  Propertires config.file=${user.dir}/foo.xml.  First, if the properties object has a definition for 'user.dir' it is used.
     * Otherwise, System.getProperty('user.dir') is used.  Null is used and a warning generated if the variable isn't defined.
     * In this case, if the user.dir is '/home/dashmore', the property value will be converted to '/home/dashmore/foo.xml'.</p>
     * @param props
     */
    public static Set<String> resolveVariableReferences(Properties props) {
        Set<String> propsWithRefsSet = findSettingsNeedingResolution(props);
        Set<String> variableSet;
        
        Set<String> unresolvedVariables = new HashSet<String>();
        String currentValue;
        String currentVariableValue;
        int nbrVariablesLastIteration = Integer.MAX_VALUE;
        
        while (propsWithRefsSet.size() > 0 && propsWithRefsSet.size() != nbrVariablesLastIteration) {
            nbrVariablesLastIteration = propsWithRefsSet.size();
            
            for (String propName: propsWithRefsSet) {
                currentValue = props.getProperty(propName);
                variableSet = findReferencedVariableNames(currentValue, 0);
                
                for (String variableName: variableSet) {
                    currentVariableValue = props.getProperty(variableName);
                    if (currentVariableValue == null) {
                        currentVariableValue = System.getProperty(variableName);
                    }
                    
                    if (currentVariableValue == null) {
                        currentVariableValue = "";
                        unresolvedVariables.add(variableName);
                    }
                                        
                    currentValue = replaceVariableWithValue(variableName, currentVariableValue, currentValue);
                    props.setProperty(propName, currentValue);
                }
            }
            
            
            
            propsWithRefsSet = findSettingsNeedingResolution(props);
        }
        
        return unresolvedVariables;
        
    }
    
    protected static Set<String> findSettingsNeedingResolution(Properties props) {
        Set<String> nameSet = new HashSet<String>();
        String value;
        int firstOffset;
        
        for (Object propName: props.keySet()) {
            value = props.getProperty( (String)propName);
            firstOffset = value.indexOf("${");
            if (firstOffset >= 0 && value.indexOf("}", firstOffset + 1) > 0) {
                nameSet.add( (String)propName);
            }
        }
        
        return nameSet;
    }
    
    protected static Set<String> findReferencedVariableNames(String value, int beginningOffset) {
        int firstOffset, lastOffset;
        Set<String> set = new HashSet<String>();
        firstOffset = value.indexOf("${", beginningOffset);
        lastOffset = value.indexOf("}", firstOffset + 1);
        
        if (firstOffset < 0 || lastOffset < 0) {
            return set;
        }
        
        set.add(value.substring(firstOffset + 2, lastOffset).trim());
        
        if (value.indexOf("${", lastOffset) > 0) {
            set.addAll(findReferencedVariableNames(value, lastOffset));
        }
        
        return set;
    }
    
    protected static String replaceVariableWithValue(String variableName, String variableValue, String baseString) {
        int firstOffset, lastOffset;
        StringBuffer buffer = new StringBuffer();
        int beginOffset = 0;        
        firstOffset = baseString.indexOf("${", beginOffset);
        String tempVariableName;
        int lastCopiedOffset = 0;
        
        lastOffset = 0;
        while (firstOffset >= 0 && lastCopiedOffset < baseString.length()) {
            buffer.append(baseString.substring(lastCopiedOffset, firstOffset));
            lastCopiedOffset = firstOffset;
            
            lastOffset = baseString.indexOf("}", firstOffset + 1);
            tempVariableName = baseString.substring(firstOffset + 2, lastOffset).trim();
            if (variableName.equals(tempVariableName)) {
                buffer.append(variableValue);
            }
            else {
                if (lastOffset + 1 > baseString.length()) {
                    buffer.append(baseString.substring(lastCopiedOffset));
                }
                else buffer.append(baseString.substring(lastCopiedOffset, lastOffset + 1));
            }
            lastCopiedOffset = lastOffset + 1;
            
            firstOffset = baseString.indexOf("${", lastCopiedOffset);
        }
        
        if (lastCopiedOffset < baseString.length()) {
            buffer.append(baseString.substring(lastCopiedOffset));
        }
        
        return buffer.toString();
    }

}
