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
package net.admin4j.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import net.admin4j.deps.commons.lang3.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Daemon worker thread that iterates through a work/sleep cycle forever.
 * @author D. Ashmore
 *
 */
public class Daemon extends Thread {
    
    private static final AtomicBoolean jvmInShutdown = new AtomicBoolean(false);
    static {
        Runtime.getRuntime().addShutdownHook(new DaemonShutdownHook());
    }
    
    private Runnable task;
    
    static final long DEFAULT_SLEEP_TIME_IN_MILLIS = 600000;   // 10 minutes
    private final AtomicLong sleepTime = new AtomicLong(DEFAULT_SLEEP_TIME_IN_MILLIS);
    
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public Daemon(Runnable task, String threadName) {        
        this(task, threadName, (Long)null);
    }
    
    /**
     * @Deprecated Use Long constructor instead
     * @param task
     * @param threadName
     * @param sleepTimeInMillis
     */
    public Daemon(Runnable task, String threadName, Integer sleepTimeInMillis) {
        this(task, threadName, sleepTimeInMillis == null ? (Long)null: sleepTimeInMillis.longValue());
    }
    
    public Daemon(Runnable task, String threadName, Long sleepTimeInMillis) {        
        Validate.notNull(task, "Null task not allowed.");
        Validate.notEmpty(threadName, "Null or blank threadName not allowed.");
        this.setDaemon(true);
        this.setName(threadName);
        
        if (sleepTimeInMillis != null && sleepTimeInMillis > 0) {
            this.sleepTime.set(sleepTimeInMillis);
        }
        
        this.task = task;
        this.start();
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        try
        {
            while (true)
            {
                Thread.sleep(sleepTime.get());
                try {this.task.run();}
                catch (Throwable t)
                {
                    logger.error(this.getName() + " errored out.", t);
                }
            }
        }
        catch (Throwable t)
        {
            if ( !getJvminshutdown().get() ) {
                logger.error(this.getName() + " restarted.", t);
                this.start();
            }
        }
    }

    public Runnable getTask() {
        return task;
    }

    static AtomicBoolean getJvminshutdown() {
        return jvmInShutdown;
    }

}

class DaemonShutdownHook extends Thread {

    @Override
    public void run() {
        Daemon.getJvminshutdown().set(true);
    }
    
}
