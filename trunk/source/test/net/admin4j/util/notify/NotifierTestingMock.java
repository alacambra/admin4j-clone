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
package net.admin4j.util.notify;

import java.util.Properties;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class NotifierTestingMock implements Notifier {
    
    private boolean supportsHtml = false;
    private boolean supportsSMS = false;
    
    private String subject = null;
    private String message = null;

    public void configure(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

    public void configure(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

    public void notify(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public boolean supportsHtml() {
        return supportsHtml;
    }
    
    public void setSupportsHtml(boolean b) {
        this.supportsHtml = b;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
    
    public void reset() {
        this.message = null;
        this.subject = null;
    }

    /* (non-Javadoc)
     * @see net.admin4j.util.notify.Notifier#supportsSMS()
     */
    public boolean supportsSMS() {
        return supportsSMS;
    }

    public void setSupportsSMS(boolean supportsSMS) {
        this.supportsSMS = supportsSMS;
    }

    /* (non-Javadoc)
     * @see net.admin4j.util.notify.Notifier#configure(java.lang.String, java.util.Properties)
     */
    public void configure(String namePrefix, Properties config) {
        // TODO Auto-generated method stub
        
    }

}
