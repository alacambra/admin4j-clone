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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.beanutils.BeanComparator;
import net.admin4j.deps.commons.collections.comparators.ComparatorChain;
import net.admin4j.deps.commons.collections.comparators.ReverseComparator;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.entity.ExecutionPoint;
import net.admin4j.hotspot.ExecutionTracker;
import net.admin4j.hotspot.HotSpotUtils;
import net.admin4j.util.ServletUtils;

/**
 * Will display execution statistics and allow users to see programatic hot spots
 * in real time.
 * 
 * <p>Init parameters for this servlet are as follows:</p>
 * <li>sleep.interval.millis -- Optional.  Default 30000 (30 sec).  Amount of time in millis monitor will sleep between checks.</li>
 * @author D. Ashmore
 * @since 1.0
 */
public class HotSpotDisplayServlet extends AdminDisplayServlet {

    private static final String DISPLAY_OPTION_TOP_50 = "Top 50";
    private static final String DISPLAY_OPTION_TOP_25 = "Top 25";
    private static final String DISPLAY_OPTION_TOP_10 = "Top 10";
    private static final String DISPLAY_OPTION_ALL = "All";
    private static final long serialVersionUID = 2757696314660986019L;
    
    public static final Integer DEFAULT_SLEEP_INTERGER_MILLIS=30000;
    private Integer sleepIntervalInMillis = DEFAULT_SLEEP_INTERGER_MILLIS;
    
    public static final String PUBLIC_HANDLE="hotSpot";

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        // Take the sleep interval meant specifically for HotSpot first.
        String sleepIntervalMillisStr = ServletUtils.getConfigurationSetting(
                new String[]{PUBLIC_HANDLE + ".sleep.interval.millis", 
                        "sleep.interval.millis"}, config);

        if ( !StringUtils.isEmpty(sleepIntervalMillisStr) ) {
            try {
                sleepIntervalInMillis = Integer.parseInt(sleepIntervalMillisStr);
            } catch (NumberFormatException e) {
                this.logger.error("Invalid Sleep Interval provided.  Default used.  value="+sleepIntervalMillisStr, e);
                sleepIntervalInMillis = DEFAULT_SLEEP_INTERGER_MILLIS;
            }

        }
        else if ( Admin4JConfiguration.getHotSpotSleepIntervalMillis() != null ) {
            sleepIntervalInMillis = Admin4JConfiguration.getHotSpotSleepIntervalMillis();
        }
        
        ExecutionTracker.startTracking(sleepIntervalInMillis);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if ( !StringUtils.isEmpty(request.getParameter("Reset"))) {
            ExecutionTracker.reset();
        }
        
        String selectedDisplayOption = DISPLAY_OPTION_ALL;
        if ( !StringUtils.isEmpty(request.getParameter("displayOption"))) {
            selectedDisplayOption = request.getParameter("displayOption");
        }
                
        Map<StackTraceElement, ExecutionPoint> fullExecutionMap = ExecutionTracker.getExecutionMap();
        Map<StackTraceElement, ExecutionPoint> executionMap = ExecutionTracker.getHotSpotMap();
        Map<StackTraceElement, ExecutionPoint> blockedExecutionMap = ExecutionTracker.getBlockedExecutionMap();
        
        displayHotSpotPage(request, response, selectedDisplayOption,
                fullExecutionMap, executionMap, blockedExecutionMap);
    }

    protected void displayHotSpotPage(HttpServletRequest request,
            HttpServletResponse response, String selectedDisplayOption,
            Map<StackTraceElement, ExecutionPoint> fullExecutionMap,
            Map<StackTraceElement, ExecutionPoint> executionMap,
            Map<StackTraceElement, ExecutionPoint> blockedExecutionMap)
            throws IOException {
        ExecutionPoint displayedExecutionPoint = null;
        if ( !StringUtils.isEmpty(request.getParameter("DisplayClass"))) {
            StringTokenizer epStrTok = new StringTokenizer(request.getParameter("DisplayClass"), ":");
            String className = epStrTok.nextToken();
            String methodName = epStrTok.nextToken();
            String fileName = epStrTok.nextToken();
            
            displayedExecutionPoint = fullExecutionMap.get(new StackTraceElement(className, methodName, fileName, 0));
        }
        
        List<String> displayOptionList = new ArrayList<String>();

        displayOptionList.add(DISPLAY_OPTION_ALL);
        displayOptionList.add(DISPLAY_OPTION_TOP_10);
        displayOptionList.add(DISPLAY_OPTION_TOP_25);
        displayOptionList.add(DISPLAY_OPTION_TOP_50);
        
        List<ExecutionPoint> pointList = new ArrayList<ExecutionPoint>();
        pointList.addAll(executionMap.values());
        ComparatorChain comparator = new ComparatorChain();
        comparator.addComparator(new ReverseComparator(new BeanComparator("nbrExecutions")));
        comparator.addComparator(new BeanComparator("stackTraceElement.className"));
        comparator.addComparator(new BeanComparator("stackTraceElement.methodName"));
        comparator.addComparator(new BeanComparator("stackTraceElement.lineNumber"));
        Collections.sort(pointList, comparator);
        
        if ( !selectedDisplayOption.equals(DISPLAY_OPTION_ALL)) {
            int top = 50;
            if (selectedDisplayOption.equals(DISPLAY_OPTION_TOP_25)) {
                top = 25;
            }
            if (selectedDisplayOption.equals(DISPLAY_OPTION_TOP_10)) {
                top = 10;
            }
            
            while (pointList.size() > top) {
                pointList.remove(top);
            }
        }
        
        List<ExecutionPoint> blockedPointList = new ArrayList<ExecutionPoint>();
        blockedPointList.addAll(blockedExecutionMap.values());
        comparator = new ComparatorChain();
        comparator.addComparator(new ReverseComparator(new BeanComparator("nbrBlockedExecutions")));
        comparator.addComparator(new BeanComparator("stackTraceElement.className"));
        comparator.addComparator(new BeanComparator("stackTraceElement.methodName"));
        comparator.addComparator(new BeanComparator("stackTraceElement.lineNumber"));
        Collections.sort(blockedPointList, comparator);
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("displayOptionList", displayOptionList);
        variableMap.put("executionPointList", pointList);
        variableMap.put("blockedExecutionPointList", blockedPointList);
        if (displayedExecutionPoint != null) {
            variableMap.put("displayedExecutionPointList", Arrays.asList(displayedExecutionPoint));
        }
        variableMap.put("selectedDisplayOption", selectedDisplayOption);
        variableMap.put("hotSpotUtils", new HotSpotUtils(fullExecutionMap));
        
        displayFreeMarkerPage(request, response, "hotSpotServletDisplay.ftl", variableMap);
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "HotSpot Display";
    }

    /**
     * Just here to support unit testing.
     * @return
     */
    protected Integer getSleepIntervalInMillis() {
        return sleepIntervalInMillis;
    }
    
}
