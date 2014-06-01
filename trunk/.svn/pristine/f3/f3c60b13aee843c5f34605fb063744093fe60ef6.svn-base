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
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Standard filter chain that implements Admin4J filters with a standard configuration 
 * usable by most.
 * @author D. Ashmore
 *
 */
public class Admin4JStandardFilterChain implements FilterChain {
    
    private static Map<String, Filter> filterMap = new ConcurrentHashMap<String, Filter>();
    private static ThreadLocal<Stack<Filter>> localFilterStack = new ThreadLocal<Stack<Filter>>();
    private static Logger logger = LoggerFactory.getLogger(Admin4JStandardFilterChain.class);
    private FilterChain delegateChain;
    
    public Admin4JStandardFilterChain(List<String> filterKey, FilterChain delegateChain) {
        Validate.notNull(filterKey, "Null filterKey not allowed.");
        Validate.notNull(delegateChain, "Null filterKey not allowed.");
        
        this.delegateChain = delegateChain;
        
        Stack<Filter> localStack = new Stack<Filter>();
        Filter localFilter;
        
        for (int i = filterKey.size() - 1; i>=0; i--) {
            if ( !StringUtils.isEmpty(filterKey.get(i))) {
                localFilter = filterMap.get(filterKey.get(i));
                if (localFilter == null) {
                    throw new Admin4jRuntimeException("Filter not recognized")
                        .addContextValue("filterKey", filterKey.get(i));
                }
                localStack.push(localFilter);
            }
        }
        
        localFilterStack.set(localStack);
    }

    public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        Filter localFilter = null;
        if (localFilterStack.get().size() > 0) {
            localFilter = localFilterStack.get().pop();
        }
        
        if (localFilter != null) {
            localFilter.doFilter(request, response, this);
        }
        else this.delegateChain.doFilter(request, response);

    }
    
    public static void registerFilter(Filter filter) {
        registerFilter(filter, false);
    }
    
    public static void registerFilter(Filter filter, boolean avoidDuplicateRegistration) {
        Validate.notNull(filter, "Null filter not allowed.");
        
        synchronized(filterMap) {
            if (avoidDuplicateRegistration && !filterMap.containsKey(filter.getClass().getName())) {                
                filterMap.put(filter.getClass().getName(), filter);
                logger.info("Admin4J Filter registered: {}", filter.getClass().getName());
            }
            else if ( !avoidDuplicateRegistration) {
                filterMap.put(filter.getClass().getName(), filter);
                logger.info("Admin4J Filter registered: {}", filter.getClass().getName());
            }
        }
    }
    
    public static boolean isRegistered(String filterClassName) {
        Validate.notEmpty(filterClassName, "Null or blank filterClassName not allowed.");
        return filterMap.containsKey(filterClassName);
    }
    
    /**
     * This only exists to support unit testing.
     */
    protected static void clearFilterMap() {
        filterMap.clear();
    }
    
    /**
     * This only exists to support unit testing
     * @param filterClassName
     * @return
     */
    protected static Filter getRegisteredFilter(String filterClassName) {
        return filterMap.get(filterClassName);
    }

}
