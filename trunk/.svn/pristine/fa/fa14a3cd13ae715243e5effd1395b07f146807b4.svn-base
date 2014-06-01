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

import java.util.Date;
import java.util.List;

import net.admin4j.util.NumberUtils;

/**
 * Summarizes measurements taken by Task Timers.
 * @author D. Ashmore
 * @since 1.0
 */
public class DataMeasurementSummaryVO extends BaseVO {
    
    private static final long serialVersionUID = -7268348308380468547L;
    private String label;
    private SummaryType summaryType;
    private Number minimum;
    private Number maximum;
    private Number total;
    private Long nbrDataItems;
    private Double average;
    private Double variance;
    private Double standardDeviation;
    private Date firstObservationDate;
    private Date lastObservationDate;
    
    public static enum SummaryType {SUMMARY, ROLLING_TIME, ROLLING_NBR_OBS};
    
    public Number getMinimum() {
        return minimum;
    }
    
    public void setMinimum(Number minimum) {
        this.minimum = minimum;
    }
    
    public Number getMaximum() {
        return maximum;
    }
    
    public void setMaximum(Number maximum) {
        this.maximum = maximum;
    }
    
    public Number getTotal() {
        return total;
    }
    
    public void setTotal(Number total) {
        this.total = total;
    }
    
    public Long getNbrDataItems() {
        return nbrDataItems;
    }
    
    public void setNbrDataItems(Long nbrDataItems) {
        this.nbrDataItems = nbrDataItems;
    }
    
    public Double getAverage() {
        return average;
    }
    
    public void setAverage(Double average) {
        this.average = average;
    }
    
    public Double getVariance() {
        return variance;
    }
    
    public void setVariance(Double variance) {
        this.variance = variance;
    }
    
    public Double getStandardDeviation() {
        return standardDeviation;
    }
    
    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    
    public static DataMeasurementSummaryVO fromValueList(List<Number> valueList) {
        DataMeasurementSummaryVO summary = new DataMeasurementSummaryVO();
        
        summary.setMinimum(NumberUtils.min(valueList));
        summary.setMaximum(NumberUtils.max(valueList));
        summary.setNbrDataItems(Long.valueOf(valueList.size()));
        summary.setAverage(NumberUtils.average(valueList).doubleValue());
        summary.setTotal(NumberUtils.sum(valueList));
        summary.setStandardDeviation(NumberUtils.standardDeviation(valueList).doubleValue());
        summary.setVariance(NumberUtils.variance(valueList).doubleValue());
        
        return summary;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SummaryType getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(SummaryType summaryType) {
        this.summaryType = summaryType;
    }

    public Date getFirstObservationDate() {
        return firstObservationDate;
    }

    public void setFirstObservationDate(Date firstObservationDate) {
        this.firstObservationDate = firstObservationDate;
    }

    public Date getLastObservationDate() {
        return lastObservationDate;
    }

    public void setLastObservationDate(Date lastObservationDate) {
        this.lastObservationDate = lastObservationDate;
    }

}
