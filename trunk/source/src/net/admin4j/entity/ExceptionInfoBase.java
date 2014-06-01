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
import java.util.concurrent.atomic.AtomicLong;

import net.admin4j.deps.commons.lang3.ArrayUtils;
import net.admin4j.deps.commons.lang3.builder.ToStringBuilder;

/**
 * Base information about an Exception
 * @author D. Ashmore
 * @since 1.0
 */
public class ExceptionInfoBase {

	private String exceptionClassName;
	private String lastOccurrenceMessage;
	protected StackTraceElement[] stackTrace;
	private String troubleTicketUrl;
    protected AtomicLong totalNbrExceptions = new AtomicLong(0);
    protected AtomicLong firstOccuranceDt = new AtomicLong(0);
    protected AtomicLong lastOccuranceDt = new AtomicLong(0);

	public String getLastOccurrenceMessage() {
		return lastOccurrenceMessage;
	}

	public void setLastOccurrenceMessage(String message) {
		this.lastOccurrenceMessage = message;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof ExceptionInfoBase) ) {
			return false;
		}
		return this.exceptionClassName.equals(((ExceptionInfoBase)obj).exceptionClassName) 
			&& ArrayUtils.isEquals(this.stackTrace, ((ExceptionInfoBase)obj).stackTrace);
	}

	@Override
	public int hashCode() {
	    if (this.stackTrace.length >= 1) {
	        return stackTrace[0].hashCode();
	    }
	    else {
	        return "EmptyStack".hashCode();
	    }
	}

	@SuppressWarnings("rawtypes")
    public void setExceptionClass(Class exceptionClass) {
	    if (exceptionClass != null) {
	        this.setExceptionClassName(exceptionClass.getName());
	    }
	    else this.setExceptionClassName(null);
	}

    public String getTroubleTicketUrl() {
        return troubleTicketUrl;
    }

    public void setTroubleTicketUrl(String troubleTicketUrl) {
        this.troubleTicketUrl = troubleTicketUrl;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public long getTotalNbrExceptions() {
        return totalNbrExceptions.get();
    }

    public void setTotalNbrExceptions(long totalNbrOccurances) {
        this.totalNbrExceptions.set(totalNbrOccurances);
    }

    public Date getFirstOccuranceDt() {
        return new Date(firstOccuranceDt.get());
    }

    public void setFirstOccuranceDt(Date firstOccuranceDt) {
        this.firstOccuranceDt.set(firstOccuranceDt.getTime());
    }

    public Date getLastOccuranceDt() {
        return new Date(lastOccuranceDt.get());
    }

    public void setLastOccuranceDt(Date lastOccuranceDt) {
        this.lastOccuranceDt.set(lastOccuranceDt.getTime());
        if (this.firstOccuranceDt.get() == 0) {
            this.firstOccuranceDt.set(lastOccuranceDt.getTime());
        }
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
	
}