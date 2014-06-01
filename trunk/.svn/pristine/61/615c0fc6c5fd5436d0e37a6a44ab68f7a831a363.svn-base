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

import java.beans.DefaultPersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Set;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.dao.ExceptionInfoDAO;
import net.admin4j.entity.ExceptionInfo;

/**
 * DAO implementation for reading and writing Exception information.
 * @author D. Ashmore
 * @since 1.0
 */
public class ExceptionInfoDAOXml extends BaseDAOXml implements ExceptionInfoDAO {
    
    private static final Object SAVE_LOCK = new Object();

    @SuppressWarnings("unchecked")
    public Set<ExceptionInfo> findAll() {
        return (Set<ExceptionInfo>) readXmlFile(Admin4JConfiguration.getExceptionInformationXmlFileName());
    }

    public void saveAll(Set<ExceptionInfo> exceptionList) {
        XMLEncoder encoder = null;
        String xmlFileName = Admin4JConfiguration.getExceptionInformationXmlFileName();
        String tempFileName = xmlFileName + ".temp";
        String previousFileName = derivePreviousFileName(xmlFileName);

        boolean encoderClosed = false;
        
        synchronized (SAVE_LOCK) {
            /*
             * Write to a .temp file
             * Rename configured xml file to .previous
             * Rename new .temp to the configured file name.
             * 
             * Note: classloader assigning business to work around JDK bug
             * http://bugs.sun.com/view_bug.do?bug_id=6329581
             */
            ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(ExceptionInfo.class.getClassLoader());
                encoder = new XMLEncoder(
                        new BufferedOutputStream(
                            new FileOutputStream(tempFileName)));
                encoder.setExceptionListener(new DefaultExceptionListener(xmlFileName));
                encoder.setPersistenceDelegate(ExceptionInfo.class, 
                        new DefaultPersistenceDelegate(
                                new String[]{"exceptionClassName","stackTrace", "alternateId"}));
                encoder.setPersistenceDelegate(ExceptionInfo.TimeStamp.class, 
                        new DefaultPersistenceDelegate(
                                new String[]{"timestampInMillis"}));
                encoder.setPersistenceDelegate(StackTraceElement.class, 
                        new DefaultPersistenceDelegate(
                                new String[]{"className","methodName",
                                        "fileName","lineNumber"}));
                encoder.writeObject(exceptionList);
                encoder.close();
                encoderClosed = true;
                
                versionOutputFile(xmlFileName, tempFileName, previousFileName);
                
            }
            catch (Throwable t) {
                if (encoder != null && !encoderClosed) encoder.close();
            }
            finally {
                Thread.currentThread().setContextClassLoader(currentContextLoader);
            }
        }

    }

}
