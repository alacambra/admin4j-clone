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
package net.admin4j.jdbc.driver.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.admin4j.jdbc.driver.ConnectionRegistry;
import net.admin4j.jdbc.driver.DriverContext;
import net.admin4j.jdbc.driver.DriverContextImpl;
import net.admin4j.jdbc.driver.Admin4jJdbcJmxMBean.StatementInfo;
import net.admin4j.jdbc.driver.DriverContextRegistry;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Wraps a JDBC V2.0 connection so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class ConnectionWrapper30Base implements Connection, DriverContext {
	
	protected Connection connection;
	private StackTraceElement[] creationStackTrace;
	
	private List<StatementInfo> sqlStatementList = new ArrayList<StatementInfo>();
    private static final int MAX_STATEMENT_LIST_SIZE=10;
    
    private final DriverContext driverContext = new DriverContextImpl();
       
	public ConnectionWrapper30Base(Connection conn) {
	    SQLWrappingUtils.notNull(conn, "Null connection not allowed.");
		this.connection = conn;
		ConnectionRegistry.register(this);
		this.creationStackTrace = Thread.currentThread().getStackTrace();
	}

	public StackTraceElement[] registerSqlStatement(String sqlText) {
	    StackTraceElement[] stack = null;
	    if (DriverContextRegistry.isTrackExecutionStacks(this.getDriverContext())) {
	        stack = Thread.currentThread().getStackTrace();
	    }
	    else {
	        stack = new StackTraceElement[0];
	    }
        StatementInfo sInfo = new StatementInfo(sqlText, stack);
        synchronized(sqlStatementList) {
            this.sqlStatementList.add(sInfo);
            while (this.sqlStatementList.size() > MAX_STATEMENT_LIST_SIZE) {
                this.sqlStatementList.remove(0);
            }
        }
        
        return stack;
    }
    
    public List<StatementInfo> getSqlStatementHistory() {
        List<StatementInfo> list = new ArrayList<StatementInfo>();
        synchronized(sqlStatementList) {
            list.addAll(this.sqlStatementList);
        }
        
        Collections.reverse(list);
        return list;
    }
	
	public void clearWarnings() throws SQLException {
		connection.clearWarnings();

	}

	
	public void close() throws SQLException {
		try {connection.close();}
		finally {
			ConnectionRegistry.unRegister(this);
		}
	}

	
	public void commit() throws SQLException {
		connection.commit();

	}

	
	private Statement wrapStatement(Statement stmt) {
	    return (Statement)SQLWrappingUtils.wrap(stmt, this);
	}
	
	private PreparedStatement wrapPreparedStatement(PreparedStatement stmt, String sqlText, StackTraceElement[] stack) {
        return (PreparedStatement)SQLWrappingUtils.wrap(stmt, this, sqlText, stack);
    }
	
	private CallableStatement wrapCallableStatement(CallableStatement stmt, String sqlText, StackTraceElement[] stack) {
        return (CallableStatement)SQLWrappingUtils.wrap(stmt, this, sqlText, stack);
    }
	
	public Statement createStatement() throws SQLException {
		return this.wrapStatement(connection.createStatement());
	}

	
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.wrapStatement(connection.createStatement(resultSetType, resultSetConcurrency));
	}

	
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.wrapStatement(connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	
	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	
	public String getCatalog() throws SQLException {
		return connection.getCatalog();
	}

	
	public int getHoldability() throws SQLException {
		return connection.getHoldability();
	}

	
	public DatabaseMetaData getMetaData() throws SQLException {
		return connection.getMetaData();
	}

	
	public int getTransactionIsolation() throws SQLException {
		return connection.getTransactionIsolation();
	}

	
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return connection.getTypeMap();
	}

	
	public SQLWarning getWarnings() throws SQLException {
		return connection.getWarnings();
	}

	
	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}

	
	public boolean isReadOnly() throws SQLException {
		return connection.isReadOnly();
	}

	
	public String nativeSQL(String sql) throws SQLException {
		return connection.nativeSQL(sql);
	}

	
	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.wrapCallableStatement(connection.prepareCall(sql)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.wrapCallableStatement(
		        connection.prepareCall(sql, resultSetType, resultSetConcurrency)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.wrapCallableStatement(
		        connection.prepareCall(sql, resultSetType
		                , resultSetConcurrency, resultSetHoldability)
		                , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.wrapPreparedStatement(connection.prepareStatement(sql)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.wrapPreparedStatement(
		        connection.prepareStatement(sql, autoGeneratedKeys)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return this.wrapPreparedStatement(connection.prepareStatement(sql, columnIndexes)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return this.wrapPreparedStatement(
		        connection.prepareStatement(sql, columnNames)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.wrapPreparedStatement(
		        connection.prepareStatement(sql, resultSetType, resultSetConcurrency)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.wrapPreparedStatement(
		        connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
		        , sql, this.registerSqlStatement(sql));
	}

	
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		connection.releaseSavepoint(savepoint);
	}

	
	public void rollback() throws SQLException {
		connection.rollback();
	}

	
	public void rollback(Savepoint savepoint) throws SQLException {
		connection.rollback(savepoint);
	}

	
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);
	}

	
	public void setCatalog(String catalog) throws SQLException {
		connection.setCatalog(catalog);
	}

	
	public void setHoldability(int holdability) throws SQLException {
		connection.setHoldability(holdability);
	}

	
	public void setReadOnly(boolean readOnly) throws SQLException {
		connection.setReadOnly(readOnly);
	}

	
	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	
	public Savepoint setSavepoint(String name) throws SQLException {
		return connection.setSavepoint(name);
	}

	
	public void setTransactionIsolation(int level) throws SQLException {
		connection.setTransactionIsolation(level);

	}

	
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		connection.setTypeMap(map);

	}

	
	public Connection getConnection() {
		return connection;
	}

	
	protected void finalize() throws Throwable {
	    if (!this.isClosed()) {
	        DriverManager.getLogWriter().println("Leaked database connection closed.  Connection created at: " + SQLWrappingUtils.LINE_SEPARATOR + SQLWrappingUtils.array2String(this.getCreationStackTrace()));
	        this.close();
	    }
	    else ConnectionRegistry.unRegister(this);
		super.finalize();
	}

	public StackTraceElement[] getCreationStackTrace() {
		return creationStackTrace;
	}

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#getDriverClassname()
     */
    public String getDriverClassname() {
        return driverContext.getDriverClassname();
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#setDriverClassname(java.lang.String)
     */
    public void setDriverClassname(String driverClassname) {
        this.driverContext.setDriverClassname(driverClassname);
        
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#getDriverMajorVersion()
     */
    public Integer getDriverMajorVersion() {
        return driverContext.getDriverMajorVersion();
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#setDriverMajorVersion(java.lang.String)
     */
    public void setDriverMajorVersion(Integer driverMajorVersion) {
        this.driverContext.setDriverMajorVersion(driverMajorVersion);
        
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#getDriverMinorVersion()
     */
    public Integer getDriverMinorVersion() {
        return driverContext.getDriverMinorVersion();
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#setDriverMinorVersion(java.lang.String)
     */
    public void setDriverMinorVersion(Integer driverMinorVersion) {
        this.driverContext.setDriverMinorVersion(driverMinorVersion);
        
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#getPoolname()
     */
    public String getPoolName() {
        return driverContext.getPoolName();
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.DriverContext#setPoolname(java.lang.String)
     */
    public void setPoolName(String poolName) {
        this.driverContext.setPoolName(poolName);
        
    }

    public DriverContext getDriverContext() {
        return driverContext;
    }

}
