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

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;

import net.admin4j.deps.commons.lang3.Validate;

/**
 * This is a rolling list with a defined maximum size.  As elements are added beyond
 * the maxSize, elements are removed from the front of the list.
 * @author D. Ashmore
 * @since 1.0.2
 */
public class FixedSizeRollingList<E> extends AbstractList<E> implements Serializable {
    
    private static final long serialVersionUID = -6973689738559294580L;

    public static final int DEFAULT_MAX_SIZE=10;
    
    private Object[] elementData;
    private int maxSize;
    private int size;
    
    public FixedSizeRollingList() {
        this(DEFAULT_MAX_SIZE);
    }
    
    public FixedSizeRollingList(int maxSize) {
        Validate.isTrue(maxSize > 0, "Max Size must be greater than zero.  maxSize={}", maxSize);
        this.maxSize = maxSize;
        this.elementData = new Object[this.maxSize];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        return (E) elementData[index];
    }

    @Override
    public int size() {
         return this.size;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
        E removed = this.get(index);
        this.elementData[index] = element;
        return removed;
    }
    
    /* (non-Javadoc)
     * @see java.util.AbstractList#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        if (this.size == this.maxSize) {
            this.remove(0);
        }
        this.add(this.size, e);
        return true;
    }


    /* (non-Javadoc)
     * @see java.util.AbstractList#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, E element) {
        this.maxRangeCheck(index);
        if (index == this.size) {
            this.elementData[size++] = element;
        }
        else if (index < size) {
            if (this.size == this.maxSize) {
                this.remove(this.size - 1);
            }
            
            int numMoved = this.size - index;
            System.arraycopy(this.elementData, index, this.elementData, index+1,
                             numMoved);
            this.elementData[index] = element;
            this.size++;
        }
        else {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }
    
    /* (non-Javadoc)
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Validate.notNull(c, "Null collection not allowed.");
        if (c.isEmpty()) {
            return true;
        }
        
        for (E ele: c) {
            this.add(ele);
        }
        return super.addAll(c);
    }
    
    /* (non-Javadoc)
     * @see java.util.AbstractList#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Validate.notNull(c, "Null collection not allowed.");
        if (c.isEmpty()) {
            return true;
        }
        if (this.size + c.size() > this.maxSize) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index) + ": No room to add collection of this at this index.");
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#remove(int)
     */
    @Override
    public E remove(int index) {
        E removed = this.get(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        }
        elementData[--size] = null;
        return removed;
    }

    public int getMaxSize() {
        return maxSize;
    }
    
    private void maxRangeCheck(int index) {
        if (index >= this.maxSize || index > this.size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

}
