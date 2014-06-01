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
package net.admin4j.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Logging product and all available loggers within.
 * @author D. Ashmore
 *
 */
public class LoggerProductVO extends BaseVO {
    
    private static final long serialVersionUID = -919957119837616337L;
    private String loggingProductName;
    private List<LoggerVO> loggerList = new ArrayList<LoggerVO>();
    
    public LoggerProductVO()  {}
    public LoggerProductVO(String loggingProductName, List<LoggerVO> loggerList)  {
        this.setLoggingProductName(loggingProductName);
        this.getLoggerList().addAll(loggerList);
    }
    
    public String getLoggingProductName() {
        return loggingProductName;
    }
    
    public void setLoggingProductName(String loggingProductName) {
        this.loggingProductName = loggingProductName;
    }
    public List<LoggerVO> getLoggerList() {
        return loggerList;
    }

}
