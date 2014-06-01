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
package net.admin4j.vo;

import net.admin4j.deps.commons.lang3.builder.EqualsBuilder;
import net.admin4j.deps.commons.lang3.builder.HashCodeBuilder;
import net.admin4j.entity.ExceptionInfo;
import net.admin4j.entity.ExceptionInfoBase;

/**
 * Exception statistics
 * @author D. Ashmore
 * @since 1.0
 */
public class ExceptionStatisticVO extends ExceptionInfoBase {
	
	private String id;

	public ExceptionStatisticVO(ExceptionInfo eInfo) {
		this.stackTrace = eInfo.getStackTrace();
		this.id = eInfo.getAlternateId();
		this.setTroubleTicketUrl(eInfo.getTroubleTicketUrl());
		this.setExceptionClassName(eInfo.getExceptionClassName());
		this.setLastOccurrenceMessage(eInfo.getLastOccurrenceMessage());
		this.setFirstOccuranceDt(eInfo.getFirstOccuranceDt());
		this.setLastOccuranceDt(eInfo.getLastOccuranceDt());
		this.setTotalNbrExceptions(eInfo.getTotalNbrExceptions());
		
	}

    public String getExceptionPoint() {
        if (this.getStackTrace() != null && this.getStackTrace().length > 0) {
            return this.getStackTrace()[0].toString();
        }
        
        return "";
    }
    
    public String getId() {
        return this.id;
    }

    /* (non-Javadoc)
     * @see net.admin4j.exception.ExceptionInfoBase#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if ( !(obj instanceof ExceptionStatisticVO) ) {
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
}