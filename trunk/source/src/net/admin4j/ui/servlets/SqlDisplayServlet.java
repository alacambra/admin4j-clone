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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.deps.commons.collections.comparators.ReverseComparator;
import net.admin4j.jdbc.driver.SqlStatementTimerFactory;
import net.admin4j.util.CompositeBeanComparator;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.util.GuiUtils;
import net.admin4j.vo.DataMeasurementSummaryVO;
import net.admin4j.vo.SqlStatementPerformanceSummaryVO;

/**
 * Displays the top configured number of SQL Statements along with associated information.
 * @author D. Ashmore
 *
 */
public class SqlDisplayServlet extends AdminDisplayServlet {

    private static final long serialVersionUID = -912400494533151009L;
    public static final String PUBLIC_HANDLE="sql";

    @Override
    public String getServletLabel() {
        return "SQL Display Summary";
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String,Set<DataMeasurementSummaryVO>> summaryMap = SqlStatementTimerFactory.getDataSummaryMap();
        List<SqlStatementPerformanceSummaryVO> summaryList = new ArrayList<SqlStatementPerformanceSummaryVO>();
        
        for (Set<DataMeasurementSummaryVO> summarySet: summaryMap.values()) {
            summaryList.add(new SqlStatementPerformanceSummaryVO(takeOne(summarySet)));
        }
        
        CompositeBeanComparator comparator = new CompositeBeanComparator(
                new String[]{"summary.total", "poolName"});
        Collections.sort(summaryList, new ReverseComparator(comparator));
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("sqlSummaryList", summaryList);
        variableMap.put("GuiUtils", new GuiUtils());
        FreemarkerUtils.addConfiguration(variableMap);
        
        this.displayFreeMarkerPage(request, response, "sqlServletDisplay.ftl", variableMap);

    }
    
    private DataMeasurementSummaryVO takeOne(Set<DataMeasurementSummaryVO> set) {
        ArrayList<DataMeasurementSummaryVO> list = new ArrayList<DataMeasurementSummaryVO>(set);
        return list.get(0);
    }

}
