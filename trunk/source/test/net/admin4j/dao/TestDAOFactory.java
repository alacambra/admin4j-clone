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

import junit.framework.Assert;
import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.config.Admin4JConfiguration.StorageFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDAOFactory {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        Admin4JConfiguration.setExceptionInformationStorageFormat(null);
        try {
            DAOFactory.getExceptionInfoDAO();
            Assert.fail("Should have blown up.");
        }
        catch (Exception e) {
            // NoOp -- expected
        }
        
        Admin4JConfiguration.setExceptionInformationStorageFormat(StorageFormat.XML);
        Assert.assertTrue("DAO returned", DAOFactory.getExceptionInfoDAO() != null);
        
        Admin4JConfiguration.setPerformanceInformationStorageFormat(null);
        try {
            DAOFactory.getTaskTimerDAO();
            Assert.fail("Should have blown up.");
        }
        catch (Exception e) {
            // NoOp -- expected
        }
        
        Admin4JConfiguration.setPerformanceInformationStorageFormat(StorageFormat.XML);
        Assert.assertTrue("DAO returned", DAOFactory.getTaskTimerDAO() != null);
        
        new DAOFactory();
    }

}
