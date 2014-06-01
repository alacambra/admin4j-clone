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

import static org.junit.Assert.*;

import javax.servlet.FilterConfig;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTextEmailNotifier {
    
    private static final String TEST_TO_ADDRESS = "manager@test.com";
    private static final String TEST_FROM_ADDRESS = "tester@test.com";
    private static final String TEST_MAIL_SERVER_NAME = "foo.bogus.com";
    private TextEmailNotifier notifier1;
    private SmsTextEmailNotifier notifier2;

    @Before
    public void setUp() throws Exception {
        notifier1 = new TextEmailNotifier();
        notifier2 = new SmsTextEmailNotifier();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        Assert.assertTrue("html test", !notifier1.supportsHtml());
        Assert.assertTrue("sms test", !notifier1.supportsSMS());
        localTestNotifier(notifier1);
        
        Assert.assertTrue("html test", !notifier2.supportsHtml());
        Assert.assertTrue("sms test", notifier2.supportsSMS());
        localTestNotifier(notifier2);
    }
    
    public void localTestNotifier(Notifier notifier) throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn(null).atLeastOnce();
        
        EasyMock.replay(config);
        notifier.configure(config);
        try {
            notifier.notify("Boo", "Boo");
            fail("Mail server not existing!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
