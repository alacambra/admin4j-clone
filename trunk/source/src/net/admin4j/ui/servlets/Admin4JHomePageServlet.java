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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.deps.commons.collections.map.ListOrderedMap;
import net.admin4j.deps.commons.lang3.ArrayUtils;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * Index page display for all Admin4J Servlets.
 * @author D. Ashmore
 *
 */
public class Admin4JHomePageServlet extends AdminDisplayServlet {
    
    private static final long serialVersionUID = -249983381219640941L;
    @SuppressWarnings("rawtypes")
    private static Map<Class, String> requestClassMapping = new ConcurrentHashMap<Class, String>();
    private static Set<String> publicHandleSet = new HashSet<String>();
    @SuppressWarnings("unchecked")
    private static Map<String, HttpServlet> urlMap = new ListOrderedMap();
    
    private ServletConfig servletConfig;
    private List<Admin4JServlet> servletList = new ArrayList<Admin4JServlet>();
    
    static {
        
        requestClassMapping.put(ExceptionDisplayServlet.class, ExceptionDisplayServlet.PUBLIC_HANDLE);
        requestClassMapping.put(HotSpotDisplayServlet.class, HotSpotDisplayServlet.PUBLIC_HANDLE);
        requestClassMapping.put(PerformanceDisplayServlet.class, PerformanceDisplayServlet.PUBLIC_HANDLE);
        requestClassMapping.put(SqlDisplayServlet.class, SqlDisplayServlet.PUBLIC_HANDLE);
        
        requestClassMapping.put(LogLevelServlet.class, LogLevelServlet.PUBLIC_HANDLE);
        requestClassMapping.put(FileExplorerServlet.class, FileExplorerServlet.PUBLIC_HANDLE);
        requestClassMapping.put(JmxServlet.class, JmxServlet.PUBLIC_HANDLE);
        
        publicHandleSet.addAll(requestClassMapping.values());
               
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (urlMap.size() == 0) {           
            registerServletMappings(request);
        }
        
        HttpServlet delegateServlet = urlMap.get(request.getRequestURI());
        if (delegateServlet != null) {
            delegateServlet.service(request, response);
            return;
        }
        
        request.getSession().setAttribute(ADMIN4J_SESSION_VARIABLE_PREFIX + "IndexUri", request.getRequestURI());
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("servletList", urlMap.entrySet());

        displayFreeMarkerPage(request, response, "admin4jIndexDisplayServlet.ftl", variableMap);
    }
    
    private void registerServletMappings(HttpServletRequest request) throws ServletException {
        

        String baseURI = determineBaseUri(request);        
        StringBuffer base = new StringBuffer(baseURI);
        if ( !baseURI.endsWith("/")) {
            base.append("/");
        }
       
        for (Admin4JServlet servlet: this.servletList) {
            urlMap.put(base + requestClassMapping.get(servlet.getClass()), servlet);
        }

    }

    protected String determineBaseUri(HttpServletRequest request) {
        String baseURI;
        String[] nodes = StringUtils.split(request.getRequestURI(), "/");
        int publicHandleOffset = findPublicHandleOffset(nodes);
        if (publicHandleOffset > 0) {
            baseURI = "/" + StringUtils.join(ArrayUtils.subarray(nodes, 0, publicHandleOffset), "/");
        }
        else {
            baseURI = request.getRequestURI();
        }
        return baseURI;
    }
    
    private int findPublicHandleOffset(String[] nodes) {
        for (int i = nodes.length - 1; i >= 0; i--) {
            if (publicHandleSet.contains(nodes[i])) {
                return i;
            }
        }
        
        return -1;
    }

    @SuppressWarnings("rawtypes")
    private Admin4JServlet initServlet(Class servletClass)
            throws ServletException {
        Admin4JServlet tempServlet;
        try {
            tempServlet = (Admin4JServlet)servletClass.newInstance();
        } catch (Throwable e) {
            throw new Admin4jRuntimeException(e);
        } 
        tempServlet.init(this.servletConfig);
        this.servletList.add(tempServlet);
        return tempServlet;
    }
    
    @SuppressWarnings("rawtypes")
    private void ensureInitializedServlet(Class servletClass) throws ServletException {
        Set<HttpServlet> servletSet;
        Admin4JServlet tempServlet;
        
        servletSet = getRegisteredServletSet(servletClass);
        if (servletSet.size() == 0) {
            try {
                tempServlet = (Admin4JServlet)servletClass.newInstance();
            } catch (Throwable e) {
                throw new Admin4jRuntimeException(e);
            } 
            tempServlet.init(this.servletConfig);
        }

    }
    
    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "Admin4J Index Page";
    }

    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        this.servletConfig = config;
        
        initServlet(HotSpotDisplayServlet.class);
        initServlet(ExceptionDisplayServlet.class);
        initServlet(FileExplorerServlet.class);
        initServlet(SqlDisplayServlet.class);
        initServlet(JmxServlet.class);
        initServlet(LogLevelServlet.class);
        initServlet(PerformanceDisplayServlet.class);
        
        ensureInitializedServlet(MemoryMonitorStartupServlet.class);
        ensureInitializedServlet(ThreadMonitorStartupServlet.class);

    }

}
