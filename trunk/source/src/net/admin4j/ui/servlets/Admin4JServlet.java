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
package net.admin4j.ui.servlets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.admin4j.config.Admin4JConfiguration;

/**
 * Base servlet for all Admin4J Servlets.  
 * @author D. Ashmore
 *
 */
abstract class Admin4JServlet extends HttpServlet {

    private static final long serialVersionUID = -1960693935838064409L;
    
    @SuppressWarnings("rawtypes")
    private static Map<Class, Set<HttpServlet>> registryMap = new HashMap<Class, Set<HttpServlet>>();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init() throws ServletException {
        super.init();
        logger.debug("Initializing Servlet {}", this.getClass().getName());
        new Admin4JConfiguration();
        
        synchronized(registryMap) {
            Set<HttpServlet> registrySet = registryMap.get(this.getClass());
            if (registrySet == null) {
                registrySet = new HashSet<HttpServlet>();
                registryMap.put(this.getClass(), registrySet);
            }
            registrySet.add(this);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.init();
    }
    
    public static Set<HttpServlet> getRegisteredServletSet(@SuppressWarnings("rawtypes") Class klass) {
        Set<HttpServlet> registrySet = new HashSet<HttpServlet>();
        synchronized(registryMap) {
            if (registryMap.containsKey(klass)) {
                registrySet.addAll(registryMap.get(klass));
            }
        }
        
        return registrySet;
    }
    
    public abstract boolean hasDisplay();



}
