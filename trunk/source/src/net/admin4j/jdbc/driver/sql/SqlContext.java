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
package net.admin4j.jdbc.driver.sql;

/**
 * Describes an object that has a SQL Statement context
 * @author D. Ashmore
 *
 */
public interface SqlContext {
    
    public String getSqlText();
    
    public void setSqlText(String sqlText);
    
    public StackTraceElement[] getExecutionStack();
    
    public void setExecutionStack(StackTraceElement[] stack);

}
