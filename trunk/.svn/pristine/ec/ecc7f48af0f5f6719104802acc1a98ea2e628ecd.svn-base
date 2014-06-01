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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.util.ServletUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This filter will log Web Transactions errors.
 * <p>Init parameters for this filter are as follows:</p>
 * <li>logger -- Optional.  Default net.admin4j.ui.filters.ErrorLoggingFilter</li>
 * @author D. Ashmore
 * @since 1.0
 */
public class ErrorLoggingFilter implements Filter {
    
    private Logger logger = null;

    public void destroy() {
        // NoOp

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {chain.doFilter(request, response);}
        catch (Throwable t) {
            logger.error("Web Transaction Error", t);
            ServletUtils.reThrowServletFilterException(t);
        }

    }

    public void init(FilterConfig config) throws ServletException {
        new Admin4JConfiguration();
        String loggerName = config.getInitParameter("logger");
        if (StringUtils.isEmpty(loggerName)) {
            loggerName = Admin4JConfiguration.getWebTransactionErrorLoggerName();
        }
        
        if (StringUtils.isEmpty(loggerName)) {
            logger = LoggerFactory.getLogger(ErrorLoggingFilter.class);
        }
        else logger = LoggerFactory.getLogger(loggerName);
        
        Admin4JStandardFilterChain.registerFilter(this);

    }

    /**
     * Note:  Here for testing.
     * @return
     */
    protected Logger getLogger() {
        return logger;
    }

}
