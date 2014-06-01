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
package net.admin4j.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.dao.DAOFactory;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.exception.ExceptionUtils;
import net.admin4j.entity.ExceptionInfo;
import net.admin4j.entity.ExceptionInfoBase;
import net.admin4j.util.Daemon;
import net.admin4j.vo.ExceptionStatisticVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tracks statistics about different kinds of traces and how often they are thrown.
 * @author D. Ashmore
 * @since 1.0
 */
public class ExceptionTracker {
	
	private static Map<ExceptionInfo, ExceptionInfo> thrownExceptionMap = new HashMap<ExceptionInfo, ExceptionInfo>();
	private static Map<String, ExceptionInfo> thrownAlternateKeyMap = new HashMap<String, ExceptionInfo>();
	private static Logger logger = LoggerFactory.getLogger(ExceptionTracker.class);
	private static Map<String,String> exemptedExceptionClassNames = new ConcurrentHashMap<String, String>();
	
	static {
	    if (Admin4JConfiguration.isExceptionInfoStored()) {
	        try {
    	        for (ExceptionInfo eInfo: DAOFactory.getExceptionInfoDAO().findAll()) {
    	            if (validate(eInfo)) {
        	            synchronized(thrownExceptionMap) {
            	            thrownExceptionMap.put(eInfo, eInfo);
            	            thrownAlternateKeyMap.put(eInfo.getAlternateId(), eInfo);
        	            }
    	            }
    	            else {logger.warn("Invalid exception detected: ", eInfo);}
    	        }
    	        
    	        logger.info("Exception Info read without error.  Nbr of exceptions read: " + thrownExceptionMap.size());
	        }
	        catch (Throwable t) {
	            logger.error("Error reading stored exception info", t);
	        }
	    }
	}
	
	private static boolean validate(ExceptionInfo eInfo) {
	    boolean answer = true;
	    if (eInfo == null) {  
	        answer = false; 
	    } 
	    else if (eInfo.getExceptionClassName() == null || eInfo.getAlternateId() == null) {
	        logger.info("Exception recorded that no longer exists.  Information from that exception: {}", eInfo.toString());
	        answer = false;
	    }
	    // Can't do this -- JSP generated classes might not exist yet -- but might be valid.  It's just that
	    // the temp dir was purged and nobody used the JSP yet.
//	    if (answer) {
//	        for (StackTraceElement element: eInfo.getStackTrace()) {
//	            try {
//                    Class.forName(element.getClassName());
//                } catch (ClassNotFoundException e) {
//                    logger.info("Exception recorded with invalid stacktrace.  Class not found: {}.  Information from that exception: {}", e.getMessage(), eInfo.toString());
//                    return false;
//                }
//	        }
//	    }
	    return answer;
	}

    private static final Daemon CLEANUP_DAEMON = new Daemon(new ExceptionTrackerCleanupTask(), "Admin4J-ExceptionTrackerCleanupTask");
    static {
        Runtime.getRuntime().addShutdownHook(new ExceptionTrackerShutdownHook());
    }
    
    /**
     * Adds class names for exceptions that will <b>not</b> be tracked.
     * @param className
     */
    public static void addExemptedExceptionClassName(String className) {
        Validate.notEmpty(className, "Null or blank class name not allowed.");
        String local = className.trim();
        exemptedExceptionClassNames.put(local, local);
    }
	
	/**
	 * Tracks an exception for reporting/browsing.
	 * @param t
	 */
	public static void track(Throwable t) {
		Validate.notNull(t, "Null exception not allowed.");
		
		Throwable rootCause = ExceptionUtils.getRootCause(t);
		if (rootCause == null) {
		    rootCause = t;
		}
		
		if (exemptedExceptionClassNames.containsKey(rootCause.getClass().getName())) {
		    logger.debug("Exception bypassed: {} - {}", rootCause.getClass().getName(), rootCause.getMessage());
		    return;
		}
		
		ExceptionInfo eInfo = findExceptionInfo(rootCause);
		eInfo.setLastOccurrenceMessage(rootCause.getMessage());
		eInfo.postOccurance(System.identityHashCode(rootCause));
		
		logger.debug("Exception tracked: {} - {}", rootCause.getClass().getName(), rootCause.getMessage());
	}
	
	public static void recordUrl(String alternateId, String urlString) {
	    synchronized(thrownExceptionMap) {
    	    ExceptionInfoBase eInfo = thrownAlternateKeyMap.get(alternateId);
    	    if (eInfo != null) {
    	        eInfo.setTroubleTicketUrl(urlString);
    	    }
	    }
	}
	
	public static ExceptionInfo lookupException(String alternateId) {
        synchronized(thrownExceptionMap) {
            return thrownAlternateKeyMap.get(alternateId);
        }
    }
	
	public static void deleteException(String alternateId) {
	    synchronized(thrownExceptionMap) {
            ExceptionInfoBase eInfo = thrownAlternateKeyMap.get(alternateId);
            if (eInfo != null) {
                thrownAlternateKeyMap.remove(alternateId);
                thrownExceptionMap.remove(eInfo);
                
                if (Admin4JConfiguration.isExceptionInfoStored()) {
                    DAOFactory.getExceptionInfoDAO().saveAll(getExceptionInfoSet());
                }
            }
	    }
    }
	
	public static void setPurgeThresholdInDays(int days) {
	    ExceptionTrackerCleanupTask task = (ExceptionTrackerCleanupTask)CLEANUP_DAEMON.getTask();
	    task.setPurgeThresholdInDays(days);
	}
	
	protected static int getMapSize() {
	    synchronized(thrownExceptionMap) {
	        return thrownExceptionMap.size();
	    }
	}
	
	public static Set<ExceptionInfo> getExceptionInfoSet() {
	    Set<ExceptionInfo> set = new HashSet<ExceptionInfo>();
	    synchronized(thrownExceptionMap) {
	        set.addAll(thrownExceptionMap.keySet());
	    }
	    return set;
	}
	
	public static Set<ExceptionStatisticVO> getExceptionStatisticSet() {
        Set<ExceptionStatisticVO> set = new HashSet<ExceptionStatisticVO>();
        for (ExceptionInfo eInfo: getExceptionInfoSet()) {
            set.add(new ExceptionStatisticVO(eInfo));
        }
        return set;
    }
	
	protected static void purgeException(ExceptionInfo eInfo) {
	    synchronized(thrownExceptionMap) {
    	    thrownExceptionMap.remove(eInfo);
    	    thrownAlternateKeyMap.remove(eInfo.getAlternateId());
	    }
	}
	
	protected static ExceptionInfo findExceptionInfo(Throwable t) {
		StackTraceElement[] stackTrace = t.getStackTrace();
		
		ExceptionInfo eInfo = null;
		synchronized(thrownExceptionMap) {
    		eInfo = thrownExceptionMap.get(new ExceptionInfo(t.getClass().getName(), stackTrace));
    		if (eInfo == null) {
    			eInfo = new ExceptionInfo(t.getClass().getName(), stackTrace);
    			Date ts = new Date();
    			eInfo.setFirstOccuranceDt(ts);
    			eInfo.setLastOccuranceDt(ts);
    			eInfo.setLastOccurrenceMessage(t.getMessage());
    			eInfo.setExceptionClass(t.getClass());
    			thrownExceptionMap.put(eInfo, eInfo);
    			thrownAlternateKeyMap.put(eInfo.getAlternateId(), eInfo);
    		}
		}
		
		return eInfo;
	}

}
