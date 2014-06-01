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


import java.util.ArrayList;

/**
 * Daemon cleaning up expired entries in registered ExpiringCache objects.
 * 
 * @author D. Ashmore
 *
 * 
 */
class ExpiringCacheCleanupTask implements Runnable
{
    
    private ArrayList<ExpiringCache> expiringCacheList = new ArrayList<ExpiringCache>();
    
    /**
     * Registers cache for periodic cleanup. 
     * @param cache
     */
    protected void registerCache(ExpiringCache cache)
    {
        if (cache == null)  throw new IllegalArgumentException("Null cache not allowed.");
        synchronized (this.expiringCacheList)
        {
            this.expiringCacheList.add(cache);
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        ExpiringCache cache;
        
        synchronized (this.expiringCacheList)
        {
            for (int i = 0; i < this.expiringCacheList.size(); i++)
            {
                cache = (ExpiringCache) this.expiringCacheList.get(i);
                cache.purgeExpiredContent();
            }
        }
    }
    
    /**
     * Clears all entries in all caches.  This is needed for admin and testing use.
     *
     */
    public void clearAllCacheContent()
    {
        ExpiringCache cache;
        
        synchronized (this.expiringCacheList)
        {
            for (int i = 0; i < this.expiringCacheList.size(); i++)
            {
                cache = (ExpiringCache) this.expiringCacheList.get(i);
                cache.clear();
            }
        }
    }
}
