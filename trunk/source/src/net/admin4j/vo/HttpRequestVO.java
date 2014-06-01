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

import java.util.List;

import net.admin4j.util.NameValuePair;

/**
 * Summarizes a Http Request
 * @author D. Ashmore
 * @since 1.0.2
 */
public class HttpRequestVO extends BaseVO {

    private static final long serialVersionUID = -6620168446486224800L;
    private String uri;
    private List<NameValuePair> requestParameters;
    
    public HttpRequestVO(String uri, List<NameValuePair> requestParameters) {
        this.uri = uri;
        this.requestParameters = requestParameters;
    }

    public String getUri() {
        return uri;
    }

    public List<NameValuePair> getRequestParameters() {
        return requestParameters;
    }
}
