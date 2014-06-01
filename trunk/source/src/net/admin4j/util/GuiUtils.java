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

import java.text.DecimalFormat;
import java.util.Date;

import net.admin4j.deps.commons.lang3.StringEscapeUtils;
import net.admin4j.deps.commons.lang3.StringUtils;

/**
 * Generic utilities needed for generating page displays.
 * @author D. Ashmore
 * @since 1.0
 */
public class GuiUtils {
    
    public static String htmlEscape(Object obj) {
        String str = null;
        if (obj instanceof String) {
            str = (String)obj;
        }
        else if (obj == null) {
            str = "";
        }
        else str = obj.toString();
        
        return StringEscapeUtils.escapeHtml4(str);
    }
    
    public static String javascriptEscape(Object obj) {
        String str = null;
        if (obj instanceof String) {
            str = (String)obj;
        }
        else if (obj == null) {
            str = "";
        }
        else str = obj.toString();
        
        str = StringUtils.replace(str, "'", "\\'");
        str = StringUtils.replace(str, "\"", "\\\"");
        
        return str;
    }
    
    public static String objectId(Object obj) {
        if (obj == null)  return "-1";
        DecimalFormat format = new DecimalFormat("#");
        return format.format(System.identityHashCode(obj));
    }
    
    public static Date toDate(Object timeInMillis) {
        if (timeInMillis == null)  return null;
        if (timeInMillis instanceof Number) {
            return new Date( ((Number)timeInMillis).longValue());
        }
        
        throw new Admin4jRuntimeException("Invalid Long date")
            .addContextValue("timeInMillis", timeInMillis);
    }
    
    public static Double bytes2Mb(Number bytes) {
        if (bytes == null) {
            return null;
        }
        
        return bytes.doubleValue() / 1024000.000;
    }
    
    public static String abbreviate(String text, Number maxNbrChars) {
        return abbreviate(text, maxNbrChars, "...");
    }
    
    public static String abbreviate(String text, Number maxNbrChars, String suffix) {
        if (text == null) {
            return text;
        }
        String localSuffix = suffix;
        if (localSuffix == null) {
            localSuffix = "";
        }
        
        if (text.length() > maxNbrChars.intValue()) {
            return text.substring(0, maxNbrChars.intValue()) + localSuffix;
        }
        return text;
    }

}
