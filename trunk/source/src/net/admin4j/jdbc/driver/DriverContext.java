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

/**
 * Describes underlying JDBC driver in use.
 * @author D. Ashmore
 * @since 1.0.1
 */
public interface DriverContext {
    
    public String getDriverClassname();
    
    public void setDriverClassname(String driverClassname);
    
    public Integer getDriverMajorVersion();
    
    public void setDriverMajorVersion(Integer driverMajorVersion);
    
    public Integer getDriverMinorVersion();
    
    public void setDriverMinorVersion(Integer driverMinorVersion);
    
    public String getPoolName();
    
    public void setPoolName(String poolName);

}