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

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.builder.EqualsBuilder;
import net.admin4j.deps.commons.lang3.builder.HashCodeBuilder;
import net.admin4j.deps.commons.lang3.builder.ToStringBuilder;

/**
 * Geenric execution stack trace 
 * @author D. Ashmore
 *
 */
public class ExecutionStack {
    
    private StackTraceElement[] stackTraceElementArray;
    
    public ExecutionStack(StackTraceElement[] stackArray) {
        Validate.notNull(stackArray, "Null stackArray not allowed.");
        stackTraceElementArray = stackArray;
    }

    Integer hashCode = null;
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (hashCode != null) {
            return hashCode;
        }
        hashCode = new HashCodeBuilder()
             .append(stackTraceElementArray)
             .toHashCode();
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExecutionStack rhs = (ExecutionStack) obj;
        return new EqualsBuilder()
            .append(this.stackTraceElementArray, rhs.stackTraceElementArray)
            .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append(this.stackTraceElementArray)
            .toString();
    }

    public StackTraceElement[] getStackTraceElementArray() {
        return stackTraceElementArray;
    }

}
