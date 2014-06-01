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
package net.admin4j.util.notify;

import java.util.Properties;

import junit.framework.Assert;

import net.admin4j.ui.filters.MockFilterConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogNotifier {
    
    private LogNotifier notifier;

    @Before
    public void setUp() throws Exception {
        notifier = new LogNotifier();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        notifier.configure(new MockFilterConfig());
        notifier.configure("boo", new Properties());
        
        notifier.notify("Boo", "Boo");
        notifier.notify(null, "Boo");
        notifier.notify("Boo", null);
        
        Assert.assertTrue("html test", !notifier.supportsHtml());
        Assert.assertTrue("sms test", !notifier.supportsSMS());
    }

}
