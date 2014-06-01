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
package net.admin4j.jdbc.driver;

import net.admin4j.deps.commons.lang3.builder.EqualsBuilder;
import net.admin4j.deps.commons.lang3.builder.HashCodeBuilder;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Default implementation of a DriverContext
 * @author D. Ashmore
 * @since 1.0.1
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public class DriverContextImpl implements DriverContext {
    
    private String driverClassname = null;
    private Integer driverMajorVersion = null;
    private Integer driverMinorVersion = null;
    private String poolName = null;

    public DriverContextImpl() {}
    public DriverContextImpl(String driverClassname
            , Integer driverMajorVersion
            , Integer driverMinorVersion
            , String poolName) {
        this.setDriverClassname(driverClassname);
        this.setDriverMajorVersion(driverMajorVersion);
        this.setDriverMinorVersion(driverMinorVersion);
        this.setPoolName(poolName);
    }
    
    public String getDriverClassname() {
        return driverClassname;
    }

    public void setDriverClassname(String driverClassname) {
        this.driverClassname = driverClassname;

    }

    public Integer getDriverMajorVersion() {
        return driverMajorVersion;
    }

    public void setDriverMajorVersion(Integer driverMajorVersion) {
        this.driverMajorVersion = driverMajorVersion;

    }

    public Integer getDriverMinorVersion() {
        return driverMinorVersion;
    }

    public void setDriverMinorVersion(Integer driverMinorVersion) {
        this.driverMinorVersion = driverMinorVersion;

    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;

    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(driverClassname)
            .append(driverMajorVersion)
            .append(driverMinorVersion)
            .append(poolName)
            .toHashCode();
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
        DriverContextImpl rhs = (DriverContextImpl) obj;
        return new EqualsBuilder()
            .append(this.driverClassname, rhs.driverClassname)
            .append(this.driverMajorVersion, rhs.driverMajorVersion)
            .append(this.driverMinorVersion, rhs.driverMinorVersion)
            .append(this.poolName, rhs.poolName)
            .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(nullSafeStr(driverClassname));
        buffer.append(":");
        buffer.append(nullSafeStr(driverMajorVersion));
        buffer.append(":");
        buffer.append(nullSafeStr(driverMinorVersion));
        buffer.append(":");
        buffer.append(nullSafeStr(poolName));
        return buffer.toString();
    }
    
    private String nullSafeStr(Object value) {
        if (value == null) {
            return "na";
        }
        return value.toString();
    }

}
