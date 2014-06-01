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
package net.admin4j.monitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.admin4j.timer.SummaryDataMeasure;
import net.admin4j.util.GuiUtils;
import net.admin4j.util.HostUtils;
import net.admin4j.util.NumberUtils;
import net.admin4j.util.notify.Notifier;
import net.admin4j.util.threaddumper.ThreadDumperFactory;
import net.admin4j.vo.MemoryUtilizationVO;

/**
 * Notifies administrators for low memory conditions experienced by application.
 * @author D. Ashmore
 * @since 1.0
 */
public class LowMemoryDetector extends Detector {
    
    public static final int DEFAULT_MEMORY_USAGE_THRESHOLD_PCT=90;
    private long memoryThresholdPct = DEFAULT_MEMORY_USAGE_THRESHOLD_PCT;
    
    public static final long DEFAULT_SLEEP_INTERVAL=30000;
    
    public static final int DEFAULT_NBR_INTERVALS_BETWEEN_WARNINGS=30;
    private int nbrIntervalsBetweenWarnings;
    
    public static final long DEFAULT_STARTUP_PERIOD_IN_MILLIS=60000;
    private long startupPeriodInMillis = DEFAULT_STARTUP_PERIOD_IN_MILLIS;
    private Long monitorStartTimeMillis;
    
    public static final long DEFAULT_LOW_WATERMARK_MONITOR_INTERVAL_IN_MILLIS=1800000;
    private long lowWatermarkMonitorIntervalInMillis = DEFAULT_LOW_WATERMARK_MONITOR_INTERVAL_IN_MILLIS;
    
    public static final int DEFAULT_NBR_LOW_WATERMARK_INTERVALS=48;
    private int nbrLowWatermarkIntervals = DEFAULT_NBR_LOW_WATERMARK_INTERVALS;
    private SummaryDataMeasure[] memoryMeasurement = new SummaryDataMeasure[nbrLowWatermarkIntervals];
    private int nbrLowWatermarkMeasurementOffset = -1;
    
//    private static Logger log = LoggerFactory.getLogger(LowMemoryDetector.class);
    
    private int nbrIntervalsSinceLastWarning = 0;
    private boolean lowMemoryStateDetected = false;
    private Long timeMemoryProblemBeganInMillis = null;
    
    public LowMemoryDetector(Notifier notifier) {
        super(notifier);
    }
    
    public LowMemoryDetector(Notifier notifier, long memoryThresholdPct, int nbrIntervalsBetweenWarnings, int nbrLowWatermarkIntervals, long lowWatermarkMonitorIntervalInMillis) {
        this(notifier);
        this.setMemoryThresholdPct(memoryThresholdPct);
        this.setNbrIntervalsBetweenWarnings(nbrIntervalsBetweenWarnings);
        this.setNbrLowWatermarkIntervals(nbrLowWatermarkIntervals);
        this.setLowWatermarkMonitorIntervalInMillis(lowWatermarkMonitorIntervalInMillis);
    }
    
    public void run() {
        if (monitorStartTimeMillis == null) {
            monitorStartTimeMillis = System.currentTimeMillis();
        }

        MemoryUtilizationVO memoryVO = this.findMemoryUtilization();
        this.watchLowWatermark(memoryVO);
        this.checkMemory(memoryVO);
    }
    
    protected void watchLowWatermark(MemoryUtilizationVO memoryVO) {
        if (monitorStartTimeMillis == null) {
            monitorStartTimeMillis = System.currentTimeMillis();
        }
        
        if (System.currentTimeMillis() < monitorStartTimeMillis + startupPeriodInMillis) {
            return; // In startup period -- memory expected to be abnormally low during setup.
        }
        
        if (nbrLowWatermarkMeasurementOffset < 0 && System.currentTimeMillis() >= monitorStartTimeMillis + startupPeriodInMillis) {
            nbrLowWatermarkMeasurementOffset = 0;
            memoryMeasurement[nbrLowWatermarkMeasurementOffset] = new SummaryDataMeasure(System.currentTimeMillis());
        }
        else if (System.currentTimeMillis() > memoryMeasurement[nbrLowWatermarkMeasurementOffset].getFirstObservationTime() + lowWatermarkMonitorIntervalInMillis) {
            nbrLowWatermarkMeasurementOffset++;
            if (nbrLowWatermarkMeasurementOffset >= memoryMeasurement.length) {
                nbrLowWatermarkMeasurementOffset = 0;                
            }
            memoryMeasurement[nbrLowWatermarkMeasurementOffset] = new SummaryDataMeasure(System.currentTimeMillis());
        }
        
        memoryMeasurement[nbrLowWatermarkMeasurementOffset].addNumber(memoryVO.getMemoryInUseInBytes());
    }
    
    private List<SummaryDataMeasure> getCurrentLowWatermarkMeasurementList() {
        List<SummaryDataMeasure> measureList = new ArrayList<SummaryDataMeasure>();
        
        for (int i = nbrLowWatermarkMeasurementOffset - 1; i >= 0; i--) {
            if (memoryMeasurement[i] != null) {
                measureList.add(memoryMeasurement[i]);
            }
        }
        for (int i = memoryMeasurement.length - 1; i > nbrLowWatermarkMeasurementOffset; i--) {
            if (memoryMeasurement[i] != null) {
                measureList.add(memoryMeasurement[i]);
            }
        }
        
        return measureList;
    }
    
    private double getAverageLowWatermarkIncrease(List<SummaryDataMeasure> measureList) {
        List<Number> numberList = new ArrayList<Number>();
        Number lastNumber = null;
        for (SummaryDataMeasure measurement: measureList) {
            if (lastNumber != null) {
                numberList.add(measurement.getMinimum() - lastNumber.doubleValue());
            }
            lastNumber = measurement.getMinimum();
        }
        
        return NumberUtils.average(numberList).doubleValue();
    }
    
    protected void checkMemory(MemoryUtilizationVO memoryVO) {
                
        if (memoryVO.getPercentMemoryUsed() > this.getMemoryThresholdPct()) {
            if (this.lowMemoryStateDetected && this.nbrIntervalsSinceLastWarning >= this.getNbrIntervalsBetweenWarnings()) {
                this.sendLowMemoryNotice(memoryVO);
                this.nbrIntervalsSinceLastWarning = 0;
            }
            if (this.lowMemoryStateDetected) {
                this.nbrIntervalsSinceLastWarning++;
            }
            else {
                this.lowMemoryStateDetected = true;
                this.nbrIntervalsSinceLastWarning = 0;
                this.timeMemoryProblemBeganInMillis = System.currentTimeMillis();
                this.sendLowMemoryNotice(memoryVO);
            }
        }
        else {
            if (this.lowMemoryStateDetected) {
                this.sendAllClearMemoryNotice(memoryVO);
                this.lowMemoryStateDetected = false;
                this.timeMemoryProblemBeganInMillis = null;
            }
        }
    }
    
    protected void sendLowMemoryNotice(MemoryUtilizationVO memoryUtilization) {
        List<SummaryDataMeasure> lowMemoryMeasurementList = this.getCurrentLowWatermarkMeasurementList();
        Double averageLowMemoryIncrease = this.getAverageLowWatermarkIncrease(lowMemoryMeasurementList);
        
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("host", HostUtils.getHostName());
        variableMap.put("memory", memoryUtilization);
        variableMap.put("currentDatetime", new Date());
        variableMap.put("averageLowMemoryIncrease", averageLowMemoryIncrease);
        variableMap.put("lowWatermarkMonitorIntervalInMillis", lowWatermarkMonitorIntervalInMillis);
        variableMap.put("lowMemoryMeasurementList", lowMemoryMeasurementList);
        variableMap.put("GuiUtils", new GuiUtils());
        variableMap.put("threadInfoArray", ThreadDumperFactory.getThreadDumper().dumpAllThreads());
        
        if (this.getNotifier().supportsHtml()) {            
            this.sendMessage(HostUtils.getHostName() + ": High Memory Usage - " + memoryUtilization.getPercentMemoryUsed() + "%", "lowMemoryNoticeHtml.ftl", variableMap);
        }
        else if (this.getNotifier().supportsSMS()) {
            this.getNotifier().notify(HostUtils.getHostName() + 
                    ": High Memory Usage - " + memoryUtilization.getPercentMemoryUsed() + "%", 
                    "Free Memory=" + memoryUtilization.getFreeAvailableMemoryInMb() + " Mb out of " + memoryUtilization.getMaxMemoryInBytesInMb() + " Mb.");
        }
        else {
            this.sendMessage(HostUtils.getHostName() + ": High Memory Usage - " + memoryUtilization.getPercentMemoryUsed() + "%", "lowMemoryNoticeText.ftl", variableMap);
        }
    }
    
    protected void sendAllClearMemoryNotice(MemoryUtilizationVO memoryUtilization) {
        List<SummaryDataMeasure> lowMemoryMeasurementList = this.getCurrentLowWatermarkMeasurementList();
        Double averageLowMemoryIncrease = this.getAverageLowWatermarkIncrease(lowMemoryMeasurementList);
        
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("host", HostUtils.getHostName());
        variableMap.put("memory", memoryUtilization);
        variableMap.put("endDate", new Date());
        if (this.timeMemoryProblemBeganInMillis != null) {
            variableMap.put("startDate", new Date(this.timeMemoryProblemBeganInMillis));
        }
        else {
            logger.error("Low Memory Detector:  Start Time is null.  Current substituted but all-clear notice is incorrect.");
            variableMap.put("startDate", new Date());
        }
        
        variableMap.put("averageLowMemoryIncrease", averageLowMemoryIncrease);
        variableMap.put("lowMemoryMeasurementList", lowMemoryMeasurementList);
        variableMap.put("lowWatermarkMonitorIntervalInMillis", lowWatermarkMonitorIntervalInMillis);
        variableMap.put("GuiUtils", new GuiUtils());
        variableMap.put("threadInfoArray", ThreadDumperFactory.getThreadDumper().dumpAllThreads());
        
        if (this.getNotifier().supportsHtml()) {            
            this.sendMessage(HostUtils.getHostName() + " - Memory Usage Normal ", "lowMemoryAllClearNoticeHtml.ftl", variableMap);
        }
        else if (this.getNotifier().supportsSMS()) {
            this.getNotifier().notify(HostUtils.getHostName() + 
                    " - Memory Usage Normal ", 
                    "Free Memory=" + memoryUtilization.getFreeAvailableMemoryInMb() + 
                    " Mb out of " + memoryUtilization.getMaxMemoryInBytesInMb() + " Mb.");
        }
        else {
            this.sendMessage(HostUtils.getHostName() + " - Memory Usage Normal ", "lowMemoryAllClearNoticeText.ftl", variableMap);
        }
    }
    
    private MemoryUtilizationVO findMemoryUtilization() {
        
        long currentMemoryAllocated = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory(); //before allocating more
        long maxMemory = Runtime.getRuntime().maxMemory(); //maximum possible memory
        
        return new MemoryUtilizationVO(currentMemoryAllocated, freeMemory, maxMemory);        
    }

    public long getMemoryThresholdPct() {
        return memoryThresholdPct;
    }

    protected void setMemoryThresholdPct(long usedMemoryThresholdPct) {
        this.memoryThresholdPct = usedMemoryThresholdPct;
    }

    public int getNbrIntervalsBetweenWarnings() {
        return nbrIntervalsBetweenWarnings;
    }

    protected void setNbrIntervalsBetweenWarnings(int nbrIntervalsBetweenWarnings) {
        this.nbrIntervalsBetweenWarnings = nbrIntervalsBetweenWarnings;
    }

    public int getNbrIntervalsSinceLastWarning() {
        return nbrIntervalsSinceLastWarning;
    }

    public boolean isLowMemoryStateDetected() {
        return lowMemoryStateDetected;
    }

    public long getStartupPeriodInMillis() {
        return startupPeriodInMillis;
    }

    protected void setStartupPeriodInMillis(long startupPeriodInMillis) {
        this.startupPeriodInMillis = startupPeriodInMillis;
    }

    public long getLowWatermarkMonitorIntervalInMillis() {
        return lowWatermarkMonitorIntervalInMillis;
    }

    protected void setLowWatermarkMonitorIntervalInMillis(
            long lowWatermarkMonitorIntervalInMillis) {
        this.lowWatermarkMonitorIntervalInMillis = lowWatermarkMonitorIntervalInMillis;
    }

    public int getNbrLowWatermarkIntervals() {
        return nbrLowWatermarkIntervals;
    }

    protected void setNbrLowWatermarkIntervals(int nbrLowWatermarkIntervals) {
        this.nbrLowWatermarkIntervals = nbrLowWatermarkIntervals;
    }   

}
