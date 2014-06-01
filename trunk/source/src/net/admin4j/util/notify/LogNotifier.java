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

import net.admin4j.deps.commons.lang3.SystemUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Places a notification in the log.
 * @author D. Ashmore
 * @since 1.0
 */
public class LogNotifier implements Notifier {

    private static Logger logger = LoggerFactory.getLogger(LogNotifier.class);
    
    public void configure(ServletConfig config) throws ServletException {
        // No Op

    }

    public void configure(FilterConfig config) throws ServletException {
        // No Op

    }

    public void configure(String namePrefix, Properties config) {
        // No Op

    }

    public void notify(String subject, String message) {
        StringBuffer buffer = new StringBuffer(1024);
        
        if (subject != null) {
            buffer.append(subject);
            buffer.append(SystemUtils.LINE_SEPARATOR);
        }
        if (message != null) {
            buffer.append(message);
        }

        logger.error(message);
    }

    public boolean supportsHtml() {
        return false;
    }

    public boolean supportsSMS() {
        return false;
    }

}
