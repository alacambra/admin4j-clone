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

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache which will time and expire entries on a configurable basis.  This is
 * intended to be thread-safe.
 * 
 * <p>Note:  
 * <li>Purge and expiration intervals intentionally made immutable.</li>
 * <li>Nulling out a reference isn't enough to garbage-collect an ExpiringCache -- 
 * Once it's instantiated, it'll be permanently referenced by the JVM.</li>
 * 
 * @author D. Ashmore
 *
 * 
 */
public class ExpiringCache 
{
    private final long	purgeIntervalInMillis;
    private final long  expirationTimeInMillis;
    
    private long nextPurgeTime;
    
    private final ConcurrentHashMap<Object, Object> cacheMap = new ConcurrentHashMap<Object, Object>();
    
    public static final long DEFAULT_PURGE_INTERVAL_IN_MILLIS = 60000 * 60 * 4; 	// 4 hours
    public static final long DEFAULT_EXPIRATION_TIME_IN_MILLIS = 60000 * 60; 		// 1 hour
    
    private static final ExpiringCacheCleanupTask     CLEANUP_TASK = new ExpiringCacheCleanupTask();
    @SuppressWarnings("unused")
    private static final Daemon CLEANUP_DAEMON = new Daemon(CLEANUP_TASK, "Admin4J-Expiring-Cache-Cleanup", 600000);
    
    
    /**
     * Purge and expiration times defaulted.
     *
     */
    public ExpiringCache()
    {
        this.purgeIntervalInMillis = DEFAULT_PURGE_INTERVAL_IN_MILLIS;
        this.expirationTimeInMillis = DEFAULT_EXPIRATION_TIME_IN_MILLIS;
        this.setNextPurgeTime();
        CLEANUP_TASK.registerCache(this);
    }
    
    public ExpiringCache(long expirationTimeMillis, long purgeIntervalMillis)
    {
        if (expirationTimeMillis < 0) 
            throw new IllegalArgumentException("Negative expiration not allowed: " + expirationTimeMillis);
        if (purgeIntervalMillis < 0) 
            throw new IllegalArgumentException("Negative purge interval not allowed: " + expirationTimeMillis);

        this.purgeIntervalInMillis = purgeIntervalMillis;
        this.expirationTimeInMillis = expirationTimeMillis;
        this.setNextPurgeTime();
        CLEANUP_TASK.registerCache(this);
    }
    
    private synchronized void setNextPurgeTime()
    {
   	    this.nextPurgeTime = System.currentTimeMillis() + this.purgeIntervalInMillis;
    }
    
     /**
     * Iterate through content and purge all expiring entries.
     *
     */
    @SuppressWarnings("rawtypes")
    protected synchronized void purgeExpiredContent()
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime < this.nextPurgeTime)  return;
        
        this.setNextPurgeTime();

        Set entrySet = this.cacheMap.entrySet();
        Iterator entryIt = entrySet.iterator();
        DatedValue dv;
        ConcurrentHashMap.Entry entry;
        
        // Iterate through the entries purging those that are expired.
        while (entryIt.hasNext())
        {
            entry = (ConcurrentHashMap.Entry) entryIt.next();
            if (entry.getValue() != null)
            {
                dv = (DatedValue) entry.getValue();
                if (currentTime > dv.expirationTime)
                {
                    this.cacheMap.remove(entry.getKey(), entry.getValue());
                }
            }
        }
    }
    
    /**
     * Retrieves an object from the cache.
     * @param key
     * @return
     */
    public Object get(Object key)
    {
        if (key == null)  throw new IllegalArgumentException("Null key not allowed.");
        
        Object obj = this.cacheMap.get(key);
        Object value = null;
        
        if (obj != null)
        {
            DatedValue dv = (DatedValue) obj;
            if (System.currentTimeMillis() > dv.expirationTime)
            {
                this.cacheMap.remove(key, obj);     // Value past expiration
            }
            else value = dv.value;
        }
        
        return value;
    }
    
    /**
     * Puts an object in cache.
     * @param key
     * @param value
     */
    public void put(Object key, Object value)
    {
        if (key == null)  throw new IllegalArgumentException("Null key not allowed.");
        if (value == null)  throw new IllegalArgumentException("Null value not allowed.");
        
        this.cacheMap.put(key, new DatedValue(value));
    }
    
    /**
     * Removes an object from cache.
     * @param key
     * @return
     */
    public void remove(Object key)
    {
        if (key == null)  throw new IllegalArgumentException("Null key not allowed.");
        
        this.cacheMap.remove(key);
    }
    
    public int size()
    {
        return this.cacheMap.size();
    }
    
    /**
     * Clears all entries in cache.
     *
     */
    public synchronized void clear()
    {
        this.setNextPurgeTime();
        this.cacheMap.clear();
    }
    
    /**
     * Clears all content for all expiring caches.  
     * 
     * <p>Warning:  Should only be called for administrative and testing purposes.
     *
     */
    public static synchronized void clearAll()
    {
        CLEANUP_TASK.clearAllCacheContent();
    }
    
    private class DatedValue
    {
        public final long expirationTime = System.currentTimeMillis() + expirationTimeInMillis;
        public Object value;
        
        public DatedValue(Object v)
        {
            this.value = v;
        }
    }

    public long getExpirationTimeInMillis() {
        return expirationTimeInMillis;
    }

}
