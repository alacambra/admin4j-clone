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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.admin4j.vo.DataMeasurementSummaryVO;

/**
 * Keeps the last given number of observations of a series of data measurements.
 * @author D. Ashmore
 * @since 1.0
 */
public class RollingNbrObservationsDataMeasure implements DataMeasure {
    
    private static final long serialVersionUID = -240040580088773258L;
    public static final int DEFAULT_NUMBER_ROLLING_OBSERVATIONS=100;
    private int nbrRollingObservations = DEFAULT_NUMBER_ROLLING_OBSERVATIONS;
    private Number[] observations;
    private Long[] observationTime;
    private int currentObservationOffset = 0;
    
    public RollingNbrObservationsDataMeasure() {
        this(DEFAULT_NUMBER_ROLLING_OBSERVATIONS);
    }
    
    public RollingNbrObservationsDataMeasure(int nbrRollingObservations) {
        this.nbrRollingObservations = nbrRollingObservations;
        this.observations = new Number[this.nbrRollingObservations];
        this.observationTime = new Long[this.nbrRollingObservations];
    }

    public void addNumber(Number number) {
        synchronized (observations) {
            if (this.currentObservationOffset >= this.observations.length) {
                this.currentObservationOffset = 0;
            }
            this.observations[this.currentObservationOffset] = number;
            this.observationTime[this.currentObservationOffset] = System.currentTimeMillis();
            this.currentObservationOffset++;
        }

    }

    public DataMeasurementSummaryVO getDataMeasurementSummary() {
        List<Number> valueList = new ArrayList<Number>();
        long firstObsTime = Long.MAX_VALUE;
        long lastObsTime = Long.MIN_VALUE;
        
        synchronized (observations) {
            for (int i = 0; i < observations.length; i++) {
                if (observations[i] != null) {
                    valueList.add(observations[i]);
                    if (this.observationTime[i] < firstObsTime) {
                        firstObsTime = this.observationTime[i];
                    }
                    if (this.observationTime[i] > lastObsTime) {
                        lastObsTime = this.observationTime[i];
                    }
                }
            }
        }
        DataMeasurementSummaryVO summary = DataMeasurementSummaryVO.fromValueList(valueList);
        summary.setLabel("Last " + this.nbrRollingObservations + " observations");
        summary.setSummaryType(DataMeasurementSummaryVO.SummaryType.ROLLING_NBR_OBS);
        
        if (firstObsTime < Long.MAX_VALUE) {
            summary.setFirstObservationDate(new Date(firstObsTime));
        }
        if (lastObsTime > Long.MIN_VALUE) {
            summary.setLastObservationDate(new Date(lastObsTime));
        }
        return summary;
    }

    public void purgeObsoleteObservations() {
        // NoOp

    }

    public void reset() {
        synchronized (observations) {
            this.currentObservationOffset = 0;
            for (int i = 0; i < observations.length; i++) {
                observations[i] = null;
            }
        }

    }

    public int getNbrRollingObservations() {
        return nbrRollingObservations;
    }

    public void setNbrRollingObservations(int nbrRollingObservations) {
        this.nbrRollingObservations = nbrRollingObservations;
    }

    public Number[] getObservations() {
        return observations;
    }

    public void setObservations(Number[] observations) {
        this.observations = observations;
    }

    public Long[] getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(Long[] observationTime) {
        this.observationTime = observationTime;
    }

    public int getCurrentObservationOffset() {
        return currentObservationOffset;
    }

    public void setCurrentObservationOffset(int currentObservationOffset) {
        this.currentObservationOffset = currentObservationOffset;
    }

}
