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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.time.DurationFormatUtils;
import net.admin4j.vo.DataMeasurementSummaryVO;

/**
 * Will keep observations for the last specified time period (e.g. all observations 
 * from the last 10 minutes)
 * @author D. Ashmore
 *
 */
public class RollingTimePeriodDataMeasure implements DataMeasure {
    
    private static final long serialVersionUID = -4023496364533544416L;
    public static final Long DEFAULT_TIMER_PERIOD_IN_MILLIS=60000*60L; // 1 hr
    private Long timePeriodInMillis;
    private Set<TimedObservation> observationSet = new HashSet<TimedObservation>();
    
    public RollingTimePeriodDataMeasure() {
        this(DEFAULT_TIMER_PERIOD_IN_MILLIS);
    }
    
    public RollingTimePeriodDataMeasure(long timePeriodInMillis) {
        this.timePeriodInMillis = timePeriodInMillis;
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#addNumber()
     */
    public synchronized void addNumber(Number number) {
        Validate.notNull(number, "Null number not allowed.");
        observationSet.add(new TimedObservation(number));
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#reset()
     */
    public synchronized void reset() {
        this.observationSet.clear();

    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#purgeObsoleteObservations()
     */
    public synchronized void purgeObsoleteObservations() {
        long earliestTime = getEarliestObservationTime();
        Set<TimedObservation> removedObservations = new HashSet<TimedObservation>();
        for (TimedObservation obs: this.observationSet) {
            if (obs.getTime() < earliestTime) {
                removedObservations.add(obs);
            }
        }
        
        this.observationSet.removeAll(removedObservations);        
    }

    private long getEarliestObservationTime() {
        long earliestTime = System.currentTimeMillis() - this.timePeriodInMillis;
        return earliestTime;
    }
    
    private static class TimedObservation {
        private long time;
        private Number value;
        
        public TimedObservation(Number value) {
            this.value = value.doubleValue();
            this.time = System.currentTimeMillis();
        }
        public long getTime() {
            return time;
        }
        public Number getValue() {
            return value;
        }
    }

    /* (non-Javadoc)
     * @see net.admin4j.timer.DataMeasure#getDataMeasurementSummary()
     */
    public synchronized DataMeasurementSummaryVO getDataMeasurementSummary() {
        List<Number> valueList = new ArrayList<Number>();
        long firstObsTime = Long.MAX_VALUE;
        long lastObsTime = Long.MIN_VALUE;
        
        long earliestTime = getEarliestObservationTime();
        for (TimedObservation obs: this.observationSet) {
            if (obs.getTime() >= earliestTime) {
                valueList.add(obs.getValue());
                if (obs.getTime() < firstObsTime) {
                    firstObsTime = obs.getTime();
                }
                if (obs.getTime() > lastObsTime) {
                    lastObsTime = obs.getTime();
                }
            }
        }
        DataMeasurementSummaryVO summary = DataMeasurementSummaryVO.fromValueList(valueList);
        summary.setLabel("Observations since " + DurationFormatUtils.formatDuration(this.timePeriodInMillis, "H hours, mm min."));
        summary.setSummaryType(DataMeasurementSummaryVO.SummaryType.ROLLING_TIME);
        if (firstObsTime < Long.MAX_VALUE) {
            summary.setFirstObservationDate(new Date(firstObsTime));
        }
        if (lastObsTime > Long.MIN_VALUE) {
            summary.setLastObservationDate(new Date(lastObsTime));
        }
        return summary;
    }

    public Long getTimePeriodInMillis() {
        return timePeriodInMillis;
    }

    protected void setTimePeriodInMillis(Long timePeriodInMillis) {
        this.timePeriodInMillis = timePeriodInMillis;
    }

    public Set<TimedObservation> getObservationSet() {
        return observationSet;
    }

    public void setObservationSet(Set<TimedObservation> observationSet) {
        this.observationSet = observationSet;
    }

}
