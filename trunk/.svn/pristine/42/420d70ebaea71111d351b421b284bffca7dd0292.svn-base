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

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import net.admin4j.deps.commons.lang3.StringUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPropertyConfigurator {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPropertyConfigurator() throws Exception {
        // By default, should not except
        PropertyConfigurator.configure();
        PropertyConfigurator.setConfigurator(null);
        
        System.setProperty("admin4j.configuration.file", "foo.xml");
        boolean exceptionOccurred = false;
        try {PropertyConfigurator.configure();}
        catch (Throwable re) {
            exceptionOccurred = true;
        }
        
        assertTrue("false config test", !exceptionOccurred);
        
        reinitConfig();
        
        Properties configProps = new Properties();
        configProps.setProperty("exception.info.storage.format", "xml");
        configProps.setProperty("exception.info.xml.file", "xml.xml");
        configProps.setProperty("performance.info.storage.format", "xml");
        configProps.setProperty("performance.info.xml.file", "xml.xml");
        configProps.setProperty("additional.log.manager.classes", "foo");
        configProps.setProperty("base.freemarker.template.dir", "..");
        configProps.setProperty("exception.time.tracking.in.days", "12");
        
        configProps.setProperty("exception.exempted.exception.types", "Mytype");
        configProps.setProperty("file.explorer.restrict.to.base.dir", "true");
        configProps.setProperty("file.explorer.restrict.from.exec", "xml");
        configProps.setProperty("file.explorer.restrict.from.write", "xml");
        configProps.setProperty("file.explorer.base.dir.name", "..");
        
        configProps.setProperty("hotspot.sleep.interval.millis", "30000");
        configProps.setProperty("memory.sleep.interval.millis", "30000");
        configProps.setProperty("memory.threshold.pct", "90");
        configProps.setProperty("memory.nbr.intervals.between.warnings", "5");
        configProps.setProperty("memory.nbr.low.watermark.intervals", "12");
        
        configProps.setProperty("memory.low.watermark.monitor.interval.millis", "90000");
        configProps.setProperty("thread.sleep.interval.millis", "30000");
        configProps.setProperty("thread.max.blocked.threads", "2");
        configProps.setProperty("web.transaction.error.logger.name", "foo");
        configProps.setProperty("error.notification.time.interval.millis", "100000");
        
        configProps.setProperty("error.exempted.exception.types", "xml");
        configProps.setProperty("web.transaction.performance.notification.threshold.in.millis", "100000");
        configProps.setProperty("sql.nbr.retained.sql.statements", "50");
        configProps.setProperty("sql.retention.time.in.millis", "100000");
        configProps.setProperty("usage.sleep.interval.millis", "100000");
        configProps.setProperty("usage.alert.levels", "50,100,150");
        configProps.setProperty("exception", "${foo}");
        
        configProps.setProperty("request.history.nbr.retained", "50");
        
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        configProps.store(outstream, "Just testing");
        
        PropertyConfigurator config = new PropertyConfigurator();
        config.assignProps(new ByteArrayInputStream(outstream.toByteArray()));
        
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getErrorExemptedExceptionTypes()));
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getExceptionExemptedExceptionTypes()));
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getExceptionInformationXmlFileName()));
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getFileExplorerBaseDirName()));
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getPerformanceInformationXmlFileName()));
        
        Assert.assertTrue( !StringUtils.isEmpty(Admin4JConfiguration.getWebTransactionErrorLoggerName()));
        
        Assert.assertTrue( Admin4JConfiguration.getAdditionalLogManagerClassNames() != null);        
        Assert.assertTrue( Admin4JConfiguration.getFileExplorerRestrictFromExec() != null);
        Assert.assertTrue( Admin4JConfiguration.getFileExplorerRestrictFromWrite() != null);
        Assert.assertTrue( Admin4JConfiguration.getFileExplorerRestrictToBaseDir() != null);
        Assert.assertTrue( Admin4JConfiguration.getBaseFreemarkerTemplateDirectory() != null);
        
        Assert.assertTrue( Admin4JConfiguration.getDefaultNotifier() == null);
        Assert.assertTrue( Admin4JConfiguration.getErrorNotificationTimeIntervalMillis() != null);
        Assert.assertTrue( Admin4JConfiguration.getExceptionInformationStorageFormat() != null);
        Assert.assertTrue( Admin4JConfiguration.getExceptionTimeTrackingInDays() != null);
        Assert.assertTrue( Admin4JConfiguration.getHotSpotSleepIntervalMillis() != null);
        
        Assert.assertTrue( Admin4JConfiguration.getMemoryLowWatermarkMonitorIntervalInMillis() != null);
        Assert.assertTrue( Admin4JConfiguration.getMemoryNbrIntervalsBetweenWarnings() != null);
        Assert.assertTrue( Admin4JConfiguration.getMemoryNbrLowWatermarkIntervals() != null);
        Assert.assertTrue( Admin4JConfiguration.getMemorySleepIntervalMillis() != null);
        Assert.assertTrue( Admin4JConfiguration.getMemoryThresholdPct() != null);
        
        Assert.assertTrue( Admin4JConfiguration.getPerformanceInformationStorageFormat() != null);
        Assert.assertTrue( Admin4JConfiguration.getSqlNbrRetainedSqlStatements() != null);
        Assert.assertTrue( Admin4JConfiguration.getSqlRetentionTimeInMillis() != null);
        Assert.assertTrue( Admin4JConfiguration.getThreadMaxBlockedThreads() != null);
        Assert.assertTrue( Admin4JConfiguration.getThreadSleepIntervalMillis() != null);
        Assert.assertTrue( Admin4JConfiguration.getWebTransactionPerformanceNotificationThresholdInMillis() != null);
        
        Assert.assertTrue( Admin4JConfiguration.getConcurrentUsageAlertLevels() != null);
        Assert.assertTrue( Admin4JConfiguration.getConcurrentUsageSleepIntervalMillis() != null);
        
        Assert.assertTrue( Admin4JConfiguration.getRequestHistoryNbrRetained() != null);
        
        reinitConfig();
        
        
    }
    
    private void reinitConfig() {
        Admin4JConfiguration.setBaseFreemarkerTemplateDirectory(null);
        Admin4JConfiguration.setDefaultNotifier(null);
        Admin4JConfiguration.setErrorExemptedExceptionTypes(null);
        Admin4JConfiguration.setErrorNotificationTimeIntervalMillis(null);
        Admin4JConfiguration.setExceptionExemptedExceptionTypes(null);
        
        Admin4JConfiguration.setExceptionInformationStorageFormat(null);
        Admin4JConfiguration.setExceptionInformationXmlFileName(null);
        Admin4JConfiguration.setExceptionTimeTrackingInDays(null);
        Admin4JConfiguration.setFileExplorerBaseDirName(null);
        Admin4JConfiguration.setFileExplorerRestrictFromExec(null);
        
        Admin4JConfiguration.setFileExplorerRestrictFromWrite(null);
        Admin4JConfiguration.setFileExplorerRestrictToBaseDir(null);
        Admin4JConfiguration.setHotSpotSleepIntervalMillis(null);
        Admin4JConfiguration.setMemoryLowWatermarkMonitorIntervalInMillis(null);
        Admin4JConfiguration.setMemoryNbrIntervalsBetweenWarnings(null);
        
        Admin4JConfiguration.setMemoryNbrLowWatermarkIntervals(null);
        Admin4JConfiguration.setMemorySleepIntervalMillis(null);
        Admin4JConfiguration.setMemoryThresholdPct(null);
        Admin4JConfiguration.setPerformanceInformationStorageFormat(null);
        Admin4JConfiguration.setPerformanceInformationXmlFileName(null);
        
        Admin4JConfiguration.setSqlNbrRetainedSqlStatements(null);
        Admin4JConfiguration.setSqlRetentionTimeInMillis(null);
        Admin4JConfiguration.setThreadMaxBlockedThreads(null);
        Admin4JConfiguration.setThreadSleepIntervalMillis(null);
        Admin4JConfiguration.setWebTransactionErrorLoggerName(null);
        
        Admin4JConfiguration.setWebTransactionPerformanceNotificationThresholdInMillis(null);
        
        Admin4JConfiguration.setConcurrentUsageAlertLevels(null);
        Admin4JConfiguration.setConcurrentUsageSleepIntervalMillis(null);
    }

}
