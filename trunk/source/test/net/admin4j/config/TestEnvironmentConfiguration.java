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
package net.admin4j.config;


/**
 * Allows test classes to cust set configuration items.
 * @author D. Ashmore
 *
 */
public class TestEnvironmentConfiguration extends Admin4JConfiguration {
    
    public static void setExceptionInformationStorageFormat(
            StorageFormat exceptionInformationStorageFormat) {
        Admin4JConfiguration.setExceptionInformationStorageFormat(exceptionInformationStorageFormat);
    }
    
    public static void setExceptionInformationXmlFileName(
            String exceptionInformationXmlFileName) {
        Admin4JConfiguration.setExceptionInformationXmlFileName(exceptionInformationXmlFileName);
    }
    
    public static void setPerformanceInformationStorageFormat(
            StorageFormat performanceInformationStorageFormat) {
        Admin4JConfiguration.setPerformanceInformationStorageFormat(performanceInformationStorageFormat);
    }
    
    public static void setPerformanceInformationXmlFileName(
            String performanceInformationXmlFileName) {
        Admin4JConfiguration.setPerformanceInformationXmlFileName(performanceInformationXmlFileName);
    }

}
