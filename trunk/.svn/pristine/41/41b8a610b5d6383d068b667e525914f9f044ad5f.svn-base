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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.GuiUtils;
import net.admin4j.util.HostUtils;
import net.admin4j.util.notify.Notifier;
import net.admin4j.util.threaddumper.ThreadDumperFactory;

/**
 * Will send out alerts and thread dumps at configurable usage levels.  Notice
 * will also be sent when usage returns to normal.
 * @author D. Ashmore
 * @since 1.0.1
 */
public class ConcurrentUsageDetector extends Detector {
    
    public static final Integer[] DEFAULT_ALERT_LEVELS = new Integer[]{100};
    public static final long DEFAULT_SLEEP_INTERVAL=30000;

    private Integer[] alertLevels;
    private AtomicInteger currentUsageLevel = new AtomicInteger(0);
    private Integer lastAlertLevelOffset = -1;
    
    public ConcurrentUsageDetector(Notifier notifier) {
        this(notifier, DEFAULT_ALERT_LEVELS);
    }
    
    public ConcurrentUsageDetector(Notifier notifier, Integer[] alertLevels) {
        super(notifier);
        
        Validate.notEmpty(alertLevels, "Null or empty alertLevels not allowed");
        Arrays.sort(alertLevels);
        this.alertLevels = alertLevels;
    }

    public void run() {
        Integer currentLevel = currentUsageLevel.get();
        
        for (int i = alertLevels.length - 1; i >= 0; i--) {
            if (currentLevel >= alertLevels[i] && lastAlertLevelOffset < i) {
                lastAlertLevelOffset = i;
                this.sendHighUsageNotice(currentLevel, alertLevels[i]);
                i = -1;
            }
            else if (i == 0 && lastAlertLevelOffset >= 0 && currentLevel < alertLevels[0]) {
                lastAlertLevelOffset = -1;
                this.sendNormalUsageNotice(currentLevel);
            }
        }

    }
    
    protected void sendNormalUsageNotice(Integer currentLevel) {
        this.sendNotice("Concurrent Usage returned to normal.  Usage=" + currentLevel, "Concurrent Usage Returned to normal", currentLevel);
    }
    
    protected void sendHighUsageNotice(Integer currentLevel, Integer threshold) {
        this.sendNotice("Concurrent Usage exceeded " + threshold + ".  Usage=" + currentLevel, "High Concurrent Usage Detected", currentLevel);
    }
    
    private void sendNotice(String subjectLine, String title, Integer currentLevel) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("host", HostUtils.getHostName());
        variableMap.put("currentDatetime", new Date());
        variableMap.put("currentLevel", currentLevel);
        variableMap.put("titleArg", title);
        variableMap.put("GuiUtils", new GuiUtils());
        variableMap.put("threadInfoArray", ThreadDumperFactory.getThreadDumper().dumpAllThreads());
        
        if (this.getNotifier().supportsHtml()) {            
            this.sendMessage(HostUtils.getHostName() + ": " + subjectLine, "concurrentUsageNoticeHtml.ftl", variableMap);
        }
        else if (this.getNotifier().supportsSMS()) {
            this.getNotifier().notify(HostUtils.getHostName() + 
                    ": " + subjectLine, "");
        }
        else {
            this.sendMessage(HostUtils.getHostName()  + ": " + subjectLine, "concurrentUsageNoticeText.ftl", variableMap);
        }
    }
    
    public Integer increment() {
        return currentUsageLevel.addAndGet(1);
    }
    
    public Integer decrement() {
        return currentUsageLevel.addAndGet(-1);
    }
    
    protected Integer getCurrentUsageLevel() {
        return currentUsageLevel.get();
    }

}
