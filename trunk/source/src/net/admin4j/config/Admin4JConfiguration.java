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
import java.util.HashSet;
import java.util.Set;

import net.admin4j.util.notify.Notifier;

/**
 * Responsible for providing current configuration information for Admin4J.
 * @author D. Ashmore
 * @since 1.0
 */
public class Admin4JConfiguration {
    
    public enum StorageFormat {XML, DATABASE};
    
    private static StorageFormat exceptionInformationStorageFormat = null;
    private static String exceptionInformationXmlFileName = null;
    private static StorageFormat performanceInformationStorageFormat = null;
    private static String performanceInformationXmlFileName = null;
    private static Notifier defaultNotifier = null;
    
    private static File baseFreemarkerTemplateDirectory = null;
    
    private static Set<String> additionalLogManagerClassNames = new HashSet<String>();
    
    private static Integer exceptionTimeTrackingInDays = null;
    private static String exceptionExemptedExceptionTypes = null;
    
    private static Boolean fileExplorerRestrictToBaseDir = null;
    private static Boolean fileExplorerRestrictFromExec = null;
    private static Boolean fileExplorerRestrictFromWrite = null;
    private static String fileExplorerBaseDirName = null;
    
    private static Integer hotSpotSleepIntervalMillis = null;
    private static Integer memoryThresholdPct = null;
    private static Integer memoryNbrIntervalsBetweenWarnings = null;
    private static Long memorySleepIntervalMillis = null;
    private static Integer memoryNbrLowWatermarkIntervals = null;
    private static Long memoryLowWatermarkMonitorIntervalInMillis = null;
    
    private static Integer sqlNbrRetainedSqlStatements = null;
    private static Long sqlRetentionTimeInMillis = null;
    
    private static Integer threadSleepIntervalMillis = null;
    private static Integer threadMaxBlockedThreads = null;
    
    private static Integer concurrentUsageSleepIntervalMillis = null;
    private static Integer[] concurrentUsageAlertLevels = null;
    
    private static String webTransactionErrorLoggerName = null;
    private static Long errorNotificationTimeIntervalMillis = null;
    private static String errorExemptedExceptionTypes = null;
    private static Long webTransactionPerformanceNotificationThresholdInMillis = null;
    
    private static Integer requestHistoryNbrRetained = null;
    
 // Cause configuration setting via property file.
    static {
        PropertyConfigurator.configure();
    }

    public static StorageFormat getExceptionInformationStorageFormat() {
        return exceptionInformationStorageFormat;
    }

    public static void setExceptionInformationStorageFormat(
            StorageFormat exceptionInformationStorageFormat) {
        Admin4JConfiguration.exceptionInformationStorageFormat = exceptionInformationStorageFormat;
    }

    public static String getExceptionInformationXmlFileName() {
        return exceptionInformationXmlFileName;
    }

    static void setExceptionInformationXmlFileName(
            String exceptionInformationXmlFileName) {
        Admin4JConfiguration.exceptionInformationXmlFileName = exceptionInformationXmlFileName;
    }
    
    public static boolean isExceptionInfoStored() {
        return exceptionInformationXmlFileName != null;
    }

    public static StorageFormat getPerformanceInformationStorageFormat() {
        return performanceInformationStorageFormat;
    }

    public static void setPerformanceInformationStorageFormat(
            StorageFormat performanceInformationStorageFormat) {
        Admin4JConfiguration.performanceInformationStorageFormat = performanceInformationStorageFormat;
    }

    public static String getPerformanceInformationXmlFileName() {
        return performanceInformationXmlFileName;
    }

    static void setPerformanceInformationXmlFileName(
            String performanceInformationXmlFileName) {
        Admin4JConfiguration.performanceInformationXmlFileName = performanceInformationXmlFileName;
    }
    
    public static boolean isPerformanceInfoStored() {
        return performanceInformationXmlFileName != null;
    }

    public static Notifier getDefaultNotifier() {
        return defaultNotifier;
    }

    static void setDefaultNotifier(Notifier defaultNotifier) {
        Admin4JConfiguration.defaultNotifier = defaultNotifier;
    }

    public static Set<String> getAdditionalLogManagerClassNames() {
        return additionalLogManagerClassNames;
    }

    public static File getBaseFreemarkerTemplateDirectory() {
        return baseFreemarkerTemplateDirectory;
    }

    static void setBaseFreemarkerTemplateDirectory(
            File baseFreemarkerTemplateDirectory) {
        Admin4JConfiguration.baseFreemarkerTemplateDirectory = baseFreemarkerTemplateDirectory;
    }

    public static Integer getExceptionTimeTrackingInDays() {
        return exceptionTimeTrackingInDays;
    }

    static void setExceptionTimeTrackingInDays(
            Integer exceptionTimeTrackingInDays) {
        Admin4JConfiguration.exceptionTimeTrackingInDays = exceptionTimeTrackingInDays;
    }

    public static String getExceptionExemptedExceptionTypes() {
        return exceptionExemptedExceptionTypes;
    }

    static void setExceptionExemptedExceptionTypes(
            String exceptionExemptedExceptionTypes) {
        Admin4JConfiguration.exceptionExemptedExceptionTypes = exceptionExemptedExceptionTypes;
    }

    public static Boolean getFileExplorerRestrictToBaseDir() {
        return fileExplorerRestrictToBaseDir;
    }

    static void setFileExplorerRestrictToBaseDir(
            Boolean fileBrowserRestrictToBaseDir) {
        Admin4JConfiguration.fileExplorerRestrictToBaseDir = fileBrowserRestrictToBaseDir;
    }

    public static Boolean getFileExplorerRestrictFromExec() {
        return fileExplorerRestrictFromExec;
    }

    static void setFileExplorerRestrictFromExec(
            Boolean fileBrowserRestrictFromExec) {
        Admin4JConfiguration.fileExplorerRestrictFromExec = fileBrowserRestrictFromExec;
    }

    public static Boolean getFileExplorerRestrictFromWrite() {
        return fileExplorerRestrictFromWrite;
    }

    static void setFileExplorerRestrictFromWrite(
            Boolean fileBrowserRestrictFromWrite) {
        Admin4JConfiguration.fileExplorerRestrictFromWrite = fileBrowserRestrictFromWrite;
    }

    public static String getFileExplorerBaseDirName() {
        return fileExplorerBaseDirName;
    }

    static void setFileExplorerBaseDirName(String fileBrowserBaseDirName) {
        Admin4JConfiguration.fileExplorerBaseDirName = fileBrowserBaseDirName;
    }

    public static Integer getHotSpotSleepIntervalMillis() {
        return hotSpotSleepIntervalMillis;
    }

    static void setHotSpotSleepIntervalMillis(
            Integer hotSpotSleepIntervalMillis) {
        Admin4JConfiguration.hotSpotSleepIntervalMillis = hotSpotSleepIntervalMillis;
    }

    public static Integer getMemoryThresholdPct() {
        return memoryThresholdPct;
    }

    static void setMemoryThresholdPct(Integer memoryThresholdPct) {
        Admin4JConfiguration.memoryThresholdPct = memoryThresholdPct;
    }

    public static Integer getMemoryNbrIntervalsBetweenWarnings() {
        return memoryNbrIntervalsBetweenWarnings;
    }

    static void setMemoryNbrIntervalsBetweenWarnings(
            Integer memoryNbrIntervalsBetweenWarnings) {
        Admin4JConfiguration.memoryNbrIntervalsBetweenWarnings = memoryNbrIntervalsBetweenWarnings;
    }

    public static Integer getThreadSleepIntervalMillis() {
        return threadSleepIntervalMillis;
    }

    static void setThreadSleepIntervalMillis(
            Integer threadSleepIntervalMillis) {
        Admin4JConfiguration.threadSleepIntervalMillis = threadSleepIntervalMillis;
    }

    public static Integer getThreadMaxBlockedThreads() {
        return threadMaxBlockedThreads;
    }

    static void setThreadMaxBlockedThreads(Integer threadMaxBlockedThreads) {
        Admin4JConfiguration.threadMaxBlockedThreads = threadMaxBlockedThreads;
    }

    public static String getWebTransactionErrorLoggerName() {
        return webTransactionErrorLoggerName;
    }

    static void setWebTransactionErrorLoggerName(String errorLoggerName) {
        Admin4JConfiguration.webTransactionErrorLoggerName = errorLoggerName;
    }

    public static Long getErrorNotificationTimeIntervalMillis() {
        return errorNotificationTimeIntervalMillis;
    }

    static void setErrorNotificationTimeIntervalMillis(
            Long errorNotificationTimeIntervalMillis) {
        Admin4JConfiguration.errorNotificationTimeIntervalMillis = errorNotificationTimeIntervalMillis;
    }

    public static String getErrorExemptedExceptionTypes() {
        return errorExemptedExceptionTypes;
    }

    static void setErrorExemptedExceptionTypes(
            String errorExemptedExceptionTypes) {
        Admin4JConfiguration.errorExemptedExceptionTypes = errorExemptedExceptionTypes;
    }

    public static Long getWebTransactionPerformanceNotificationThresholdInMillis() {
        return webTransactionPerformanceNotificationThresholdInMillis;
    }

    static void setWebTransactionPerformanceNotificationThresholdInMillis(
            Long webTransactionPerformanceNotificationThresholdInMillis) {
        Admin4JConfiguration.webTransactionPerformanceNotificationThresholdInMillis = webTransactionPerformanceNotificationThresholdInMillis;
    }

    public static Long getMemorySleepIntervalMillis() {
        return memorySleepIntervalMillis;
    }

    static void setMemorySleepIntervalMillis(Long memorySleepIntervalMillis) {
        Admin4JConfiguration.memorySleepIntervalMillis = memorySleepIntervalMillis;
    }

    public static Integer getMemoryNbrLowWatermarkIntervals() {
        return memoryNbrLowWatermarkIntervals;
    }

    public static void setMemoryNbrLowWatermarkIntervals(
            Integer memoryNbrLowWatermarkIntervals) {
        Admin4JConfiguration.memoryNbrLowWatermarkIntervals = memoryNbrLowWatermarkIntervals;
    }

    public static Long getMemoryLowWatermarkMonitorIntervalInMillis() {
        return memoryLowWatermarkMonitorIntervalInMillis;
    }

    public static void setMemoryLowWatermarkMonitorIntervalInMillis(
            Long memoryLowWatermarkMonitorIntervalInMillis) {
        Admin4JConfiguration.memoryLowWatermarkMonitorIntervalInMillis = memoryLowWatermarkMonitorIntervalInMillis;
    }

    public static Integer getSqlNbrRetainedSqlStatements() {
        return sqlNbrRetainedSqlStatements;
    }

    public static void setSqlNbrRetainedSqlStatements(Integer sqlTimerNbrSqlStatements) {
        Admin4JConfiguration.sqlNbrRetainedSqlStatements = sqlTimerNbrSqlStatements;
    }

    public static Long getSqlRetentionTimeInMillis() {
        return sqlRetentionTimeInMillis;
    }

    public static void setSqlRetentionTimeInMillis(Long sqlRetentionTimeInMillis) {
        Admin4JConfiguration.sqlRetentionTimeInMillis = sqlRetentionTimeInMillis;
    }

    public static Integer getConcurrentUsageSleepIntervalMillis() {
        return concurrentUsageSleepIntervalMillis;
    }

    public static void setConcurrentUsageSleepIntervalMillis(
            Integer concurrentUsageSleepIntervalMillis) {
        Admin4JConfiguration.concurrentUsageSleepIntervalMillis = concurrentUsageSleepIntervalMillis;
    }

    public static Integer[] getConcurrentUsageAlertLevels() {
        return concurrentUsageAlertLevels;
    }

    public static void setConcurrentUsageAlertLevels(
            Integer[] concurrentUsageAlertLevels) {
        Admin4JConfiguration.concurrentUsageAlertLevels = concurrentUsageAlertLevels;
    }

    public static Integer getRequestHistoryNbrRetained() {
        return requestHistoryNbrRetained;
    }

    public static void setRequestHistoryNbrRetained(
            Integer requestHistoryNbrRetained) {
        Admin4JConfiguration.requestHistoryNbrRetained = requestHistoryNbrRetained;
    }



}
