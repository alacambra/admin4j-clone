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
package net.admin4j.dao;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.config.Admin4JConfiguration.StorageFormat;
import net.admin4j.dao.xml.ExceptionInfoDAOXml;
import net.admin4j.dao.xml.TaskTimerDAOXml;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Provides implemented DAO based on Configuration.
 * @author D. Ashmore
 * @since 1.0
 */
public class DAOFactory {
    
    public static ExceptionInfoDAO getExceptionInfoDAO() {
        if (StorageFormat.XML.equals(Admin4JConfiguration.getExceptionInformationStorageFormat())) {
            return new ExceptionInfoDAOXml();
        }
        throw new Admin4jRuntimeException("Invalid Exception Storage Format")
            .addContextValue("Format", Admin4JConfiguration.getExceptionInformationStorageFormat());
    }
    
    public static TaskTimerDAO getTaskTimerDAO() {
        if (StorageFormat.XML.equals(Admin4JConfiguration.getPerformanceInformationStorageFormat())) {
            return new TaskTimerDAOXml();
        }
        throw new Admin4jRuntimeException("Invalid Performance Timing Storage Format")
            .addContextValue("Format", Admin4JConfiguration.getPerformanceInformationStorageFormat());
    }

}
