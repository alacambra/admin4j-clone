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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSqlStatementTimerRegistry {
    
    private DriverContext driverContext;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        driverContext = new DriverContextImpl();
        
        driverContext.setDriverClassname("org.foo.driver");
        driverContext.setDriverMajorVersion(1);
        driverContext.setDriverMinorVersion(1);
        driverContext.setPoolName("fooDB");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBasic() throws Exception {
        SqlStatementTimerRegistry.setNbrRetainedSqlStatements(3);
        SqlStatementTimerRegistry.setSqlRetentionTimeInMillis(50L);
        
        SqlStatementTimerFactory.start("t1", driverContext, Thread.currentThread().getStackTrace()).stop();
        SqlStatementTimerFactory.start("t2", driverContext, Thread.currentThread().getStackTrace()).stop();
        SqlStatementTimerFactory.start("t3", driverContext, Thread.currentThread().getStackTrace()).stop();
        SqlStatementTimerFactory.start("t4", driverContext, Thread.currentThread().getStackTrace()).stop();
        SqlStatementTimerFactory.start("t5", driverContext, Thread.currentThread().getStackTrace()).stop();

        Assert.assertTrue(SqlStatementTimerRegistry.getDataSummaryMap().size() == 3);
        
        Thread.sleep(100);
        Assert.assertTrue(SqlStatementTimerRegistry.getDataSummaryMap().size() == 0);
    }

}
