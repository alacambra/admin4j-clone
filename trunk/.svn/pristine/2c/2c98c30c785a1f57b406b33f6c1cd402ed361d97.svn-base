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

import java.io.Serializable;

import net.admin4j.deps.commons.beanutils.BeanUtils;
import net.admin4j.deps.commons.lang3.builder.EqualsBuilder;
import net.admin4j.deps.commons.lang3.builder.HashCodeBuilder;
import net.admin4j.deps.commons.lang3.builder.ReflectionToStringBuilder;
import net.admin4j.deps.commons.lang3.exception.ContextedRuntimeException;

/**
 * Base Value Object implementing equals(), hashCode(), toString() and clone() 
 * @author D. Ashmore
 *
 */
public abstract class BaseVO implements Serializable, Cloneable {

    private static final long serialVersionUID = 2618192279106780874L;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(17, 37, this);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return BeanUtils.cloneBean(this);
        } catch (Exception e) {
            throw new ContextedRuntimeException("Error cloning value object", e)
                .addContextValue("class", this.getClass().getName());
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

}
