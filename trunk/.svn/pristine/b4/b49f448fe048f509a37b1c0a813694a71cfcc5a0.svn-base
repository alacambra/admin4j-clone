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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import net.admin4j.deps.commons.collections.iterators.IteratorEnumeration;

public class HttpSessionMock implements HttpSession {
    
    Map<String, Object> attributeMap = new HashMap<String, Object>();

    public Object getAttribute(String attr) {
        return attributeMap.get(attr);
    }

    public Enumeration getAttributeNames() {
        return new IteratorEnumeration(attributeMap.keySet().iterator());
    }

    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getValue(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getValueNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public void invalidate() {
        // TODO Auto-generated method stub

    }

    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }

    public void putValue(String arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    public void removeAttribute(String attr) {
        attributeMap.remove(attr);

    }

    public void removeValue(String arg0) {
        // TODO Auto-generated method stub

    }

    public void setAttribute(String attr, Object value) {
        attributeMap.put(attr, value);

    }

    public void setMaxInactiveInterval(int arg0) {
        // TODO Auto-generated method stub

    }

}
