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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.PropertyUtils;
import net.admin4j.util.notify.NotifierUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Will configure Admin4J from a properties file.
 * 
 * <p>Configuration properties can be specified in two ways:</p>
 * <li>By setting system property 'admin4j.configuration.file' equal to a properties file name containing
 * the admin4j configuration</li>
 * <li>Placing a file called 'admin4j.properties' in the classpath.</li>
 * 
 * <p>If both the system property is specified and an admin4j.properties file is placed in the classpath, the system property
 * takes precedence.</p>
 * 
 * <p>Allowed properties are as follows:</p>
 * <li>exception.info.storage.format - Optional (xml/database).  Default XML.</li>
 * <li>exception.info.xml.file - Optional - default none; exception info won't be stored and won't persist between recycles.</li>
 * <li>performance.info.storage.format - Optional (xml/database).  Default XML.</li>
 * <li>performance.info.xml.file - Optional - default none; performance info won't be stored and won't persist between recycles.</li>
 * <li>additional.log.manager.classes - Optional - default none; Comma delimited list of classes that implement LogManager interface.</li>
 * <li>base.freemarker.template.dir - Optional - default none; Specifies a development directory containing admin4j FTL.  Useful in development.</li>
 * <li>exception.time.tracking.in.days - Optional - default @see net.admin4j.exception.ExceptionTrackerCleanupTask.  Specifies when tracked exceptions are purged if not encountered.</li>
 * <li>exception.exempted.exception.types - Optional - default none.  Comma delimited list specifying exception classes that are not tracked.</li>
 * <li>file.explorer.restrict.to.base.dir - (true/false) Optional - default true.  If true, file exploring will be restricted to the base directory and its subdirectories.</li>
 * <li>file.explorer.restrict.from.exec - (true/false) Optional - default true.  If true, file exploring will not permit execution of those files that are executable.</li>
 * <li>file.explorer.restrict.from.write - (true/false) Optional - default true.  If true, file exploring will not allow file uploads.</li>
 * <li>file.explorer.base.dir.name - Optional - default ${user.dir}.  Specifies the directory that will originate file exploring.</li>
 * <li>hotspot.sleep.interval.millis - Optional - default @see net.admin4j.ui.servlets.HotSpotDisplayServlet.  Length of HotSpot sleep interval in milliseconds.</li>
 * <li>memory.sleep.interval.millis - Optional - default @see net.admin4j.ui.servlets.MemoryMonitorStartupServlet.  Length of Memory monitor sleep interval in milliseconds.</li>
 * <li>memory.threshold.pct - Optional - default @see net.admin4j.ui.servlets.MemoryMonitorStartupServlet.  Percent memory usage required to trigger a notification.</li>
 * <li>memory.nbr.intervals.between.warnings - Optional - default @see net.admin4j.ui.servlets.MemoryMonitorStartupServlet.  Number of intervals between notifications should the memory problem persist.</li>
 * <li>memory.nbr.low.watermark.intervals - Optional - default @see net.admin4j.ui.servlets.MemoryMonitorStartupServlet. Number of low watermark observations reported with a low memory notice.</li>
 * <li>memory.low.watermark.monitor.interval.millis - Optional - default @see net.admin4j.ui.servlets.MemoryMonitorStartupServlet. Millis between low watermark observations.</li>
 * <li>thread.sleep.interval.millis - Optional - default @see net.admin4j.ui.servlets.ThreadMonitorStartupServlet.  Length of Thread monitor sleep interval in milliseconds.</li>
 * <li>thread.max.blocked.threads - Optional - default @see net.admin4j.ui.servlets.ThreadMonitorStartupServlet.  Number of blocked threads needed to trigger notification.</li>
 * <li>web.transaction.error.logger.name - Optional - default @see net.admin4j.ui.filters.ErrorLoggingFilter.  Logger name used for web transaction errors.</li>
 * <li>web.transaction.performance.notification.threshold.in.millis - Optional - default none.  If set, web transactions with execution time greater than or equal to this setting will result in notification.</li>
 * <li>error.notification.time.interval.millis - Optional - default none.  If set, web transaction errors of the same type will be surpressed for the given time period.</li>
 * <li>error.exempted.exception.types - Optional - default none.  Comma delimited list specifying exception classes that will not result in notification.</li>
 * <li>sql.nbr.retained.sql.statements - Optional - default 50.  Number of top resource intensive SQL statements retained.</li>
 * <li>sql.retention.time.in.millis - Optional - default 6 hrs.  Maximum time SQL statements are retained since last executed.</li>
 * <li>usage.sleep.interval.millis -- Optional.  Default 30000 (30 sec).  Amount of time in millis monitor will sleep between checks.</li>
 * <li>usage.alert.levels -- Optional.  Default 100. Comma-delimited list of threshold levels for which notices will be sent.  Example: 10,50,100.</li>
 * <li>request.history.nbr.retained -- Optional.  Default 5. The number of requests in the request history section for error logs and emails.</li>
 * 
 * <p>Note: Properties can have variable references with syntax ${variable-name}.  Properties are resolved with rules documented
 * with PropertyUtils</p>
 * @author D. Ashmore
 * @since 1.0
 * @see net.admin4j.util.PropertyUtils#resolveVariableReferences(Properties)
 */
class PropertyConfigurator {
    
    private static Logger logger = LoggerFactory.getLogger(PropertyConfigurator.class);
    private static PropertyConfigurator configurator;
    
    protected PropertyConfigurator () {}
    
    public synchronized static void configure() {
        InputStream inStream = null;
        boolean configurationFound = false;
        
        if (configurator == null) {
            configurator = new PropertyConfigurator();
        }
        else return;
        
        // Attempt to load from property setting.
        String propsFileName = System.getProperty("admin4j.configuration.file");
        if ( !StringUtils.isEmpty(propsFileName)) {
            try {
                inStream = new FileInputStream(propsFileName);
                logger.info("Admin4J properties loaded from file " + propsFileName);
                configurator.assignProps(inStream);
                configurationFound = true;
                return; // don't override with any configuration in the classpath.
               
            } catch (FileNotFoundException e) {
                logger.error("Error reading configuration file", new Admin4jRuntimeException(e)
                    .addContextValue("admin4j.configuration.file", propsFileName));
            } catch (IOException e) {
                logger.error("Error reading configuration file", new Admin4jRuntimeException(e)
                    .addContextValue("admin4j.configuration.file", propsFileName));
            }
        }
        
        // Attempt to load from classpath.
        inStream = Admin4JConfiguration.class.getClassLoader().getResourceAsStream("admin4j.properties");
        if (inStream != null) {
            try {
                configurator.assignProps(inStream);
                logger.info("Admin4J properties loaded from file admin4j.properties in the classpath");
                configurationFound = true;
            }
            catch (IOException e) {
                logger.error("Error reading admin4j.properties", new Admin4jRuntimeException(e));
            }           
        }
        
        if (!configurationFound) {
            logger.warn("Admin4J not configured properly.");
        }
    }

    /**
     * Note:  protected to support unit test.
     * @param inStream
     * @throws IOException
     */
    protected void assignProps(InputStream inStream) throws IOException {
        Properties admin4JProps = new Properties();
        admin4JProps.load(inStream);
        Set<String> unresolvedVariableSet = PropertyUtils.resolveVariableReferences(admin4JProps);
        for (String unresolvedVar: unresolvedVariableSet) {
            logger.warn("Properties file with unresolved variable {}", unresolvedVar);
        }
        this.assignProps(admin4JProps);
    }
    
    private void assignProps(Properties admin4JProps) {
        String value;
        
        value = admin4JProps.getProperty("exception.info.storage.format");
        if ( !StringUtils.isEmpty(value)) {
            if ("xml".equalsIgnoreCase(value)) {
                logger.info("Exception Info storage set to XML format.");
                Admin4JConfiguration.setExceptionInformationStorageFormat(Admin4JConfiguration.StorageFormat.XML);
            }
//            else if ("database".equalsIgnoreCase(value)) {
//                logger.info("Exception Info storage set to database format.");
//                Admin4JConfiguration.setExceptionInformationStorageFormat(Admin4JConfiguration.StorageFormat.DATABASE);
//            }
            else {
                throw new Admin4jRuntimeException("Invalid exception.info.storage.format")
                    .addContextValue("exception.info.storage.format", value);
            }
        }
        
        value = admin4JProps.getProperty("exception.info.xml.file");
        if ( !StringUtils.isEmpty(value)) {
            logger.info("Exception Info storage set to file: " + value);
            Admin4JConfiguration.setExceptionInformationXmlFileName(value);
        }
        
        value = admin4JProps.getProperty("performance.info.storage.format");
        if ( !StringUtils.isEmpty(value)) {
            if ("xml".equalsIgnoreCase(value)) {
                logger.info("Performance Info storage set to XML format.");
                Admin4JConfiguration.setPerformanceInformationStorageFormat(Admin4JConfiguration.StorageFormat.XML);
            }
//            else if ("database".equalsIgnoreCase(value)) {
//                logger.info("Performance Info storage set to database format.");
//                Admin4JConfiguration.setPerformanceInformationStorageFormat(Admin4JConfiguration.StorageFormat.DATABASE);
//            }
            else {
                throw new Admin4jRuntimeException("Invalid performance.info.storage.format")
                    .addContextValue("performance.info.storage.format", value);
            }
        }
        
        value = admin4JProps.getProperty("performance.info.xml.file");
        if ( !StringUtils.isEmpty(value)) {
            logger.info("Performance Info storage set to file: " + value);
            Admin4JConfiguration.setPerformanceInformationXmlFileName(value);
        }
        
        try {
            Admin4JConfiguration.setDefaultNotifier(NotifierUtils.configure("default.notifier", admin4JProps));
        } catch (Exception e) {
            throw new Admin4jRuntimeException("Invalid default.notifier.class", e)
            .addContextValue("default.notifier.class", value);
        } 
        if (Admin4JConfiguration.getDefaultNotifier() == null) {
            logger.info("Default Notifier not set");
        }
        else {
            logger.info("Default Notifier set to class: " + Admin4JConfiguration.getDefaultNotifier().getClass().getName());
        }
        
        value = admin4JProps.getProperty("additional.log.manager.classes");
        if ( !StringUtils.isEmpty(value)) {
            StringTokenizer logManagerTok = new StringTokenizer(value, ",");
            String logManagerClassName;
            while (logManagerTok.hasMoreTokens()) {
                logManagerClassName = logManagerTok.nextToken();
                if ( !StringUtils.isEmpty(logManagerClassName)) {
                    Admin4JConfiguration.getAdditionalLogManagerClassNames().add(logManagerClassName.trim());
                    logger.info("Log Manager class {} added to configuration", logManagerClassName);
                }
            }
        }
        
        value = admin4JProps.getProperty("base.freemarker.template.dir");
        if ( !StringUtils.isEmpty(value)) {
            File templateDir = new File(value);
            if ( !templateDir.canRead()) {
                throw new Admin4jRuntimeException("base.freemarker.template.dir not readable")
                .addContextValue("base.freemarker.template.dir", value);
            }
            if ( !templateDir.isDirectory()) {
                throw new Admin4jRuntimeException("base.freemarker.template.dir not a directory")
                .addContextValue("base.freemarker.template.dir", value);
            }
            Admin4JConfiguration.setBaseFreemarkerTemplateDirectory(templateDir);
            logger.info("Freemarker Template Directory set to {}", value);
        }
        
        value = admin4JProps.getProperty("exception.time.tracking.in.days");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setExceptionTimeTrackingInDays(toInteger(value, "exception.time.tracking.in.days"));
        }
        
        value = admin4JProps.getProperty("exception.exempted.exception.types");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setExceptionExemptedExceptionTypes(value);
        }
        
        value = admin4JProps.getProperty("file.explorer.restrict.to.base.dir");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setFileExplorerRestrictToBaseDir(toBoolean(value, "file.explorer.restrict.to.base.dir"));
        }
        value = admin4JProps.getProperty("file.explorer.restrict.from.exec");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setFileExplorerRestrictFromExec(toBoolean(value, "file.explorer.restrict.from.exec"));
        }
        value = admin4JProps.getProperty("file.explorer.restrict.from.write");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setFileExplorerRestrictFromWrite(toBoolean(value, "file.explorer.restrict.from.write"));
        }
        value = admin4JProps.getProperty("file.explorer.base.dir.name");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setFileExplorerBaseDirName(value);
        }
        
        value = admin4JProps.getProperty("hotspot.sleep.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setHotSpotSleepIntervalMillis(toInteger(value, "hotspot.sleep.interval.millis"));
        }
        
        value = admin4JProps.getProperty("memory.sleep.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setMemorySleepIntervalMillis(toLong(value, "memory.sleep.interval.millis"));
        }
        value = admin4JProps.getProperty("memory.threshold.pct");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setMemoryThresholdPct(toInteger(value, "memory.threshold.pct"));
        }
        value = admin4JProps.getProperty("memory.nbr.intervals.between.warnings");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setMemoryNbrIntervalsBetweenWarnings(toInteger(value, "memory.nbr.intervals.between.warnings"));
        }
        value = admin4JProps.getProperty("memory.nbr.low.watermark.intervals");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setMemoryNbrLowWatermarkIntervals(toInteger(value, "memory.nbr.low.watermark.intervals"));
        }
        value = admin4JProps.getProperty("memory.low.watermark.monitor.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setMemoryLowWatermarkMonitorIntervalInMillis(toLong(value, "memory.low.watermark.monitor.interval.millis"));
        }
        
        value = admin4JProps.getProperty("thread.sleep.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setThreadSleepIntervalMillis(toInteger(value, "thread.sleep.interval.millis"));
        }
        value = admin4JProps.getProperty("thread.max.blocked.threads");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setThreadMaxBlockedThreads(toInteger(value, "thread.max.blocked.threads"));
        }
        
        value = admin4JProps.getProperty("web.transaction.error.logger.name");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setWebTransactionErrorLoggerName(value);
        }

        value = admin4JProps.getProperty("error.notification.time.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setErrorNotificationTimeIntervalMillis(toLong(value, "error.notification.time.interval.millis"));
        }

        value = admin4JProps.getProperty("error.exempted.exception.types");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setErrorExemptedExceptionTypes(value);
        }

        value = admin4JProps.getProperty("web.transaction.performance.notification.threshold.in.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setWebTransactionPerformanceNotificationThresholdInMillis(toLong(value, "web.transaction.performance.notification.threshold.in.millis"));
        }
        
        value = admin4JProps.getProperty("sql.nbr.retained.sql.statements");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setSqlNbrRetainedSqlStatements(toInteger(value, "sql.nbr.retained.sql.statements"));
        }
        
        value = admin4JProps.getProperty("sql.retention.time.in.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setSqlRetentionTimeInMillis(toLong(value, "sql.retention.time.in.millis"));
        }

        value = admin4JProps.getProperty("usage.sleep.interval.millis");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setConcurrentUsageSleepIntervalMillis(toInteger(value, "usage.sleep.interval.millis"));
        }
        
        value = admin4JProps.getProperty("usage.alert.levels");
        if ( !StringUtils.isEmpty(value)) {
            try {
                String[] levelStr = StringUtils.split(value, ",");
                Integer[] alertLevels = new Integer[levelStr.length];
                for (int i = 0; i < levelStr.length; i++) {
                    alertLevels[i] = Integer.valueOf(levelStr[i].trim());
                }
                Admin4JConfiguration.setConcurrentUsageAlertLevels(alertLevels);
            }
            catch (Exception e) {
                logger.error("Illegal property value in admin4j properties file", 
                        new Admin4jRuntimeException(e)
                    .addContextValue("propName", "usage.alert.levels")
                    .addContextValue("value", value));
            }
        }
        
        value = admin4JProps.getProperty("request.history.nbr.retained");
        if ( !StringUtils.isEmpty(value)) {
            Admin4JConfiguration.setRequestHistoryNbrRetained(toInteger(value, "request.history.nbr.retained"));
        }

    }

    /**
     * Here for unit testing purposes only -- shouldn't be used otherwise.
     * @param configurator
     */
    protected static void setConfigurator(PropertyConfigurator configurator) {
        PropertyConfigurator.configurator = configurator;
    }
    
    private static Integer toInteger(String value, String propName) {
        Integer tempInt = null;
        try {
            tempInt = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Illegal property value in admin4j properties file", 
                    new Admin4jRuntimeException(e)
                .addContextValue("propName", propName)
                .addContextValue("value", value));
        }
        
        return tempInt;
    }
    
    private static Long toLong(String value, String propName) {
        Long tempLong = null;
        try {
            tempLong = Long.parseLong(value);
        } catch (NumberFormatException e) {
            logger.error("Illegal property value in admin4j properties file", 
                    new Admin4jRuntimeException(e)
                .addContextValue("propName", propName)
                .addContextValue("value", value));
        }
        
        return tempLong;
    }
    
    private static Boolean toBoolean(String value, String propName) {
        Boolean tempBool = null;
        try {
            tempBool = Boolean.parseBoolean(value);
        } catch (Exception e) {
            logger.error("Illegal property value in admin4j properties file", 
                    new Admin4jRuntimeException(e)
                .addContextValue("propName", propName)
                .addContextValue("value", value));
        }
        
        return tempBool;
    }

}
