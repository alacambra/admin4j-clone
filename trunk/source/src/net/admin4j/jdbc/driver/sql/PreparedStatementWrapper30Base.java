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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.jdbc.driver.SqlStatementTimerFactory;
import net.admin4j.timer.TaskTimer;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Wraps a JDBC V2.0 prepared statement so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class PreparedStatementWrapper30Base extends StatementWrapper30Base implements
        PreparedStatement, SqlContext {
    
    private String sqlText;
    private StackTraceElement[] executionStack;
    
    public PreparedStatementWrapper30Base(ConnectionWrapper30Base connection,
            PreparedStatement statement) {
        super(connection, statement);
    }
    
    protected PreparedStatement getUnderlyingPreparedStatement() {
        return (PreparedStatement)this.getUnderlyingStatement();
    }

    public void addBatch() throws SQLException {
        this.getUnderlyingPreparedStatement().addBatch();

    }

    public void clearParameters() throws SQLException {
        this.getUnderlyingPreparedStatement().clearParameters();

    }

    public boolean execute() throws SQLException {
        TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(this.getSqlText(), 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getExecutionStack());
            return this.getUnderlyingPreparedStatement().execute();
        }
        finally {
            if (timer != null) timer.stop();
        }
    }

    public ResultSet executeQuery() throws SQLException {
        TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(this.getSqlText(), 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getExecutionStack());
            return this.getUnderlyingPreparedStatement().executeQuery();
        }
        finally {
            if (timer != null) timer.stop();
        }
    }

    public int executeUpdate() throws SQLException {
        TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(this.getSqlText(),
                    this.getConnectionWrapper().getDriverContext(),
                    this.getExecutionStack());
            return this.getUnderlyingPreparedStatement().executeUpdate();
        }
        finally {
            if (timer != null) timer.stop();
        }
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return this.getUnderlyingPreparedStatement().getMetaData();
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.getUnderlyingPreparedStatement().getParameterMetaData();
    }

    public void setArray(int parameterIndex, Array x) throws SQLException {
        this.getUnderlyingPreparedStatement().setArray(parameterIndex, x);

    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setAsciiStream(parameterIndex, x, length);

    }

    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setBigDecimal(parameterIndex, x);

    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setBinaryStream(parameterIndex, x, length);

    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.getUnderlyingPreparedStatement().setBlob(parameterIndex, x);

    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.getUnderlyingPreparedStatement().setBoolean(parameterIndex, x);

    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.getUnderlyingPreparedStatement().setByte(parameterIndex, x);

    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.getUnderlyingPreparedStatement().setBytes(parameterIndex, x);

    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setCharacterStream(parameterIndex, reader, length);

    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.getUnderlyingPreparedStatement().setClob(parameterIndex, x);

    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.getUnderlyingPreparedStatement().setDate(parameterIndex, x);

    }

    public void setDate(int parameterIndex, Date x, Calendar cal)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setDate(parameterIndex, x, cal);

    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.getUnderlyingPreparedStatement().setDouble(parameterIndex, x);

    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.getUnderlyingPreparedStatement().setFloat(parameterIndex, x);

    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        this.getUnderlyingPreparedStatement().setInt(parameterIndex, x);

    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        this.getUnderlyingPreparedStatement().setLong(parameterIndex, x);

    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.getUnderlyingPreparedStatement().setNull(parameterIndex, sqlType);

    }

    public void setNull(int parameterIndex, int sqlType, String typeName)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setNull(parameterIndex, sqlType, typeName);

    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.getUnderlyingPreparedStatement().setObject(parameterIndex, x);

    }

    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setObject(parameterIndex, x, targetSqlType);

    }

    public void setRef(int parameterIndex, Ref x) throws SQLException {
        this.getUnderlyingPreparedStatement().setRef(parameterIndex, x);

    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        this.getUnderlyingPreparedStatement().setShort(parameterIndex, x);

    }

    public void setString(int parameterIndex, String x) throws SQLException {
        this.getUnderlyingPreparedStatement().setString(parameterIndex, x);

    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.getUnderlyingPreparedStatement().setTime(parameterIndex, x);

    }

    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setTime(parameterIndex, x, cal);

    }

    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setTimestamp(parameterIndex, x);

    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setTimestamp(parameterIndex, x, cal);

    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.getUnderlyingPreparedStatement().setURL(parameterIndex, x);

    }

    @SuppressWarnings({ "deprecation" })
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setUnicodeStream(parameterIndex, x, length);

    }
    
    public void setObject(int parameterIndex, Object x, int targetSqlType,
            int scaleOrLength) throws SQLException {
        this.getUnderlyingPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);

    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.SqlContext#getSqlText()
     */
    public String getSqlText() {
        return sqlText;
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.SqlContext#setSqlText(java.lang.String)
     */
    public void setSqlText(String sqlText) {
        Validate.notEmpty(sqlText, "Null or blank sqlText not allowed.");
        this.sqlText = sqlText;        
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.SqlContext#getExecutionStack()
     */
    public StackTraceElement[] getExecutionStack() {
        return executionStack;
    }

    /* (non-Javadoc)
     * @see net.admin4j.jdbc.driver.sql.SqlContext#setExecutionStack(java.lang.StackTraceElement[])
     */
    public void setExecutionStack(StackTraceElement[] stack) {
        this.executionStack = stack;
        
    }

}
