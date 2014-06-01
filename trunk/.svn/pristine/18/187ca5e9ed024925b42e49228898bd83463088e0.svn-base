package net.admin4j.jdbc.driver;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.Assert;

import net.admin4j.deps.commons.lang3.JavaVersion;
import net.admin4j.deps.commons.lang3.SystemUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin4jJdbcDriver {
	
	private Driver driver;

	@Before
	public void setUp() throws Exception {
	    if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_7)) {
	        driver = (Driver)Class.forName("net.admin4j.jdbc.driver.Admin4jJdbcDriverJdk7").newInstance();
	    }
	    else {
	        driver = (Driver)Class.forName("net.admin4j.jdbc.driver.Admin4jJdbcDriverJdk5").newInstance();
	    }
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAcceptsURL() {
		try{
			driver.acceptsURL("crap");
			fail("Accepted bad url 'crap'");
			}
		catch (Throwable t) {
			// No Op
		}
		
		try{
			driver.acceptsURL("jdbcx:admin4j:driver=crap::blah");
			
			}
		catch (Throwable t) {
			fail("Rejected good url 'jdbcx:jmx:driver=crap::blah'");
		}
		
	}
	
	@Test
    public void testDriverManagerRegistration() {
	    try {
	        Assert.assertTrue("driver Manager test", 
	                driver.getClass() == DriverManager.getDriver("jdbcx:admin4j:driver=crap::blah").getClass());
	    }
	    catch (Throwable t) {
	        t.printStackTrace();
            fail("driverManager rejected good url 'jdbcx:jmx:driver=crap::blah'");
        }
	    
	}

	@Test
	public void testConnectBasic() {
		Connection conn = null;
		try {
			Properties props = new Properties();
			props.put("user", "sa");
			props.put("password", "");
			conn = driver.connect("jdbcx:admin4j:driver=org.hsqldb.jdbcDriver::jdbc:hsqldb:mem:Admin4JTestDb", new Properties());
			conn.close();
			
			conn = driver.connect("jdbcx:admin4j:driver=org.hsqldb.jdbcDriver,poolName=fooDb::jdbc:hsqldb:mem:Admin4JTestDb", new Properties());
            conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			fail("didn't properly connect with good url");
		}
	}

}
