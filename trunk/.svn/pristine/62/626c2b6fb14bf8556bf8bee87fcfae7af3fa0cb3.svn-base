
package net.admin4j.util;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for net.admin4j.util.HostUtils. 
 * @author D. Ashmore
 *
 */
public class TestHostUtils {

    @Test
    public void testDeriveServerName() {
        assertTrue("Failed a.b test", HostUtils.deriveServerName("a.b").equals("a"));
        assertTrue("Failed a test", HostUtils.deriveServerName("a").equals("a"));
        assertTrue("Failed foo.com test", HostUtils.deriveServerName("foo.com").equals("foo"));
        assertTrue("Failed foo.myorg.com test", HostUtils.deriveServerName("foo.myorg.com").equals("foo"));
    }
    
    @Test
    public void testGetHostName() {
        assertTrue("Failed getHostName test", HostUtils.getHostName() != null);
    }

}
