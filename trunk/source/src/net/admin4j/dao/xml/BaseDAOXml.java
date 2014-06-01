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

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import net.admin4j.util.Admin4jRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDAOXml {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected void versionOutputFile(String xmlFileName, String tempFileName,
            String previousFileName) {
        File file;
        // Rename configured Xml file to .previous
        file = new File(previousFileName);
        if (file.exists()) {
            file.delete();
        }
        file = new File(xmlFileName);
        if (file.exists()) {
            file.renameTo(new File(previousFileName));
        }
        
        // Rename .temp file to configured Xml File Name
        file = new File(tempFileName);
        file.renameTo(new File(xmlFileName));
    }
    
    protected String derivePreviousFileName(String xmlFileName) {
        return xmlFileName + ".previous";
    }
    
    protected Object readXmlFile(String xmlFileName) {
        Object result = null;
        XMLDecoder decoder = null;
        boolean exceptionThrown = false;
        
        /*
         * Note: classloader assigning business to work around JDK bug
         * http://bugs.sun.com/view_bug.do?bug_id=6329581
         */
        ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            DefaultExceptionListener errorListener = new DefaultExceptionListener(xmlFileName);
            decoder = new XMLDecoder(
                new BufferedInputStream(
                    new FileInputStream(xmlFileName)));
            decoder.setExceptionListener(errorListener);
            try {result = decoder.readObject();}
            catch (Exception e) {
                logger.error("Error reading file " + xmlFileName, e);
                exceptionThrown = true;
            }
            
            if (errorListener.isError() || exceptionThrown) {
                exceptionThrown = false;
                String previousFileName = derivePreviousFileName(xmlFileName);
                errorListener = new DefaultExceptionListener(previousFileName);
                decoder = new XMLDecoder(
                        new BufferedInputStream(
                            new FileInputStream(previousFileName)));
                decoder.setExceptionListener(errorListener);
                try {result = decoder.readObject();}
                catch (Exception e) {
                    logger.error("Error reading file " + previousFileName, e);
                    exceptionThrown = true;
                }
                
                if (errorListener.isError() || exceptionThrown) {
                    Admin4jRuntimeException ae = new Admin4jRuntimeException("Error reading xml Input after two attempts");
                    ae.addContextValue("xmlFileName", xmlFileName);
                    ae.addContextValue("previousFileName", previousFileName);
                    
                    throw ae;
                }
                else {
                    logger.warn("Error reading xmlFile {}.  Back up copy used.", xmlFileName);
                }
            }
        }
        catch (Throwable t) {
            throw new Admin4jRuntimeException("Error reading XML Exception Information.", t)
                .addContextValue("xmlFileName", xmlFileName);
        }
        finally {
            if (decoder != null) decoder.close();
            Thread.currentThread().setContextClassLoader(currentContextLoader);
        }
        
        return result;
    }

}
