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
package net.admin4j.jdbc.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.jdbc.driver.sql.SQLWrappingUtils;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Proxy JDBC driver that allows JMX view into JDBC connection content and tracks
 * SQL statement performance.
 * 
 * <p>The connection URL for this driver is in the following format:</p>
 * <pre>
 *         jdbcx:admin4j:[admin4J properties]::[underlying driver URL]
 * </pre>
 * <p>For example:</p>
 * <pre>
 *         jdbcx:admin4j:driver=org.hsqldb.jdbcDriver,poolName=mainPoolDB::jdbc:hsqldb:mem:Admin4JTestDb
 * </pre>
 * 
 * <p>Supported Admin4J driver properties are the following:</p>
 * <li>driver - Required.  Class name of the underlying JDBC driver</li>
 * <li>poolName - Optional.  Default none.  Name of the connection pool to which this driver is being assigned.  
 * This is displayed with performance metrics.</li>
 * <li>stackTrace - Optional (true/false).  Default true.  Specifies if execution stack traces are tracked so you know where
 * in your code a SQL statement is being executed.  Tests show setting to false will save 
 * approximately 1 millisecond per seven SQL Statement executions.</li>
 * 
 * <p>Top resource intensive SQL statements can be browsed via the SqlDisplayServlet.  By default, the top 50
 * statements are shown.  This is configurable (see PropertyConfigurator)</p>
 * 
 * <p>Connection activity is also viewable via JMX or via the JmxServlet.</p>
 * 
 * <p>This driver honors the following configuration settings (see PropertyConfigurator):</p>
 * <li>sql.nbr.retained.sql.statements</li>
 * <li>sql.retention.time.in.millis</li>
 * 
 * @author D. Ashmore
 * @since 1.0
 * @see net.admin4j.ui.servlets.SqlDisplayServlet
 * @see net.admin4j.ui.servlets.JmxServlet
 * @see net.admin4j.ui.config.PropertyConfigurator
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class Admin4jJdbcDriverJdk5Base implements Driver {
	
	private static final String URL_PREFIX="jdbcx:admin4j:";
	private static final String URL_SEPARATOR="::";
	
	private static boolean inUse = false;

	public Admin4jJdbcDriverJdk5Base() {
	    
		try {
		    if (DriverManager.getLogWriter() != null) {
		        DriverManager.getLogWriter().println("Admin4jJdbcDriver initialization started.");
		    }
			DriverManager.registerDriver(this);
			Admin4jJdbcJmxMBean.registerMBean();
			if (DriverManager.getLogWriter() != null) {
			    DriverManager.getLogWriter().println("Admin4jJdbcDriver initialization finished.");
			}
			
			inUse = true;
		} catch (SQLException e) {
			throw new RuntimeException("Cannot register driver", e);
		}
	}

	public boolean acceptsURL(String url) throws SQLException {
		return !StringUtils.isEmpty(url) && url.startsWith(URL_PREFIX) && url.indexOf(URL_SEPARATOR) > 0;
	}

	public Connection connect(String url, Properties info) throws SQLException {
	    Validate.notEmpty(url, "Null of blank url not allowed.");
		Validate.isTrue(this.acceptsURL(url), "Url not accepted by this driver.  url=" + url);
		String configSpec=url.substring(URL_PREFIX.length(), url.indexOf(URL_SEPARATOR));
		String driverUrlSpec=url.substring(url.indexOf(URL_SEPARATOR) + 2);
		Properties config = this.findDriverProperties(configSpec);
		String poolName = config.getProperty("poolName");
		String stackTraceStr = config.getProperty("stackTrace");
		String driverName = config.getProperty("driver");
		
		Validate.isTrue( !config.contains("driver"), "Url doesn't contain driver spec listing class name for JDBC driver.");
		
		Driver driver = null;
		
		try {
			driver = (Driver)Class.forName(driverName).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		DriverContext driverContext = new DriverContextImpl(driverName, driver.getMajorVersion(), driver.getMinorVersion(), poolName);
		if ( !StringUtils.isEmpty(stackTraceStr) && !DriverContextRegistry.hasSettings(driverContext)) {
		    DriverContextRegistry.setTrackExecutionStacks(driverContext, Boolean.parseBoolean(stackTraceStr));
		}
		
	    try {
            Connection wrappedConnection =  (Connection)SQLWrappingUtils.wrap(driver.connect(driverUrlSpec, info));
            SQLWrappingUtils.encodeDriverContext(wrappedConnection
                    , driverName
                    , poolName
                    , driver.getMajorVersion()
                    , driver.getMinorVersion());
            
            return wrappedConnection;
        } catch (Exception e) {
           if (e instanceof SQLException) {
               throw (SQLException)e;
           }
           
           Admin4jRuntimeException ae = null;
           if (e instanceof Admin4jRuntimeException) {
               ae = (Admin4jRuntimeException)e;
           }
           else {
               ae = new Admin4jRuntimeException("Error creating connection");
           }
           throw ae
               .addContextValue("driverUrlSpec", driverUrlSpec)
               .addContextValue("info", info)
               .addContextValue("url", url);
        } 

	}
	
	private Properties findDriverProperties(String spec) {
		Properties props = new Properties();
		
		StringTokenizer allPropToken = new StringTokenizer(spec, ",");
		StringTokenizer propToken;
		String key,value;
		while (allPropToken.hasMoreTokens()) {
			propToken = new StringTokenizer(allPropToken.nextToken(), "="); 
			if (propToken.hasMoreTokens()) {
				key = propToken.nextToken();
			}
			else key = "";
			
			if (propToken.hasMoreTokens()) {
				value = propToken.nextToken();
			}
			else value="";
			props.put(key, value);
		}
		
		return props;
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 1;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		return new DriverPropertyInfo[0];
	}

	public boolean jdbcCompliant() {
		return false;
	}

    public static boolean isInUse() {
        return inUse;
    }

}
