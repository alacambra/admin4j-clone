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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.deps.commons.beanutils.BeanComparator;
import net.admin4j.deps.commons.collections.comparators.ReverseComparator;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.timer.TaskTimerFactory;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.vo.DataMeasurementSummaryVO;
import net.admin4j.vo.PerformanceSummaryVO;

/**
 * Will display collected performance statistics.
 * @author D. Ashmore
 * @since 1.0
 */
public class PerformanceDisplayServlet extends AdminDisplayServlet {

    private static final long serialVersionUID = 7019549673215008662L;
    public static final String PUBLIC_HANDLE="perf";

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String actionStr = request.getParameter("action");
        if ( !StringUtils.isEmpty(actionStr)) {
            if ("delete".equalsIgnoreCase(actionStr)) {
                TaskTimerFactory.delete(request.getParameter("perfLabel"));
            }
        }
        
        Map<String,Set<DataMeasurementSummaryVO>> summaryMap = TaskTimerFactory.getDataSummaryMap();
        TreeSet<String> labelSet = new TreeSet<String>(summaryMap.keySet());
        
        List<PerformanceSummaryVO> summaryList = new ArrayList<PerformanceSummaryVO>();
        PerformanceSummaryVO summary;
        Set<DataMeasurementSummaryVO> measureSet;
        for (String label: labelSet) {
            summary = new PerformanceSummaryVO();
            summaryList.add(summary);
            summary.setLabel(label);
            
            measureSet = summaryMap.get(label);
            for (DataMeasurementSummaryVO measure: measureSet) {
                if (DataMeasurementSummaryVO.SummaryType.SUMMARY.equals(measure.getSummaryType())) {
                    summary.setSummaryMeasurement(measure);
                }
                else if (DataMeasurementSummaryVO.SummaryType.ROLLING_TIME.equals(measure.getSummaryType())) {
                    summary.setRollingTimeMeasurement(measure);
                }
                else if (DataMeasurementSummaryVO.SummaryType.ROLLING_NBR_OBS.equals(measure.getSummaryType())) {
                    summary.setRollingNbrObservationsMeasurement(measure);
                }
            }
        }
        
        String sortField = request.getParameter("sortField"); 
        
        if ( StringUtils.isEmpty(sortField)) {
            sortField = "label";
        }
        
        if ("label".equals(sortField)) {
            Collections.sort(summaryList, new BeanComparator(sortField));
        }
        else {
            Collections.sort(summaryList, new ReverseComparator(new BeanComparator(sortField)));
        }
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("performanceSummaryList", summaryList);
        FreemarkerUtils.addConfiguration(variableMap);
        
        this.displayFreeMarkerPage(request, response, "performanceDisplayServletDisplay.ftl", variableMap);
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "Performance Statistics";
    }
    
}
