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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.FixedSizeRollingList;
import net.admin4j.util.ServletUtils;
import net.admin4j.vo.HttpRequestVO;

/**
 * Filter that will track requests in a session for possible error reporting. 
 * @author D. Ashmore
 * @since 1.0.2
 */
public class RequestTrackingFilter implements Filter {
    
    public static final int DEFAULT_NBR_REQUESTS_TRACKED = 5;
    public static final String REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME = "admin4j.previous.requests";
    
    private int nbrRequestsTracked;

    public void destroy() {
        // NoOp

    }

    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        
        FixedSizeRollingList<HttpRequestVO> list = (FixedSizeRollingList<HttpRequestVO>)
                httpRequest.getSession().getAttribute(REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME);
        if (list == null) {
            list = new FixedSizeRollingList<HttpRequestVO>(nbrRequestsTracked);
            httpRequest.getSession().setAttribute(REQUEST_TRACKING_SESSION_ATTRIBUTE_NAME, list);
        }
        
        list.add(new HttpRequestVO(httpRequest.getRequestURI(), 
                ServletUtils.listRequestAttributes(httpRequest)));
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        String nbrRequestsStr = config.getInitParameter("nbr.requests.tracked");
        
        if (Admin4JConfiguration.getRequestHistoryNbrRetained() != null) {
            if (Admin4JConfiguration.getRequestHistoryNbrRetained() > 0) {
                nbrRequestsTracked = Admin4JConfiguration.getRequestHistoryNbrRetained();
            }
            else {
                throw new Admin4jRuntimeException("request.history.nbr.retained must be greater than zero")
                .addContextValue("request.history.nbr.retained", Admin4JConfiguration.getRequestHistoryNbrRetained());
            }
        }
        else if (StringUtils.isEmpty(nbrRequestsStr)) {
            nbrRequestsTracked = DEFAULT_NBR_REQUESTS_TRACKED;
        }
        else {
            try {
                nbrRequestsTracked = Integer.valueOf(nbrRequestsStr);
            } catch (NumberFormatException e) {
                throw new Admin4jRuntimeException("request.history.nbr.retained must be numeric")
                .addContextValue("request.history.nbr.retained", nbrRequestsStr);
            }
        }
        Admin4JStandardFilterChain.registerFilter(this);

    }

}
