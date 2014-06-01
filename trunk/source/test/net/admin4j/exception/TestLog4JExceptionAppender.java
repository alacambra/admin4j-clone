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
package net.admin4j.exception;

import junit.framework.Assert;

import net.admin4j.deps.commons.lang3.reflect.FieldUtils;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLog4JExceptionAppender {
    
    private Log4JExceptionAppender handler;

    @Before
    public void setUp() throws Exception {
        handler = new Log4JExceptionAppender();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Exception ex = new Exception("More Boo!");
        
        LoggingEvent event = new LoggingEvent("Boo", Category.getRoot(), Priority.ERROR,null, ex);
        handler.append(event);
        
        Assert.assertTrue("tracking test", ExceptionTracker.findExceptionInfo(ex) != null);
        Assert.assertTrue("layout test", !handler.requiresLayout());
        
        handler.append(null);
    }
    
    @Test
    public void testErrorProcessing() throws Exception {
        handler.processError(new RuntimeException("Boo"));
        
        FieldUtils.writeStaticField(Log4JExceptionAppender.class, "logger", null, true);
        handler.processError(new RuntimeException("Boo"));
    }

}
