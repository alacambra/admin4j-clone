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
package net.admin4j.vo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.admin4j.BeanTester;
import net.admin4j.deps.commons.lang3.JavaVersion;
import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.entity.ExceptionInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestValueObjects {
    
    private BeanTester beanTester;

    @Before
    public void setUp() throws Exception {
        beanTester = new BeanTester();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPerformanceSummaryVO() throws Exception {
        beanTester.testBean(PerformanceSummaryVO.class);
    }
    
    @Test
    public void testDataMeasurementSummaryVO() throws Exception {
        //beanTester.addTestValueSet(Double.class, new Object[]{Double.valueOf(1.00)});
        beanTester.addTestValueSet(Number.class, new Object[]{Double.valueOf(1.00)});
        beanTester.testBean(new DataMeasurementSummaryVO());
    }
    
    @Test
    public void testExceptionStatisticVO() throws Exception {
        ExceptionStatisticVO wrapper = new ExceptionStatisticVO(
                new ExceptionInfo("java.lang.Exception", Thread.currentThread().getStackTrace(), "foo"));
        beanTester.addTestValueSet(Class.class, new Object[]{Exception.class});
        beanTester.addExcludedField("firstOccuranceDt");
        beanTester.addExcludedField("lastOccuranceDt");
        beanTester.addExcludedField("totalNbrExceptions");
        beanTester.testBean(wrapper);
        
        Assert.assertTrue("ExceptionStatisticVO exception point test", wrapper.getExceptionPoint() != null);
        Assert.assertTrue("ExceptionStatisticVO id test", wrapper.getId() == null);
        
        Assert.assertTrue("ExceptionStatisticVO firstOccuranceDt test", wrapper.getFirstOccuranceDt() != null);
        Assert.assertTrue("ExceptionStatisticVO lastOccuranceDt test", wrapper.getLastOccuranceDt() != null);
        Assert.assertTrue("ExceptionStatisticVO totalNbrExceptions test", wrapper.getTotalNbrExceptions() == 0);
    }
    
    @Test
    public void testFileWrapperVO() throws Exception {
        FileWrapperVO wrapper = new FileWrapperVO(new File("foo"));
        beanTester.testBean(wrapper);
        
        Assert.assertTrue("FileWrapperVO name test", wrapper.getName() != null);
        Assert.assertTrue("FileWrapperVO path test", wrapper.getPath() != null);
        Assert.assertTrue("FileWrapperVO executable test", !wrapper.isExecutable());
        
        if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_6)) {
            Assert.assertTrue("FileWrapperVO executable test", !(new FileWrapperVO(new File("foo.bat")).isExecutable()));
        }
    }
    
    @Test
    public void testLoggerProductVO() throws Exception {
        beanTester.addTestValueSet(List.class, new Object[]{new ArrayList<LoggerVO>()});
        beanTester.testBean(LoggerProductVO.class);
    }
    
    @Test
    public void testLoggerVO() throws Exception {
        LoggerVO wrapper = new LoggerVO("foo","foo","foo");
        beanTester.testBean(wrapper);
        
        Assert.assertTrue("LoggerVO compareTo test", wrapper.compareTo(wrapper) == 0);
    }
    
    @Test
    public void testMemoryUtilizationVO() throws Exception {
        beanTester.testBean(new MemoryUtilizationVO(0,0,0));
    }
    
    @Test
    public void testSqlStatementPerformanceSummaryVO() throws Exception {
        DataMeasurementSummaryVO summary = new DataMeasurementSummaryVO();
        summary.setLabel("foo-foo");
        
        SqlStatementPerformanceSummaryVO wrapper = new SqlStatementPerformanceSummaryVO(summary);
        beanTester.testBean(wrapper);
        
        Assert.assertTrue("SqlStatementPerformanceSummaryVO getDriverClass test", wrapper.getDriverClass() != null);
        Assert.assertTrue("SqlStatementPerformanceSummaryVO getMajorVersion test", wrapper.getMajorVersion() == null);
        Assert.assertTrue("SqlStatementPerformanceSummaryVO getMinorVersion test", wrapper.getMinorVersion() == null);
        Assert.assertTrue("SqlStatementPerformanceSummaryVO getPoolName test", wrapper.getPoolName() .equals("none"));
    }

}
