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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.deps.commons.lang3.Validate;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * Generic Freemarker utilities
 * @author D. Ashmore
 *
 */
public class FreemarkerUtils {

    @SuppressWarnings("rawtypes")
    public static Template createConfiguredTemplate(Class templateLoadingClass, String templateName)
            throws IOException {
        Validate.notNull(templateLoadingClass, "Null templateLoadingClass not allowed.");
        Validate.notEmpty(templateName, "Null or blank templateName not allowed.");
        Configuration cfg = new Configuration();
        if (Admin4JConfiguration.getBaseFreemarkerTemplateDirectory() == null) {
            cfg.setClassForTemplateLoading(templateLoadingClass, "");
        }
        else {
            StringBuffer dirName = new StringBuffer(Admin4JConfiguration.getBaseFreemarkerTemplateDirectory().getAbsolutePath());
            if ( !dirName.toString().endsWith(SystemUtils.FILE_SEPARATOR) ) {
                dirName.append(SystemUtils.FILE_SEPARATOR);
            }
            dirName.append(templateLoadingClass.getPackage().getName().replace('.', SystemUtils.FILE_SEPARATOR.charAt(0)));
            cfg.setDirectoryForTemplateLoading( new File(dirName.toString()));
        }
        
        DefaultObjectWrapper  myObjWrapper = new  DefaultObjectWrapper();
        myObjWrapper.setNullModel(TemplateScalarModel.EMPTY_STRING);
        cfg.setObjectWrapper(myObjWrapper);
                
        Template temp = cfg.getTemplate(templateName);
        return temp;
    }

    public static void addConfiguration(Map<String, Object> variableMap) {
        try {
            variableMap.put("configuration", BeansWrapper.getDefaultInstance().getStaticModels().get("net.admin4j.config.Admin4JConfiguration"));
        } catch (TemplateModelException e) {
            throw new Admin4jRuntimeException(e);
        }
    }

}
