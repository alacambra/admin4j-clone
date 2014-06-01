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

import java.io.Serializable;

/**
 * Describes memory utilization of the current runtime VM recorded at a specific point in time.
 * @author D. Ashmore
 * @since 1.0
 */
public class MemoryUtilizationVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2708169198765518131L;
    
    private long currentMemoryAllocatedInBytes;
    private long freeCurrentMemoryInbytes;
    private long maxMemoryInBytes;
    
    public MemoryUtilizationVO(long currentMemoryAllocatedInBytes, 
            long freeMemoryInbytes, long maxMemoryInBytes) {
        this.currentMemoryAllocatedInBytes = currentMemoryAllocatedInBytes;
        this.freeCurrentMemoryInbytes = freeMemoryInbytes;
        this.maxMemoryInBytes = maxMemoryInBytes;
    }
    
    public long getMemoryInUseInBytes() {
        return this.getCurrentMemoryAllocatedInBytes() - this.getFreeCurrentMemoryInbytes();
    }
    
    public long getFreeAvailableMemoryInBytes() {
        return this.getMaxMemoryInBytes() - this.getMemoryInUseInBytes();
    }
    
    public double getFreeAvailableMemoryInMb() {
        return (double)(this.getMaxMemoryInBytes() - this.getMemoryInUseInBytes()) / (double) 1024000 ;
    }
    
    public long getPercentMemoryAvailable() {
        return (100 * this.getFreeAvailableMemoryInBytes()) / this.getMaxMemoryInBytes();
    }
    
    public long getPercentMemoryUsed() {
        return 100 - this.getPercentMemoryAvailable();
    } 
    
    public long getCurrentMemoryAllocatedInBytes() {
        return currentMemoryAllocatedInBytes;
    }
    
    public long getFreeCurrentMemoryInbytes() {
        return freeCurrentMemoryInbytes;
    }
    
    public long getMaxMemoryInBytes() {
        return maxMemoryInBytes;
    }
    
    public long getMaxMemoryInBytesInMb() {
        return this.getMaxMemoryInBytes() / 1024000 ;
    }

    /* (non-Javadoc)
     * @see net.admin4j.vo.BaseVO#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new MemoryUtilizationVO(currentMemoryAllocatedInBytes, 
            this.freeCurrentMemoryInbytes, maxMemoryInBytes);
    }

}
