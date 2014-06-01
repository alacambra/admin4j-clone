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
package net.admin4j.timer;

import java.util.Date;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.vo.DataMeasurementSummaryVO;

/**
 * Responsible for keeping numerical time series data.
 * 
 * <p>Note:  This implementation is intended to be threadsafe.</p>
 * @author D. Ashmore
 * @since 1.0
 */
public class SummaryDataMeasure implements DataMeasure {
    
    private static final long serialVersionUID = 1054824969202050369L;
    private double total = 0.0D;
    private double sumOfSquares = 0.0D;
    private long nbrDataItems = 0L;
    private double minimum = Double.MAX_VALUE;
    private double maximum = Double.MIN_VALUE;
    private long firstObservationTime;
    private long lastObservationTime;
    
    /**
     * @deprecated - Only provided so reading of earlier version performance stats works.
     */
    public SummaryDataMeasure() {
        this(System.currentTimeMillis());
    }
    
    public SummaryDataMeasure(long firstObservationTime) {
        this.firstObservationTime = firstObservationTime;
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#reset()
     */
    public synchronized void  reset() {
        total = 0.0D;
        sumOfSquares = 0.0D;
        nbrDataItems = 0L;
        minimum = Double.MAX_VALUE;
        maximum = Double.MIN_VALUE;
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#addNumber(java.lang.Number)
     */
    public synchronized void addNumber(Number number) {
        Validate.notNull(number, "Null number not allowed");
        this.lastObservationTime = System.currentTimeMillis();
        
        total += number.doubleValue();
        sumOfSquares += Math.pow(number.doubleValue(), 2);
        nbrDataItems++;
        if (number.doubleValue() < minimum)  minimum = number.doubleValue();
        if (number.doubleValue() > maximum)  maximum = number.doubleValue();
    }
 

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#purgeObsoleteObservations()
     */
    public void purgeObsoleteObservations() {
        // No Op
        
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#getDataMeasurementSummary()
     */
    public synchronized DataMeasurementSummaryVO getDataMeasurementSummary() {
        DataMeasurementSummaryVO summary = new DataMeasurementSummaryVO();
        summary.setLabel("Summary for all observations");
        summary.setSummaryType(DataMeasurementSummaryVO.SummaryType.SUMMARY);        
        summary.setNbrDataItems(this.nbrDataItems);
        summary.setTotal(this.total);
        if (nbrDataItems > 0) {
            summary.setMinimum(this.minimum);
            summary.setMaximum(this.maximum);
            summary.setAverage(total / (double)nbrDataItems);
            summary.setVariance( (this.sumOfSquares / (double)this.nbrDataItems) - Math.pow(summary.getAverage(), 2));
            summary.setStandardDeviation(Math.sqrt(summary.getVariance()));
            summary.setFirstObservationDate(new Date(this.firstObservationTime));
            summary.setLastObservationDate(new Date(this.lastObservationTime));
        }
       
        return summary;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSumOfSquares() {
        return sumOfSquares;
    }

    public void setSumOfSquares(double sumOfSquares) {
        this.sumOfSquares = sumOfSquares;
    }

    public long getNbrDataItems() {
        return nbrDataItems;
    }

    public void setNbrDataItems(long nbrDataItems) {
        this.nbrDataItems = nbrDataItems;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public long getFirstObservationTime() {
        return firstObservationTime;
    }

    public void setFirstObservationTime(long firstObservationTime) {
        this.firstObservationTime = firstObservationTime;
    }

    public long getLastObservationTime() {
        return lastObservationTime;
    }

    public void setLastObservationTime(long lastObservationTime) {
        this.lastObservationTime = lastObservationTime;
    }

}
