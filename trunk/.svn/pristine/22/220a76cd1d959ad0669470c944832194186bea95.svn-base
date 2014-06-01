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

/**
 * Capable of notifying an administrator.
 * @author D. Ashmore
 * @since 1.0
 */
public interface Notifier {
    
    /**
     * Will configure the Notifier directly from a Servlet configuration.
     * @param config
     * @throws ServletException
     */
    public void configure(ServletConfig config) throws ServletException;
    
    /**
     * Will configure the Notifier directly from a Servlet Filter configuration.
     * @param config
     * @throws ServletException
     */
    public void configure(FilterConfig config) throws ServletException;
    
    /**
     * Will configure the Notifier from a  properties configufation
     * @param namePrefix
     * @param config
     * @throws ServletException
     */
    public void configure(String namePrefix, Properties config);
    
    /**
     * Will perfom a notification.
     * @param subject
     * @param message
     */
    public void notify(String subject, String message);
    
    /**
     * Indicates if this notifier supports HTML-formatted messages.
     * @return
     */
    public boolean supportsHtml();
    
    /**
     * Indicates if this notifier support SMS, which usually infers a restricted message size.
     * @return
     */
    public boolean supportsSMS();

}
