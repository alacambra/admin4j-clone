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
package net.admin4j.hotspot;

import java.lang.management.ThreadInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.entity.ExecutionPoint;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.Daemon;
import net.admin4j.util.StackTraceUtils;
import net.admin4j.util.threaddumper.ThreadDumperFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Will track execution points and collect statistics.
 * @author D. Ashmore
 * @since 1.0
 */
public class ExecutionTracker {
    private static Logger logger = LoggerFactory.getLogger(ExecutionTracker.class);
    private static Map<StackTraceElement, ExecutionPoint> executionMap = new HashMap<StackTraceElement, ExecutionPoint>();
    private static Daemon TRACKING_DAEMON;
    
    public synchronized static void startTracking(int trackingDaemonSleepTimeInMillis ) {
        if (TRACKING_DAEMON != null) {
            logger.info("Tracking Daemon already started. - duplicate Daemon start request ignored.");
        }
        else {
            TRACKING_DAEMON = new Daemon(new ExecutionTrackingTask(), "Admin4J-ExecutionTrackingTask", trackingDaemonSleepTimeInMillis);
        }
    }
    
    /**
     * Tracks statistics on a single running thread.
     * @param threadInfo
     */
    public synchronized static void track(ThreadInfo threadInfo) {
        if (Thread.State.RUNNABLE.equals(threadInfo.getThreadState()) 
                || Thread.State.BLOCKED.equals(threadInfo.getThreadState())) {
            if (threadInfo.getStackTrace() != null) {
                ExecutionPoint currentExecutionPoint;
                StackTraceElement currentStack;
                StackTraceElement calledStack;
                StackTraceElement callingStack = null;
                
                for (int i = 0; i < threadInfo.getStackTrace().length; i++) {
                    currentStack = StackTraceUtils.sanitize(threadInfo.getStackTrace()[i]);
                    if (i + 1 < threadInfo.getStackTrace().length) {
                        calledStack = StackTraceUtils.sanitize(threadInfo.getStackTrace()[i + 1]);
                        
                        if (executionMap != null) { // Check seem superfluous , but did Null out at user site on JVM shutdown.
                            currentExecutionPoint = executionMap.get(currentStack);
                            if (currentExecutionPoint == null) {
                                currentExecutionPoint = new ExecutionPoint(currentStack);
                                executionMap.put(currentStack, currentExecutionPoint);
                            }
                            
                            currentExecutionPoint.setNbrExecutions(currentExecutionPoint.getNbrExecutions() + 1);
                            if (Thread.State.BLOCKED.equals(threadInfo.getThreadState())) {
                                currentExecutionPoint.setNbrBlockedExecutions(currentExecutionPoint.getNbrBlockedExecutions() + 1);
                            }
                            if (calledStack != null) {
                                currentExecutionPoint.addCalledStackTraceElement(calledStack);
                            }
                            if (callingStack != null) {
                                currentExecutionPoint.addCallingStackTraceElement(callingStack);
                            }
                            
                            // Execution point is blocked by synchronization
                            if (callingStack == null 
                                    && Thread.State.BLOCKED.equals(threadInfo.getThreadState()) 
                                    && !StringUtils.isEmpty(threadInfo.getLockName())) {
                                currentExecutionPoint.addBlockingSynchronizedClassName(threadInfo.getLockName());
                            }
                        }
                    }
                    else calledStack = null;
                                        
                    callingStack = currentStack;
                }
            }
        }
    }
    
    /**
     * Initiates tracking of all curently running threads.
     */
    public static void trackAll() {
        ThreadInfo[] tInfo = ThreadDumperFactory.getThreadDumper().dumpAllThreads();
        for (ThreadInfo thread: tInfo) {
            track(thread);
        }
    }
    
    /**
     * Resets thread tracking.
     */
    public synchronized static void reset() {
        executionMap.clear();
    }
    
    public synchronized static Map<StackTraceElement, ExecutionPoint> getExecutionMap() {
        Map<StackTraceElement, ExecutionPoint> localMap = new HashMap<StackTraceElement, ExecutionPoint>();
        try {
            for (Map.Entry<StackTraceElement, ExecutionPoint> entry: executionMap.entrySet()) {
                localMap.put(entry.getKey(), (ExecutionPoint)entry.getValue().clone());
            }
        } catch (CloneNotSupportedException e) {
            throw new Admin4jRuntimeException(e);
        }
        return localMap;
    }
    
    public synchronized static Map<StackTraceElement, ExecutionPoint> getHotSpotMap() {
        Map<StackTraceElement, ExecutionPoint> localMap = getExecutionMap();
        Set<StackTraceElement> toBeRemovedSet = new HashSet<StackTraceElement>();
        
        for (Map.Entry<StackTraceElement, ExecutionPoint> entry: localMap.entrySet() ) {
            if (entry.getValue().getCallingStackTraceElementList().size() <= 1) {
                toBeRemovedSet.add(entry.getKey());
            }
        }
        
        for (StackTraceElement element: toBeRemovedSet) {
            localMap.remove(element);
        }
        
        return localMap;
    }
    
    public synchronized static Map<StackTraceElement, ExecutionPoint> getBlockedExecutionMap() {
        Map<StackTraceElement, ExecutionPoint> localMap = getExecutionMap();
        Set<StackTraceElement> toBeRemovedSet = new HashSet<StackTraceElement>();
        
        for (Map.Entry<StackTraceElement, ExecutionPoint> entry: localMap.entrySet() ) {
            if (entry.getValue().getBlockingSynchronizedClassList().size() == 0) {
                toBeRemovedSet.add(entry.getKey());
            }
        }
        
        for (StackTraceElement element: toBeRemovedSet) {
            localMap.remove(element);
        }
        
        return localMap;
    }
    
    public static long getNbrTrackingObservations() {
        if (TRACKING_DAEMON == null) {
            return 0;
        }
        return ((ExecutionTrackingTask)TRACKING_DAEMON.getTask()).getNbrTaskExecutions();
    }

}
