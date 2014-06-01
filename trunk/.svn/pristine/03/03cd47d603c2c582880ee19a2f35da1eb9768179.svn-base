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
package net.admin4j.entity;

import java.util.Date;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.builder.EqualsBuilder;
import net.admin4j.deps.commons.lang3.builder.HashCodeBuilder;
import net.admin4j.exception.ExceptionTracker;
import net.admin4j.util.ExpiringCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an Exception and information associated with it.
 * @author D. Ashmore
 * @since 1.0
 */
public class ExceptionInfo extends ExceptionInfoBase {
    
    private static Logger logger = LoggerFactory.getLogger(ExceptionTracker.class);
    
    /**
     * @deprecated Only provided so that we can read release 1.0_rc1 created files.
     * @param exceptionClass
     * @param trace
     * @param id
     */
    @SuppressWarnings("rawtypes")
    public ExceptionInfo(Class exceptionClass, StackTraceElement[] trace, String id) {
        Validate.notNull(exceptionClass, "Null exceptionClass not allowed.");
        if (trace == null) {
            this.stackTrace = new StackTraceElement[0];
        }
        else this.stackTrace = trace;
        
        this.setExceptionClassName(exceptionClass.getName());
        
        if (id == null) {
            this.alternateId = deriveId();
        }
    }
	
	public ExceptionInfo(String exceptionClassName, StackTraceElement[] trace) {
        this(exceptionClassName, trace, null);
    }
    
    public ExceptionInfo(String exceptionClassName, StackTraceElement[] trace, String id) {
        Validate.notEmpty(exceptionClassName, "Null or blank exceptionClassName not allowed.");
        if (trace == null) {
            this.stackTrace = new StackTraceElement[0];
        }
        else this.stackTrace = trace;
        
        this.setExceptionClassName(exceptionClassName);
        
        if (id == null) {
            this.alternateId = deriveId();
        }
    }
	
	private static ExpiringCache occuranceTimeStampMap = 
		new ExpiringCache(60000,60000);
	private String alternateId;
	private String deriveId() {
	    StringBuffer buffer = new StringBuffer(1024);
	    buffer.append(this.getExceptionClassName());
	    buffer.append("|");
	    
	    for (StackTraceElement element: this.getStackTrace()) {
	        buffer.append(element);
	    }
	    
	    return buffer.toString();
	}
	
	public void postOccurance(long systemIdHash) {
	    TimeStamp time = (TimeStamp)occuranceTimeStampMap.get(systemIdHash);	    
		if (time == null) {
		    this.setLastOccuranceDt(new Date());
	        this.totalNbrExceptions.addAndGet(1);
			occuranceTimeStampMap.put(systemIdHash, new TimeStamp(System.currentTimeMillis()));
		}
		else logger.debug("Duplicate posting detected -- sys id hash={}", systemIdHash);
	}
	
	/**
	 * This complication is needed so exceptions thrown at the same timestamp don't get collapsed when put into a Set.
	 * @author D. Ashmore
	 *
	 */
	public static class TimeStamp {
	    public TimeStamp(long time) {
	        timestampInMillis = time;
	    }
	    public Long timestampInMillis;
        public Long getTimestampInMillis() {
            return timestampInMillis;
        }
        public void setTimestampInMillis(Long timestampInMillis) {
            this.timestampInMillis = timestampInMillis;
        }
	}

    /* (non-Javadoc)
     * @see net.admin4j.exception.ExceptionInfoBase#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .isEquals();
    }

    /* (non-Javadoc)
     * @see net.admin4j.exception.ExceptionInfoBase#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37)
            .appendSuper(super.hashCode())
            .toHashCode();
    }

    public String getAlternateId() {
        return alternateId;
    }

    public void setAlternateId(String id) {
        this.alternateId = id;
    }

}