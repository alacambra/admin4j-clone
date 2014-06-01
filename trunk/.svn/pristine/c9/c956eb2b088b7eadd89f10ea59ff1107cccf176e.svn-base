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
package net.admin4j.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.admin4j.deps.commons.beanutils.BeanComparator;
import net.admin4j.deps.commons.collections.comparators.ReverseComparator;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.deps.commons.lang3.builder.ToStringBuilder;

/**
 * Describes a Class/method/line that was executed at some point along with 
 * information about what it calls.
 * @author D. Ashmore
 *
 */
public class ExecutionPoint implements Cloneable {
    
    /*
     * Note:  ANY field additions require an enhancement to method clone().
     */
    private StackTraceElement stackTraceElement;
    private Map<StackTraceElement, Long> calledStackTraceElementMap = new HashMap<StackTraceElement, Long>();
    private Map<StackTraceElement, Long> callingStackTraceElementMap = new HashMap<StackTraceElement, Long>();
    private Map<String, Long> blockingSynchronizedClassMap = new HashMap<String, Long>();
    private Object mapLock = new Object();
    
    private long nbrExecutions = 0;
    private long nbrBlockedExecutions = 0;
    private long createTimestampInMillis = System.currentTimeMillis();
    
    public ExecutionPoint(StackTraceElement stackTraceElement) {
        Validate.notNull(stackTraceElement, "Null stackTraceElement not allowed.");
        this.stackTraceElement = stackTraceElement;
    }

    public long getNbrExecutions() {
        return nbrExecutions;
    }

    public void setNbrExecutions(long nbrExecutions) {
        this.nbrExecutions = nbrExecutions;
    }

    public StackTraceElement getStackTraceElement() {
        return stackTraceElement;
    }

    @SuppressWarnings("unchecked")
    public List<Entry<StackTraceElement, Long>> getCalledStackTraceElementList() {
        List<Entry<StackTraceElement, Long>> list = new ArrayList<Entry<StackTraceElement, Long>>();
        synchronized(mapLock) {
            list.addAll(calledStackTraceElementMap.entrySet());
        }
        Collections.sort(list, new ReverseComparator(new BeanComparator("value")));
        return list;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.stackTraceElement.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)  return false;
        if ( !(obj instanceof ExecutionPoint) ) {
            return false;
        }
        ExecutionPoint ep = (ExecutionPoint)obj;
        return ep.stackTraceElement.equals(this.stackTraceElement);
    }

    public long getCreateTimestampInMillis() {
        return createTimestampInMillis;
    }

    public void setCreateTimestampInMillis(long createTimestampInMillis) {
        this.createTimestampInMillis = createTimestampInMillis;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        ExecutionPoint newPoint = new ExecutionPoint(this.stackTraceElement);
        newPoint.nbrExecutions = this.nbrExecutions;
        newPoint.nbrBlockedExecutions = this.nbrBlockedExecutions;
        newPoint.createTimestampInMillis = this.createTimestampInMillis;
        synchronized (mapLock) {
            newPoint.calledStackTraceElementMap.putAll(this.calledStackTraceElementMap); 
            newPoint.callingStackTraceElementMap.putAll(this.callingStackTraceElementMap);
            newPoint.blockingSynchronizedClassMap.putAll(this.blockingSynchronizedClassMap);
        }
        return newPoint;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        synchronized (mapLock) {
            return new ToStringBuilder(this).
            append("stackTraceElement", stackTraceElement).
            append("nbrExecutions", nbrExecutions).
            append("createTimestampInMillis", createTimestampInMillis).
            append("calledStackTraceElementMap", calledStackTraceElementMap).
            append("callingStackTraceElementMap", callingStackTraceElementMap).
            append("blockingSynchronizedClassMap", blockingSynchronizedClassMap).
            toString();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Entry<StackTraceElement, Long>> getCallingStackTraceElementList() {
        List<Entry<StackTraceElement, Long>> list = new ArrayList<Entry<StackTraceElement, Long>>();
        synchronized(mapLock) {
            list.addAll(callingStackTraceElementMap.entrySet());
        }
        Collections.sort(list, new ReverseComparator(new BeanComparator("value")));
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Entry<String, Long>> getBlockingSynchronizedClassList() {
        List<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>();
        synchronized(mapLock) {
            list.addAll(blockingSynchronizedClassMap.entrySet());
        }
        Collections.sort(list, new ReverseComparator(new BeanComparator("value")));
        return list;
    }

    public long getNbrBlockedExecutions() {
        return nbrBlockedExecutions;
    }

    public void setNbrBlockedExecutions(long nbrBlockedExecutions) {
        this.nbrBlockedExecutions = nbrBlockedExecutions;
    }
    
    public void addCallingStackTraceElement(StackTraceElement element) {
        Validate.notNull(element, "Null element not allowed.");
        
        synchronized(mapLock) {
            Long nbrExecutions = callingStackTraceElementMap.get(element);
            if (nbrExecutions == null) {
                callingStackTraceElementMap.put(element, Long.valueOf(1));
            }
            else callingStackTraceElementMap.put(element, Long.valueOf(nbrExecutions + 1));
        }
    }
    
    public void addCalledStackTraceElement(StackTraceElement element) {
        Validate.notNull(element, "Null element not allowed.");
        
        synchronized(mapLock) {
            Long nbrExecutions = calledStackTraceElementMap.get(element);
            if (nbrExecutions == null) {
                calledStackTraceElementMap.put(element, Long.valueOf(1));
            }
            else calledStackTraceElementMap.put(element, Long.valueOf(nbrExecutions + 1));
        }
    }
    
    public void addBlockingSynchronizedClassName(String className) {
        Validate.notEmpty(className, "Null or blank className not allowed.");
        
        String localClassName = className;
        int atOffset = localClassName.indexOf("@");
        if (atOffset > 0) {
            localClassName = localClassName.substring(0, atOffset);
        }
        
        synchronized(mapLock) {
            Long nbrBlocks = blockingSynchronizedClassMap.get(localClassName);
            if (nbrBlocks == null) {
                blockingSynchronizedClassMap.put(localClassName, Long.valueOf(1));
            }
            else blockingSynchronizedClassMap.put(localClassName, Long.valueOf(nbrBlocks + 1));
        }
    }

}
