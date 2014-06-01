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

/**
 * Summarizes performance measurements.
 * @author D. Ashmore
 * @since 1.0
 */
public class PerformanceSummaryVO extends BaseVO {
    
    private static final long serialVersionUID = 1280316263958819876L;
    private String label;
    private DataMeasurementSummaryVO summaryMeasurement;
    private DataMeasurementSummaryVO rollingTimeMeasurement;
    private DataMeasurementSummaryVO rollingNbrObservationsMeasurement;
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public DataMeasurementSummaryVO getSummaryMeasurement() {
        return summaryMeasurement;
    }
    
    public void setSummaryMeasurement(DataMeasurementSummaryVO summaryMeasurement) {
        this.summaryMeasurement = summaryMeasurement;
    }
    
    public DataMeasurementSummaryVO getRollingTimeMeasurement() {
        return rollingTimeMeasurement;
    }
    
    public void setRollingTimeMeasurement(
            DataMeasurementSummaryVO rollingTimeMeasurement) {
        this.rollingTimeMeasurement = rollingTimeMeasurement;
    }
    
    public DataMeasurementSummaryVO getRollingNbrObservationsMeasurement() {
        return rollingNbrObservationsMeasurement;
    }
    
    public void setRollingNbrObservationsMeasurement(
            DataMeasurementSummaryVO rollingNbrObservationsMeasurement) {
        this.rollingNbrObservationsMeasurement = rollingNbrObservationsMeasurement;
    }

}
