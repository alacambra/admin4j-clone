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
package net.admin4j.ui.filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.notify.Notifier;
import net.admin4j.util.notify.NotifierUtils;


/**
 * Base filter that accepts a configured notifier.
 * 
 * <p>Init parameters for this filter are as follows:</p>
 * <li>notifier -- Required.  Handles admin notification.  See documentation for the Notifier you're using
 * for any additional configuration requirements.  For example, 'net.admin4j.util.notify.EmailNotifier'.</li>
 * 
 * @author D. Ashmore
 * @since 1.0
 */
public abstract class BaseNotificationFilter extends BaseFilter implements Filter {

    protected Notifier notifier;
    public void init(FilterConfig config) throws ServletException {
        try {
            new Admin4JConfiguration();
            String notifierClassName = config.getInitParameter("notifier");
            
            this.notifier = NotifierUtils.configure(config, notifierClassName);
            if (this.notifier == null) {
                throw new Admin4jRuntimeException("Neither default notifier provided nor notifier parameter for Filter specified.");
            }
            
        } catch (Throwable e) {
            logger.error("Error in boot sequence", e);
            throw new ServletException(e);
        } 
        
        Admin4JStandardFilterChain.registerFilter(this);
    
    }

}
