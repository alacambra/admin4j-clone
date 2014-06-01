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

import java.lang.management.ManagementFactory;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.jdbc.driver.sql.ConnectionWrapper30Base;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Exposes Driver info via the JMX Console
 * @author D. Ashmore
 * @since 1.0
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public class Admin4jJdbcJmxMBean extends StandardMBean implements Admin4jJdbcJmx {
	
	private ConnectionInfo[] connections;
	
	public Admin4jJdbcJmxMBean() throws NotCompliantMBeanException {
		super(Admin4jJdbcJmx.class);
		refreshConnectionList();
	}

	private void refreshConnectionList() {
		List<ConnectionInfo> list = new ArrayList<ConnectionInfo>();
		ConnectionInfo cInfo;
		for (ConnectionWrapper30Base conn: ConnectionRegistry.getCurrentConnectionSet()) {
			cInfo = new ConnectionInfo();
			cInfo.setSqlStatementHistory(conn.getSqlStatementHistory().toArray(new StatementInfo[0]));
//			cInfo.setDatabaseName(conn.getDatabaseName());
//			cInfo.setInstanceName(conn.getInstanceName());
//			cInfo.setUserName(conn.getUser());
//			cInfo.setServerName(conn.getServerName());
			if (cInfo.getSqlStatementHistory() != null && cInfo.getSqlStatementHistory().length > 0) {
				cInfo.setLastSqlStatement(cInfo.getSqlStatementHistory()[0]);
			}
			list.add(cInfo);
		}
		
		connections = list.toArray(new ConnectionInfo[0]);
	}
	
	public static void registerMBean()  {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("Jdbc:type=Admin4j");
			Object mbean = new Admin4jJdbcJmxMBean();
			if (!mbs.isRegistered(name) ) {
				mbs.registerMBean(mbean, name);
			}
			else {
			    if (DriverManager.getLogWriter() != null) {
			        DriverManager.getLogWriter().println("Admin4jJdbcJmxMBean already registered.");
			    }
			}
		} catch (Exception e) {
			throw new RuntimeException("Admin4jJdbcJmxMBean registration error", e);
		} 

	}
	
	public static class ConnectionInfo {
		private StatementInfo lastSqlStatement;
		private StatementInfo[] sqlStatementHistory;
		private String userName;
		private String databaseName;
		private String serverName;
		private String instanceName;
		public StatementInfo getLastSqlStatement() {
			return lastSqlStatement;
		}
		public void setLastSqlStatement(StatementInfo lastSqlStatement) {
			this.lastSqlStatement = lastSqlStatement;
		}
		public StatementInfo[] getSqlStatementHistory() {
			return sqlStatementHistory;
		}
		public void setSqlStatementHistory(StatementInfo[] sqlStatementHistory) {
			this.sqlStatementHistory = sqlStatementHistory;
		}
		
		public String getLastSqlStatementStr() {
			return this.lastSqlStatement.toString();
		}
		
		@Override
		public String toString() {
			return lastSqlStatement.toString();
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getDatabaseName() {
			return databaseName;
		}
		public void setDatabaseName(String databaseName) {
			this.databaseName = databaseName;
		}
		public String getServerName() {
			return serverName;
		}
		public void setServerName(String serverName) {
			this.serverName = serverName;
		}
		public String getInstanceName() {
			return instanceName;
		}
		public void setInstanceName(String instanceName) {
			this.instanceName = instanceName;
		}
		
	}
	
	public static class StatementInfo {
		public String sqlText;
		public StackTraceElement[] executionStack;
		
		public StatementInfo() {}
		public StatementInfo(String sqlText, StackTraceElement[] executionStack) {
			this.setSqlText(sqlText);
			this.setExecutionStack(executionStack);
		}
		
		public String getSqlText() {
			return sqlText;
		}
		public void setSqlText(String sqlText) {
			this.sqlText = sqlText;
		}
		public StackTraceElement[] getExecutionStack() {
			return executionStack;
		}
		public void setExecutionStack(StackTraceElement[] executionStack) {
			this.executionStack = executionStack;
		}
		
		public String getExecutionStackString() {
			StringBuffer buffer = new StringBuffer();
			for (StackTraceElement element: this.executionStack) {
				if (buffer.length() > 0) {
					buffer.append(SystemUtils.LINE_SEPARATOR);
				}
				buffer.append(element);
			}
			
			return buffer.toString();
		}
		
		@Override
		public String toString() {
			return sqlText;
		}
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jtds.jdbc.JtdsJmx#getConnections()
	 */
	public ConnectionInfo[] getConnections() {
		refreshConnectionList();
		return connections;
	}

}
