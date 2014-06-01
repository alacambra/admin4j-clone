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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.HostUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for email notifiers.
 * <p>Initialization parameters for this notifier are as follows:</p>
 * <li>mail.server.host -- Required.  For example 'mail.admin4j.org'</li>
 * <li>from.email.address -- Required.  For example 'appName@admin4j.org'</li>
 * <li>to.email.address -- Required.  For example 'App_Support@admin4j.org'</li>
 * <li>allowed.servers -- Optional.  Comma delimited list of servers that can send email.  
 * For example 'server1, server2'</li>
 * @author D. Ashmore
 * @since 1.0
 *
 */
public abstract class BaseEmailNotifier implements Notifier {

    private String mailServerHost;
    private String fromEmailAddress;
    private String toEmailAddress;
    private Set<String> allowedServerSet = new HashSet<String>();
    protected Logger logger = LoggerFactory.getLogger(HtmlEmailNotifier.class);
    private boolean suppressEmail = false;

    public boolean supportsHtml() {
        return false;
    }

    public void configure(FilterConfig config) throws ServletException {
        this.mailServerHost = config.getInitParameter("mail.server.host");
    	if (this.mailServerHost ==null)  throw new ServletException("Missing mail.server.host init parameter");
    	
    	this.fromEmailAddress = config.getInitParameter("from.email.address");
    	if (this.fromEmailAddress ==null)  throw new ServletException("Missing from.email.address init parameter");
    	
    	this.toEmailAddress = config.getInitParameter("to.email.address");
    	if (this.toEmailAddress ==null)  throw new ServletException("Missing to.email.address init parameter");
    	
    	boolean allowAllServers = true;
    	String allowedServerStr = config.getInitParameter("allowed.servers");
    	if (allowedServerStr != null) {
    		allowAllServers = false;
    		StringTokenizer tokenizer = new StringTokenizer(allowedServerStr, ",");
    		while (tokenizer.hasMoreTokens()) {
    			this.allowedServerSet.add(HostUtils.deriveServerName(tokenizer.nextToken()));
    		}
    	}
    	
    	
    	
    	if (!allowAllServers && !this.allowedServerSet.contains(HostUtils.getHostName())) {
    		logger.info("Host not in allowed server set. host=" +HostUtils.getHostName());
    		this.suppressEmail = true;
    	}
    	else logger.info("Emails will be provided by host=" +HostUtils.getHostName());
    }

    public void configure(ServletConfig config) throws ServletException {
        this.mailServerHost = config.getInitParameter("mail.server.host");
    	if (this.mailServerHost ==null)  throw new ServletException("Missing mail.server.host init parameter");
    	
    	this.fromEmailAddress = config.getInitParameter("from.email.address");
    	if (this.fromEmailAddress ==null)  throw new ServletException("Missing from.email.address init parameter");
    	
    	this.toEmailAddress = config.getInitParameter("to.email.address");
    	if (this.toEmailAddress ==null)  throw new ServletException("Missing to.email.address init parameter");
    	
    	boolean allowAllServers = true;
    	String allowedServerStr = config.getInitParameter("allowed.servers");
    	if (allowedServerStr != null) {
    		allowAllServers = false;
    		StringTokenizer tokenizer = new StringTokenizer(allowedServerStr, ",");
    		while (tokenizer.hasMoreTokens()) {
    			this.allowedServerSet.add(HostUtils.deriveServerName(tokenizer.nextToken()));
    		}
    	}
    	
    	if (!allowAllServers && !this.allowedServerSet.contains(HostUtils.getHostName())) {
    		this.suppressEmail = true;
    	}
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.util.notify.Notifier#configure(java.lang.String, java.util.Properties)
     */
    public void configure(String namePrefix, Properties config) {
        this.mailServerHost = config.getProperty(namePrefix + ".mail.server.host");
        if (this.mailServerHost ==null)  {
            throw new Admin4jRuntimeException("Missing property")
                .addContextValue("property name", namePrefix + ".mail.server.host");
        }
        
        this.fromEmailAddress = config.getProperty(namePrefix + ".from.email.address");
        if (this.fromEmailAddress ==null)  {
            throw new Admin4jRuntimeException("Missing property")
            .addContextValue("property name", namePrefix + ".from.email.address");
        }
        
        this.toEmailAddress = config.getProperty(namePrefix + ".to.email.address");
        if (this.toEmailAddress ==null)  {
            throw new Admin4jRuntimeException("Missing property")
            .addContextValue("property name", namePrefix + ".to.email.address");
        }
        
        boolean allowAllServers = true;
        String allowedServerStr = config.getProperty(namePrefix + ".allowed.servers");
        if (allowedServerStr != null) {
            allowAllServers = false;
            StringTokenizer tokenizer = new StringTokenizer(allowedServerStr, ",");
            while (tokenizer.hasMoreTokens()) {
                this.allowedServerSet.add(HostUtils.deriveServerName(tokenizer.nextToken()));
            }
        }
        
        if (!allowAllServers && !this.allowedServerSet.contains(HostUtils.getHostName())) {
            this.suppressEmail = true;
        }
        
    }

    public Set<String> getAllowedServerSet() {
    	return allowedServerSet;
    }

    public void setAllowedServerSet(Set<String> allowedServerSet) {
    	this.allowedServerSet = allowedServerSet;
    }

    public String getFromEmailAddress() {
    	return fromEmailAddress;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
    	this.fromEmailAddress = fromEmailAddress;
    }

    public String getMailServerHost() {
    	return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
    	this.mailServerHost = mailServerHost;
    }

    public boolean isSuppressEmail() {
    	return suppressEmail;
    }

    public void setSuppressEmail(boolean suppressEmail) {
    	this.suppressEmail = suppressEmail;
    }

    public String getToEmailAddress() {
    	return toEmailAddress;
    }

    public void setToEmailAddress(String toEmailAddress) {
    	this.toEmailAddress = toEmailAddress;
    }

    /* (non-Javadoc)
     * @see net.admin4j.util.notify.Notifier#supportsSMS()
     */
    public boolean supportsSMS() {
        return false;
    }

}
