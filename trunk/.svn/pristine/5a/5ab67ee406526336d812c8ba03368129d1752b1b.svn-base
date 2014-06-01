
package net.admin4j.util.notify;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for class net.admin4j.util.notify.EmailNotifier.
 * @author D. Ashmore
 *
 */
public class TestEmailNotifier {
    
    private static final String TEST_TO_ADDRESS = "manager@test.com";
    private static final String TEST_FROM_ADDRESS = "tester@test.com";
    private static final String TEST_MAIL_SERVER_NAME = "foo.bogus.com";
    private HtmlEmailNotifier notifier;

    @Before
    public void setUp() throws Exception {
        notifier = new HtmlEmailNotifier();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test 
    public void testServletConfigure1() throws Exception {
        ServletConfig config = EasyMock.createMock(ServletConfig.class);
        
        // Positive case 1
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn(null).atLeastOnce();
        
        EasyMock.replay(config);
        notifier.configure(config);
        testPositiveConfigureCase1();
        
        EasyMock.verify(config);
 
    }
    
    @Test 
    public void testServletConfigure2() throws Exception {
        ServletConfig config = EasyMock.createMock(ServletConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        notifier.configure(config);
        testPositiveConfigureCase2();
        
        EasyMock.verify(config);
    }
    
    @Test 
    public void testServletConfigureMissingServer() throws Exception {
        ServletConfig config = EasyMock.createMock(ServletConfig.class);
        
        // Missing Server
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn(null).atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing server not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
 
    }
    
    public void testServletConfigureMissingToAddress() throws Exception {
        ServletConfig config = EasyMock.createMock(ServletConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing to address not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
    }
    
    @Test 
    public void testServletConfigureMissingFromAddress() throws Exception {
        ServletConfig config = EasyMock.createMock(ServletConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing from address not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
    }

    private void testPositiveConfigureCase2() {
        assertTrue("Failed mail server name test", notifier.getMailServerHost().equals(TEST_MAIL_SERVER_NAME));
        assertTrue("Failed from address test", notifier.getFromEmailAddress().equals(TEST_FROM_ADDRESS));
        assertTrue("Failed to address test", notifier.getToEmailAddress().equals(TEST_TO_ADDRESS));
        assertTrue("Failed server set size test", notifier.getAllowedServerSet().size() == 3);
    }
    
    private void testPositiveConfigureCase1() {
        assertTrue("Failed mail server name test", notifier.getMailServerHost().equals(TEST_MAIL_SERVER_NAME));
        assertTrue("Failed from address test", notifier.getFromEmailAddress().equals(TEST_FROM_ADDRESS));
        assertTrue("Failed to address test", notifier.getToEmailAddress().equals(TEST_TO_ADDRESS));
        assertTrue("Failed server set size test", notifier.getAllowedServerSet().size() == 0);
    }
    
    @Test 
    public void testFilterConfigure1() throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        
     // Positive case 1
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn(null).atLeastOnce();
        
        EasyMock.replay(config);
        notifier.configure(config);
        testPositiveConfigureCase1();
        
        EasyMock.verify(config);
    }
    
    @Test 
    public void testFilterConfigure2() throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        notifier.configure(config);
        testPositiveConfigureCase2();
        
        EasyMock.verify(config);
    }
    
    @Test 
    public void testFilterConfigureMissingServer() throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        
        // Missing Server
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn(null).atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing server not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
 
    }
    
    public void testFilterConfigureMissingToAddress() throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(TEST_FROM_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing to address not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
    }
    
    @Test 
    public void testFilterConfigureMissingFromAddress() throws Exception {
        FilterConfig config = EasyMock.createMock(FilterConfig.class);
        
     // Positive case 2 -- Optional allowed servers
        EasyMock.expect(config.getInitParameter("from.email.address")).andReturn(null).atLeastOnce();
        EasyMock.expect(config.getInitParameter("to.email.address")).andReturn(TEST_TO_ADDRESS).atLeastOnce();
        EasyMock.expect(config.getInitParameter("mail.server.host")).andReturn(TEST_MAIL_SERVER_NAME).atLeastOnce();
        EasyMock.expect(config.getInitParameter("allowed.servers")).andReturn("foo, fu, bar").atLeastOnce();
        
        EasyMock.replay(config);
        try{
            notifier.configure(config);
            fail("Missing from address not excepted!");
        }
        catch (Exception e) {
            // NoOp
        }
    }
    
    @Test 
    public void testBasic() throws Exception {
        Assert.assertTrue("html test", notifier.supportsHtml());
        Assert.assertTrue("sms test", !notifier.supportsSMS());
        
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
