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


import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.admin4j.deps.commons.mail.HtmlEmail;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.HostUtils;

/**
 * Will provide an email notification in HTML format.
 * <p> See BaseEmailNotifier for initialization parameters.</p>
 * <p>Note: If the logger for this class is DEBUG, the debug setting will
 * be passed onto the java mail api.</p>
 * @author D. Ashmore
 * @since 1.0
 * @see BaseEmailNotifier
 */
public class HtmlEmailNotifier extends BaseEmailNotifier {
	
	public HtmlEmailNotifier()  {}
	public HtmlEmailNotifier(FilterConfig config) throws ServletException {
		configure(config);
	}
	
    public HtmlEmailNotifier(ServletConfig config) throws ServletException {
		configure(config);
	}
	
    public void notify(String subject, String message) {
		try {
			
		    if (this.isSuppressEmail()) {
		        logger.debug("Email Notification surpressed: {}", message);
		        return;
		    }
			HtmlEmail email = new HtmlEmail();
			email.setHostName(this.getMailServerHost());

			if (message != null && message.indexOf("html") > 0) {
			    email.setHtmlMsg(message);
			}
			else email.setHtmlMsg("<html><body>" + message + "</body></html>");

		//  Will invoke logging on javamail if true.
            email.setDebug(logger.isDebugEnabled()); 
            
			email.setSubject(subject);
			email.setFrom(this.getFromEmailAddress());
			email.addTo(this.getToEmailAddress(), HostUtils.getHostName());
			email.send();
		} catch (Exception e) {
			throw new Admin4jRuntimeException(e);
		}
	}
	
	public boolean supportsHtml() {
	    return true;
	}
    /* (non-Javadoc)
     * @see net.admin4j.util.notify.Notifier#supportsSMS()
     */
    public boolean supportsSMS() {
        return false;
    }

}
