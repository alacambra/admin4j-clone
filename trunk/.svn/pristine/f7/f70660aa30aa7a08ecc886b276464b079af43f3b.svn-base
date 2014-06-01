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
package net.admin4j.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.admin4j.deps.commons.lang3.StringUtils;

/**
 * Generic Servlet utilities
 * @author D. Ashmore
 *
 */
public class ServletUtils {

    /**
     * Will rethrow a caught Servlet Exception
     * @param t
     * @throws ServletException
     * @throws IOException
     */
    public static void reThrowServletFilterException(Throwable t) throws ServletException,
            IOException {
        if (t instanceof ServletException) {
            throw (ServletException)t;
        }
        if (t instanceof IOException) {
            throw (IOException) t;
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        throw new ServletException(t);
    }

    /**
     * Will format session attributes as NavmeValuePairs.
     * @param session
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<NameValuePair> listSessionAttributes(HttpSession session) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
    	Enumeration attrNames = session.getAttributeNames();
    	String name;
        Object value;
    	
    	while (attrNames.hasMoreElements()) {
    		name = (String)attrNames.nextElement();
    		value = session.getAttribute(name);
    		
    		list.add(new NameValuePair(name, value.toString()));
    	}
    	
    	return list;
    }

    /**
     * Will format request attributes as NameValuePairs.
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<NameValuePair> listRequestAttributes(HttpServletRequest request) {
    
    	List<NameValuePair> list = new ArrayList<NameValuePair>();
    	Enumeration parmNames = request.getParameterNames();
    	String name;
    	Object value;
    
    	while (parmNames.hasMoreElements()) {
    		
    		name = (String)parmNames.nextElement();
    		if ("javax.faces.ViewState".equals(name))
    		{
    			value = "[suppressed]";
    		}
    		else
    		{
    			value = request.getParameter(name);
    		}
    		
    		list.add(new NameValuePair(name, value.toString()));
    	}
    	
    	return list;
    }

    /**
     * Will parse the request and return the name of the current web application.
     * @param request
     * @return
     */
    public static String getApplicationName(HttpServletRequest request) {
    	return request.getRequestURI().substring(1, request.getRequestURI().indexOf("/", 1));
    }
    
    /**
     * Will return the configuration setting requested *in the order of the keys provided*.  The
     * first key to retrieve a non-empty value will be the value returned.
     * @param settingKeysInPrecedenceOrder
     * @param config
     * @return
     */
    public static String getConfigurationSetting(String[] settingKeysInPrecedenceOrder, ServletConfig config) {
        String value = null;
        for (String key: settingKeysInPrecedenceOrder) {
            value = config.getInitParameter(key);
            if (!StringUtils.isEmpty(value)) {
                return value;
            }
        }
        
        return value;
    }

}
