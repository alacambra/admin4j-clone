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

import java.lang.management.ThreadInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.admin4j.util.HostUtils;
import net.admin4j.util.notify.Notifier;
import net.admin4j.util.threaddumper.ThreadDumperFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Daemon that detects thread contention and emails an admin group.
 * @author D. Ashmore
 * @since 1.0
 */
public class BlockedThreadDetector extends Detector {
	
	public static final long DEFAULT_SLEEP_INTERVAL=30000;
	
	public static final int DEFAULT_BLOCKED_THREAD_THRESHOLD=2;
	private int nbrBlockedThreadThreashold = DEFAULT_BLOCKED_THREAD_THRESHOLD;
	HashSet<Long> previouslyBlockedSet = new HashSet<Long>();
	
	private static Logger log = LoggerFactory.getLogger(BlockedThreadDetector.class);
	
	public BlockedThreadDetector(Notifier notifier, int nbrBlockedThreadThreashold) {
	    super(notifier);
		this.nbrBlockedThreadThreashold = nbrBlockedThreadThreashold;
	}

	/* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        this.checkThreads();        
    }

    private void checkThreads() {		
		Set<ThreadInfo> waitingForLockSet = new HashSet<ThreadInfo>();
		Set<ThreadInfo> holdingLockSet = new HashSet<ThreadInfo>();
		Set<ThreadInfo> alsoBlockedLastIntervalSet = new HashSet<ThreadInfo>();
		
	    ThreadInfo[] tInfo = ThreadDumperFactory.getThreadDumper().dumpAllThreads();
        ThreadInfo lockingThread;
        int lockedCount = 0;

        long lockingthreadId;
        for (ThreadInfo thread: tInfo) {
            if (Thread.State.BLOCKED.equals(thread.getThreadState())) {
            	lockedCount++;
            	
            	waitingForLockSet.add(thread);
            	if (this.previouslyBlockedSet.contains(thread.getThreadId())) {
					alsoBlockedLastIntervalSet.add(thread);
				}
            	
                lockingthreadId = thread.getLockOwnerId();
                lockingThread = this.findThread(tInfo, lockingthreadId);
                holdingLockSet.add(lockingThread);

            }
            
        }
        if (alsoBlockedLastIntervalSet.size() >= nbrBlockedThreadThreashold) {
            log.debug(lockedCount + " threads are Waiting for Locks out of " + tInfo.length + " active threads.");
            this.sendBlockedThreadNotice(waitingForLockSet, holdingLockSet, alsoBlockedLastIntervalSet);
        }

		// Init for next time.
		previouslyBlockedSet.clear();
		Iterator<ThreadInfo> threadIt = waitingForLockSet.iterator();
		while (threadIt.hasNext()) {
			previouslyBlockedSet.add(threadIt.next().getThreadId());
		}

	}
	
	protected void sendBlockedThreadNotice(Set<ThreadInfo> waitingForLockSet, 
	        Set<ThreadInfo> holdingLockSet, 
	        Set<ThreadInfo> alsoBlockedLastIntervalSet) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("host", HostUtils.getHostName());
        variableMap.put("waitingForLockSet", waitingForLockSet);
        variableMap.put("holdingLockSet", holdingLockSet);
        variableMap.put("alsoBlockedLastIntervalSet", alsoBlockedLastIntervalSet);
        variableMap.put("currentDatetime", new Date());
        
        if (this.getNotifier().supportsHtml()) {            
            this.sendMessage(HostUtils.getHostName() + ": Blocked Threads Detected", "blockedThreadNoticeHtml.ftl", variableMap);
        }
        else if (this.getNotifier().supportsSMS()) {
            this.getNotifier().notify(HostUtils.getHostName() + 
                    ": Blocked Threads Detected", waitingForLockSet.size() + " currently blocking other threads.");
        }
        else {
            this.sendMessage(HostUtils.getHostName() + ": Blocked Threads Detected", "blockedThreadNoticeText.ftl", variableMap);
        }
    }
	
	private ThreadInfo findThread(ThreadInfo[] tInfo, long threadId) {
	    for (ThreadInfo thread: tInfo) {
	        if (thread.getThreadId() == threadId) {
	            return thread;
	        }
	    }
	    
	    return null;
	}
	
}
