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
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.management.AttributeList;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.deps.commons.lang3.exception.ExceptionUtils;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.GuiUtils;

/**
 * Provides basic display capability for published JMX beans.
 * 
 * <p>You will need to map this servlet to an url pattern (e.g. '/admin4j/jmx').</p>
 * <p>This servlet does <b>not</b> require other web resources.</p>
 * @author D. Ashmore
 * @since 1.0
 */
@SuppressWarnings("serial")
public class JmxServlet extends AdminDisplayServlet {
    
    public static final String PUBLIC_HANDLE="jmx";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        Map<String, ObjectInstance> jmxBeanMap = new HashMap<String, ObjectInstance>();
        
        String selectedJmxBeanStr = request.getParameter("jmxBean");
        ObjectInstance selectedJmxBean = null;
        
        ObjectInstance jmxBean;
        for (Object obj: mbs.queryMBeans(null, null)) {
            jmxBean = (ObjectInstance)obj;
            jmxBeanMap.put(jmxBean.getObjectName().getCanonicalName(), jmxBean);
            if (jmxBean.getObjectName().getCanonicalName().equals(selectedJmxBeanStr)) {
                selectedJmxBean = jmxBean;
            }
        }
                
        String selectedOperationExecStr = request.getParameter("opExec");
        String operationInvokationResultStr = null;
        
        if (selectedOperationExecStr != null) {

            try {
                Object invokeReturn = mbs.invoke(selectedJmxBean.getObjectName(), selectedOperationExecStr, null, null);
                if (invokeReturn != null) {
                    operationInvokationResultStr = invokeReturn.toString();
                }
                else operationInvokationResultStr = "Executed successfully.";
            }
            catch (Throwable t) {
                operationInvokationResultStr = ExceptionUtils.getStackTrace(t);
            }
        }
        
        AttributeList aList = new AttributeList();
        List<MBeanOperationInfo> oList = new ArrayList<MBeanOperationInfo>();
        MBeanInfo mbInfo = null;
        
        if (selectedJmxBean != null) {
            try {
                mbInfo = mbs.getMBeanInfo(selectedJmxBean.getObjectName());
            } catch (Exception e) {
                throw new Admin4jRuntimeException(e);
            } 
                        
            try {
                aList = mbs.getAttributes(selectedJmxBean.getObjectName(), findMBeanAttr(mbs, selectedJmxBean.getObjectName()));   
                oList = Arrays.asList(mbInfo.getOperations());
            } catch (Exception e) {
                throw new Admin4jRuntimeException(e);
            } 
        }
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("jmxBeanList", new TreeSet<String>(jmxBeanMap.keySet()));
        variableMap.put("selectedJmxBeanName", selectedJmxBeanStr);
        variableMap.put("selectedOperationExecStr", selectedOperationExecStr);
        variableMap.put("mBeanAttributeList", aList);
        variableMap.put("mBeanOperationList", oList);
        variableMap.put("mBeanInfo", mbInfo);
        variableMap.put("operationInvokationResultStr", operationInvokationResultStr);
        variableMap.put("JmxUtils", new JmxUtils());
        
        this.displayFreeMarkerPage(request, response, "jmxServletDisplay.ftl", variableMap);
	}

	private String[] findMBeanAttr(MBeanServer mbs, ObjectName oName) throws Exception {
		List<String> nameList = new ArrayList<String>();
		MBeanInfo mbInfo = mbs.getMBeanInfo(oName);
		
		for (int i = 0; i < mbInfo.getAttributes().length; i++) {
			if (mbInfo.getAttributes()[i].isReadable()) {
				nameList.add(mbInfo.getAttributes()[i].getName());
			}
		}
		
		return (String[])nameList.toArray(new String[0]);
	}
	
	public static class JmxUtils {
	    public boolean isCompositeData(Object obj) {
	        return obj instanceof CompositeData;
	    }
	    
	    public boolean isTabularData(Object obj) {
            return obj instanceof TabularData;
        }
	    
	    public boolean isArray(Object obj) {
	        if (obj == null)  return false;
            return obj.getClass().isArray();
        }
	    
	    public boolean isOperationExecutable(Object obj) {
	        if (obj == null)  return false;
	        if (obj instanceof MBeanOperationInfo) {
	            MBeanOperationInfo oInfo = (MBeanOperationInfo)obj;
	            return oInfo.getSignature() == null || (oInfo.getSignature() != null && oInfo.getSignature().length == 0);
	        }
	        
	        return false;
	    }
	    
	    public String htmlEscape(Object obj) {
	        return new GuiUtils().htmlEscape(obj);
	    }

	    public List<String> compositeKeyList(Object obj) {
	        List<String> list = new ArrayList<String>();
	        
	        if (obj instanceof CompositeData) {
	            list.addAll( ((CompositeData)obj).getCompositeType().keySet());
	        }
	        
	        return list;
	    }
	    
	    public Object compositeValue(Object composite, Object key) {
	        if (composite instanceof CompositeData) {
	            return ((CompositeData)composite).get( (String)key);
	        }
	        
	        return null;
	    }
	    
	    public String formatOperationSignature(MBeanOperationInfo oInfo) {	        
	        if (oInfo == null)  return "()";
	        MBeanParameterInfo[] pInfo = oInfo.getSignature();
	        StringBuffer buffer = new StringBuffer(64);
	        buffer.append("(");
	        
	        for (int i = 0; i < pInfo.length; i++) {
	            if (i > 0) buffer.append(", ");
	            buffer.append(pInfo[i].getType());
	            buffer.append(" ");
	            buffer.append(pInfo[i].getName());
	        }
	        
	        buffer.append(")");
	        return buffer.toString();
	    }
	}

	/* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "JMX Browser";
    }
	
}
