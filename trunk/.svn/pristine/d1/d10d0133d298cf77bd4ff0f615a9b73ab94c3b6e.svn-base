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
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Set;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.dao.TaskTimerDAO;
import net.admin4j.timer.BasicTaskTimer;
import net.admin4j.timer.SummaryDataMeasure;
import net.admin4j.timer.TaskTimer;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * DAO implementation for reading/writing performance measurement information.
 * @author D. Ashmore
 * @since 1.0
 */
public class TaskTimerDAOXml extends BaseDAOXml implements TaskTimerDAO {
    
    private static final Object SAVE_LOCK = new Object();

    @SuppressWarnings("unchecked")
    public Set<TaskTimer> findAll() {
        Set<TaskTimer> result = null;
        XMLDecoder decoder = null;
        String xmlFileName = Admin4JConfiguration.getPerformanceInformationXmlFileName();
        
        /*
         * Note: classloader assigning business to work around JDK bug
         * http://bugs.sun.com/view_bug.do?bug_id=6329581
         */
        ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(TaskTimer.class.getClassLoader());
            decoder = new XMLDecoder(
                new BufferedInputStream(
                    new FileInputStream(xmlFileName)));
            decoder.setExceptionListener(new DefaultExceptionListener(xmlFileName));
            result = (Set<TaskTimer>)decoder.readObject();
        }
        catch (Throwable t) {
            throw new Admin4jRuntimeException("Error reading XML Performance Information.", t)
                .addContextValue("xmlFileName", xmlFileName);
        }
        finally {
            if (decoder != null) decoder.close();
            Thread.currentThread().setContextClassLoader(currentContextLoader);
        }
        
        return result;
    }

    public void saveAll(Set<TaskTimer> exceptionList) {
        XMLEncoder encoder = null;
        String xmlFileName = Admin4JConfiguration.getPerformanceInformationXmlFileName();
        String tempFileName = xmlFileName + ".temp";
        String previousFileName = derivePreviousFileName( ".previous" );

        boolean encoderClosed = false;
        
        /*
         * Write to a .temp file
         * Rename configured xml file to .previous
         * Rename new .temp to the configured file name.
         * 
         * Note: classloader assigning business to work around JDK bug
         * http://bugs.sun.com/view_bug.do?bug_id=6329581
         */
        synchronized (SAVE_LOCK) {
            
            ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
            try {
                
                Thread.currentThread().setContextClassLoader(TaskTimer.class.getClassLoader());
                encoder = new XMLEncoder(
                        new BufferedOutputStream(
                            new FileOutputStream(tempFileName)));
                encoder.setExceptionListener(new DefaultExceptionListener(xmlFileName));
                encoder.setPersistenceDelegate(BasicTaskTimer.class, 
                        new DefaultPersistenceDelegate(
                                new String[]{"label","dataMeasures"}));
                encoder.setPersistenceDelegate(SummaryDataMeasure.class, 
                        new DefaultPersistenceDelegate(
                                new String[]{"firstObservationTime"}));
               
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
