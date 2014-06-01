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
package net.admin4j.exception;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.dao.DAOFactory;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.time.DateUtils;
import net.admin4j.entity.ExceptionInfo;

/**
 * Will clean up dated entries in the Exception Tracker so memory usage is contained (e.g. doesn't leak').
 * @author D. Ashmore
 * @since 1.0
 */
class ExceptionTrackerCleanupTask implements Runnable {
    
    static final int DEFAULT_PURGE_THRESHOLD_IN_DAYS = 30;  
    private AtomicInteger purgeThresholdInDays = new AtomicInteger(DEFAULT_PURGE_THRESHOLD_IN_DAYS);

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    public void run() {

        Date purgeDt = DateUtils.addDays(new Date(), -1 * purgeThresholdInDays.get());
        
        Set<ExceptionInfo> exceptionSet = ExceptionTracker.getExceptionInfoSet();
        for (ExceptionInfo eInfo: exceptionSet) {
            if (purgeDt.getTime() > eInfo.getLastOccuranceDt().getTime()) {
                ExceptionTracker.purgeException(eInfo);
            }
        }
        
        if (Admin4JConfiguration.isExceptionInfoStored()) {
            DAOFactory.getExceptionInfoDAO().saveAll(exceptionSet);
        }
 
    }
    
    public void setPurgeThresholdInDays(Integer days) {
        Validate.notNull(days, "Purge threshhold cannot be null");
        Validate.isTrue(days > 0, "Purge threshhold must be larger than zero");
        purgeThresholdInDays.set(days);
    }
    
}
