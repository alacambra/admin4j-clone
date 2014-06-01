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
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;

import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.Product;
import net.admin4j.util.annotate.ProductDependencies;

/**
 * Wraps a JDBC V2.0 prepared statement so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
@ProductDependencies( {Product.JDBC40} )
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class PreparedStatementWrapper40Base extends PreparedStatementWrapper30Base  {

    public PreparedStatementWrapper40Base(ConnectionWrapper30Base connection,
            PreparedStatement statement) {
        super(connection, statement);
    }
    
    protected PreparedStatement getUnderlyingPreparedStatement() {
        return (PreparedStatement)this.getUnderlyingStatement();
    }
    
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.getUnderlyingPreparedStatement().setRowId(parameterIndex, x);
    }
    
    public void setNString(int parameterIndex, String value)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setNString(parameterIndex, value);   
    }

    public void setNCharacterStream(int parameterIndex, Reader value,
            long length) throws SQLException {
        this.getUnderlyingPreparedStatement().setNCharacterStream(parameterIndex, value, length);

    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.getUnderlyingPreparedStatement().setNClob(parameterIndex, value);

    }
    
    public void setClob(int parameterIndex, Reader reader, long length)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setClob(parameterIndex, reader, length);
    
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setBlob(parameterIndex, inputStream, length);
    
    }
    
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        this.getUnderlyingPreparedStatement().setNClob(parameterIndex, reader, length);
        
    }
    
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setSQLXML(parameterIndex, xmlObject);
    
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType,
            int scaleOrLength) throws SQLException {
        this.getUnderlyingPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);

    }
    
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setAsciiStream(parameterIndex, x, length);
    
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setBinaryStream(parameterIndex, x, length);
    
    }
    
    public void setCharacterStream(int parameterIndex, Reader reader,
            long length) throws SQLException {
        this.getUnderlyingPreparedStatement().setCharacterStream(parameterIndex, reader, length);

    }

    public void setAsciiStream(int parameterIndex, InputStream x)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setAsciiStream(parameterIndex, x);
    
    }
    
    public void setBinaryStream(int parameterIndex, InputStream x)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setBinaryStream(parameterIndex, x);
    
    }

    public void setCharacterStream(int parameterIndex, Reader reader)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setCharacterStream(parameterIndex, reader);
    
    }
    
    public void setNCharacterStream(int parameterIndex, Reader value)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setNCharacterStream(parameterIndex, value);
    
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.getUnderlyingPreparedStatement().setClob(parameterIndex, reader);

    }
    
    public void setBlob(int parameterIndex, InputStream inputStream)
        throws SQLException {
        this.getUnderlyingPreparedStatement().setBlob(parameterIndex, inputStream);
    
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.getUnderlyingPreparedStatement().setNClob(parameterIndex, reader);

    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isClosed()
     */
    public boolean isClosed() throws SQLException {
        return this.getUnderlyingPreparedStatement().isClosed();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setPoolable(boolean)
     */
    public void setPoolable(boolean poolable) throws SQLException {
        this.getUnderlyingPreparedStatement().setPoolable(poolable);
        
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isPoolable()
     */
    public boolean isPoolable() throws SQLException {
        return this.getUnderlyingPreparedStatement().isPoolable();
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getUnderlyingPreparedStatement().isWrapperFor(iface);
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.getUnderlyingPreparedStatement().unwrap(iface);
    }


}
