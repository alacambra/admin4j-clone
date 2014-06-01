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
package net.admin4j.hotspot;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Initiates tracking of all currently running threads.
 * @author D. Ashmore
 * @since 1.0
 */
class ExecutionTrackingTask implements Runnable {
    
    private AtomicLong nbrTaskExecutions = new AtomicLong(0);

    public void run() {
        ExecutionTracker.trackAll();
        nbrTaskExecutions.incrementAndGet();
    }

    public long getNbrTaskExecutions() {
        return nbrTaskExecutions.get();
    }

}
