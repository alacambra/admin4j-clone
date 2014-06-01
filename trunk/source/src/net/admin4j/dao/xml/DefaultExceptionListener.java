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
package net.admin4j.dao.xml;

import java.beans.ExceptionListener;

import net.admin4j.deps.commons.lang3.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExceptionListener implements ExceptionListener {
    
    private static Logger logger = LoggerFactory.getLogger(DefaultExceptionListener.class);
    private String label;
    private boolean error = false;
    
    public DefaultExceptionListener(String label) {
        Validate.notEmpty(label, "Null or blank label not allowed.");
        this.label = label;
    }

    public void exceptionThrown(Exception e) {
        logger.error("Read error for " + label, e);
        error = true;
    }

    public boolean isError() {
        return error;
    }

}
