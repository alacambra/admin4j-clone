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

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;

/**
 * Generic utilities for Notifier setup.
 * @author D. Ashmore
 *
 */
public class NotifierUtils {

    /**
     * Standard configuration for Servlet Filters.
     * @param config
     * @param notifierClassName
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    public static Notifier configure(FilterConfig config, String notifierClassName)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, ServletException {
        Notifier notifier = null;
        if ( !StringUtils.isEmpty(notifierClassName) ) {
            notifier = (Notifier)Class.forName(notifierClassName).newInstance();
            notifier.configure(config);
        }
        else if (Admin4JConfiguration.getDefaultNotifier() != null) {
            notifier = Admin4JConfiguration.getDefaultNotifier();
        }
        return notifier;
    }
    
    /**
     * Standard Notifier configuration for Servlets
     * @param config
     * @param notifierClassName
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    public static Notifier configure(ServletConfig config, String notifierClassName)
    throws InstantiationException, IllegalAccessException,
    ClassNotFoundException, ServletException {
        Notifier notifier = null;
        if ( !StringUtils.isEmpty(notifierClassName) ) {
            notifier = (Notifier)Class.forName(notifierClassName).newInstance();
            notifier.configure(config);
        }
        else if (Admin4JConfiguration.getDefaultNotifier() != null) {
            notifier = Admin4JConfiguration.getDefaultNotifier();
        }
        return notifier;
    }
    
    /**
     * Standard Notifier configuration based on properties input.
     * @param namePrefix
     * @param config
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    public static Notifier configure(String namePrefix, Properties config)
    throws InstantiationException, IllegalAccessException,
    ClassNotFoundException, ServletException {
        String notifierClassName = config.getProperty(namePrefix + ".class");
        Notifier notifier = null;
        if ( !StringUtils.isEmpty(notifierClassName) ) {
            notifier = (Notifier)Class.forName(notifierClassName).newInstance();
            notifier.configure(namePrefix, config);
        }
        else if (Admin4JConfiguration.getDefaultNotifier() != null) {
            notifier = Admin4JConfiguration.getDefaultNotifier();
        }
        return notifier;
    }

}
