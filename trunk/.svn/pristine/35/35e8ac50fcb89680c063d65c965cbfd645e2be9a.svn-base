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

import java.util.StringTokenizer;

import net.admin4j.deps.commons.lang3.Validate;

/**
 * Summarizes the performance of a SQL statement
 * @author D. Ashmore
 *
 */
public class SqlStatementPerformanceSummaryVO extends BaseVO {

    private static final long serialVersionUID = 447417429076494830L;
    
    private DataMeasurementSummaryVO summary;
    private String driverClass;
    private Integer majorVersion;
    private Integer minorVersion;
    private String poolName;
    private String sqlText;
    
    public SqlStatementPerformanceSummaryVO(DataMeasurementSummaryVO summary) {
        Validate.notNull(summary, "Null summary not allowed.");
        this.summary = summary;
        
        StringTokenizer token1 = new StringTokenizer(summary.getLabel(), "-");
        String context = token1.nextToken();
        
        if (token1.hasMoreTokens()) {
            this.sqlText = token1.nextToken();
        }
        
        StringTokenizer token2 = new StringTokenizer(context, ":");
        if (token2.hasMoreTokens()) {
            this.driverClass = token2.nextToken();
        }
        if (token2.hasMoreTokens()) {
            this.majorVersion = Integer.valueOf(token2.nextToken());
        }
        if (token2.hasMoreTokens()) {
            this.minorVersion = Integer.valueOf(token2.nextToken());
        }
        
        if (token2.hasMoreTokens()) {
            this.poolName = token2.nextToken();
        }
        else {
            this.poolName = "none";
        }
    }

    public DataMeasurementSummaryVO getSummary() {
        return summary;
    }
    
    public String getDriverClass() {
        return driverClass;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public String getPoolName() {
        return poolName;
    }

    public String getSqlText() {
        return sqlText;
    }

    /* (non-Javadoc)
     * @see net.admin4j.vo.BaseVO#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SqlStatementPerformanceSummaryVO clone = new SqlStatementPerformanceSummaryVO( 
                (DataMeasurementSummaryVO)this.summary.clone());
        clone.driverClass = this.driverClass;
        clone.majorVersion = this.majorVersion;
        clone.minorVersion = this.minorVersion;
        clone.poolName = this.poolName;
        return clone;
    }

}
