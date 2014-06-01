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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.admin4j.deps.commons.lang3.StringUtils;

/**
 * Standard filter that conveniently invokes all other Admin4J filters in a standard configuration.
 * this exists to make Admin4J easier to configure.
 * 
 * <p>Init parameters for this filter are as follows:</p>
 * <li>include.serverside.timestamp -- Optional.  Default false.  If true, includes a server-side timestamp in all returned html.
 *    Useful if debugging possible performance issues reported only by people in some remote places of the world.</li>
 * @author D. Ashmore
 *
 */
public class Admin4JStandardFilter extends BaseFilter {
    
    private List<String> filterKeyList;
    
    public void destroy() {
        // No Op

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        Admin4JStandardFilterChain localChain = new Admin4JStandardFilterChain(this.findfilterKeyList(), chain);
        localChain.doFilter(request, response);
    }
    
    private synchronized List<String> findfilterKeyList() {
        if (filterKeyList != null) {
            return filterKeyList;
        }
        
        List<String> list = new ArrayList<String>();
        if (Admin4JStandardFilterChain.isRegistered(PerformanceMonitoringFilter.class.getName())) {
            list.add(PerformanceMonitoringFilter.class.getName());
        }
        if (Admin4JStandardFilterChain.isRegistered(PerformanceTimeStampingFilter.class.getName())) {
            list.add(PerformanceTimeStampingFilter.class.getName());
        }
        if (Admin4JStandardFilterChain.isRegistered(ConcurrentUsageFilter.class.getName())) {
            list.add(ConcurrentUsageFilter.class.getName());
        }
        if (Admin4JStandardFilterChain.isRegistered(RequestTrackingFilter.class.getName())) {
            list.add(RequestTrackingFilter.class.getName());
        }
        
        if (Admin4JStandardFilterChain.isRegistered(ErrorNotificationFilter.class.getName())) {
            list.add(ErrorNotificationFilter.class.getName());
        }
        else {
            list.add(ErrorLoggingFilter.class.getName());
        }
        
        filterKeyList = list;
        return list;
    }

    public void init(FilterConfig config) throws ServletException {
        this.configureFilter(new ErrorLoggingFilter(), config);
        this.configureFilter(new ErrorNotificationFilter(), config);
        this.configureFilter(new PerformanceMonitoringFilter(), config);
        this.configureFilter(new ConcurrentUsageFilter(), config);
        this.configureFilter(new RequestTrackingFilter(), config);
        
        String includeTimestampingStr = config.getInitParameter("include.serverside.timestamp");
        if ( !StringUtils.isEmpty(includeTimestampingStr) && "true".equalsIgnoreCase(includeTimestampingStr)) {
            this.configureFilter(new PerformanceTimeStampingFilter(), config);
        }
                
    }
    
    private void configureFilter(Filter filter, FilterConfig config) {
        if (Admin4JStandardFilterChain.isRegistered(filter.getClass().getName())) {
            return;
        }
        
        try {
            filter.init(config);
            Admin4JStandardFilterChain.registerFilter(filter, true);
        }
        catch (Throwable t) {
            logger.warn("Error configuring Filter", t);
        }
    }

}
