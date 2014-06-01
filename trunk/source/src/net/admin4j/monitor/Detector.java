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
package net.admin4j.monitor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.FreemarkerUtils;
import net.admin4j.util.notify.Notifier;
import freemarker.template.Template;

/**
 * Base class for all Detectors.
 * @author D. Ashmore
 * @since 1.0
 */
public abstract class Detector implements Runnable {
    
    protected Notifier notifier;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public Detector(Notifier emailNotifier) {
        Validate.notNull(emailNotifier, "Null emailNotifier not allowed");
        this.notifier = emailNotifier;
    }

    protected Notifier getNotifier() {
    	return notifier;
    }

    protected void sendMessage(String subject, String templateName, Map<String, Object> variableMap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Template temp = FreemarkerUtils.createConfiguredTemplate(this.getClass(), templateName);
            temp.process(variableMap, new OutputStreamWriter(outputStream));
        }
        catch (Exception e) {
            throw new Admin4jRuntimeException(e);
        }
        
        this.getNotifier().notify(subject, outputStream.toString());
    }

}
