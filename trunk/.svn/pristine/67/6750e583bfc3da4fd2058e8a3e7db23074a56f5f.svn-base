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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRollingTimePeriodDataMeasure {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        RollingTimePeriodDataMeasure measure = new RollingTimePeriodDataMeasure();
        Assert.assertTrue(RollingTimePeriodDataMeasure.DEFAULT_TIMER_PERIOD_IN_MILLIS.equals(measure.getTimePeriodInMillis()));
        
        measure = new RollingTimePeriodDataMeasure(100);
        addData(measure);
        measure.purgeObsoleteObservations();
        
        Assert.assertTrue(measure.getObservationSet().size() > 0);
        Assert.assertTrue(measure.getDataMeasurementSummary().getNbrDataItems() > 0);
        
        Thread.currentThread().sleep(105);
        measure.purgeObsoleteObservations();
        Assert.assertTrue(measure.getObservationSet().size() == 0);
        Assert.assertTrue(measure.getDataMeasurementSummary().getNbrDataItems() == 0);
        
        addData(measure);
        measure.reset();
        Assert.assertTrue(measure.getObservationSet().size() == 0);
        Assert.assertTrue(measure.getDataMeasurementSummary().getNbrDataItems() == 0);
    }

    protected void addData(RollingTimePeriodDataMeasure measure) {
        measure.addNumber(Integer.valueOf(1));
        measure.addNumber(Integer.valueOf(2));
        measure.addNumber(Integer.valueOf(3));
        measure.addNumber(Integer.valueOf(4));
        measure.addNumber(Integer.valueOf(5));
    }

}
