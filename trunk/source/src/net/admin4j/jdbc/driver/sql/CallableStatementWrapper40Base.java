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
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;

import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.Product;
import net.admin4j.util.annotate.ProductDependencies;

/**
 * Wraps a JDBC V3.0 callable statement so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
@ProductDependencies( {Product.JDBC40} )
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class CallableStatementWrapper40Base extends CallableStatementWrapper30Base implements CallableStatement {

    public CallableStatementWrapper40Base(ConnectionWrapper30Base connection,
            CallableStatement statement) {
        super(connection, statement);
    }
    
    protected CallableStatement getUnderlyingCallableStatement() {
        return (CallableStatement)this.getUnderlyingStatement();
    }
    
    public NClob getNClob(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getNClob(parameterIndex);
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getNClob(parameterName);
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterName, value);

    }
    
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getSQLXML(parameterIndex);
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getSQLXML(parameterName);
    }
    
    public void setSQLXML(String parameterName, SQLXML xmlObject)
        throws SQLException {
        this.getUnderlyingCallableStatement().setSQLXML(parameterName, xmlObject);
    
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getRowId(parameterIndex);
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getRowId(parameterName);
    }
    
    public void setRowId(String parameterName, RowId x) throws SQLException {
        this.getUnderlyingCallableStatement().setRowId(parameterName, x);

    }
    
    public void setNString(String parameterName, String value)
        throws SQLException {
        this.getUnderlyingCallableStatement().setNString(parameterName, value);
    
    }

    public void setNCharacterStream(String parameterName, Reader value,
            long length) throws SQLException {
        this.getUnderlyingCallableStatement().setNCharacterStream(parameterName, value, length);

    }
    
    public void setClob(String parameterName, Reader reader, long length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setClob(parameterName, reader, length);
    
    }

    public void setBlob(String parameterName, InputStream inputStream,
            long length) throws SQLException {
        this.getUnderlyingCallableStatement().setBlob(parameterName, inputStream, length);

    }

    public void setNClob(String parameterName, Reader reader, long length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterName, reader, length);
    
    }

    public String getNString(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getNString(parameterIndex);
    }

    public String getNString(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getNString(parameterName);
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getNCharacterStream(parameterIndex);
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getNCharacterStream(parameterName);
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return this.getUnderlyingCallableStatement().getCharacterStream(parameterIndex);
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return this.getUnderlyingCallableStatement().getCharacterStream(parameterName);
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        this.getUnderlyingCallableStatement().setBlob(parameterName, x);

    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        this.getUnderlyingCallableStatement().setClob(parameterName, x);

    }

    public void setAsciiStream(String parameterName, InputStream x, long length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setAsciiStream(parameterName, x, length);
    
    }

    public void setBinaryStream(String parameterName, InputStream x, long length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setBinaryStream(parameterName, x, length);
    
    }

    public void setCharacterStream(String parameterName, Reader reader,
            long length) throws SQLException {
        this.getUnderlyingCallableStatement().setCharacterStream(parameterName, reader, length);

    }

    public void setAsciiStream(String parameterName, InputStream x)
        throws SQLException {
        this.getUnderlyingCallableStatement().setAsciiStream(parameterName, x);
    
    }
    
    public void setAsciiStream(String parameterName, InputStream x, int length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setAsciiStream(parameterName, x, length);
    
    }
    
    public void setBinaryStream(String parameterName, InputStream x)
        throws SQLException {
        this.getUnderlyingCallableStatement().setBinaryStream(parameterName, x);
    
    }
    
    public void setBinaryStream(String parameterName, InputStream x, int length)
        throws SQLException {
        this.getUnderlyingCallableStatement().setBinaryStream(parameterName, x, length);
    
    }

    public void setCharacterStream(String parameterName, Reader reader)
        throws SQLException {
        this.getUnderlyingCallableStatement().setCharacterStream(parameterName, reader);
    
    }
    
    public void setCharacterStream(String parameterName, Reader reader,
        int length) throws SQLException {
        this.getUnderlyingCallableStatement().setCharacterStream(parameterName, reader, length);
    
    }

    public void setNCharacterStream(String parameterName, Reader value)
        throws SQLException {
        this.getUnderlyingCallableStatement().setNCharacterStream(parameterName, value);
    
    }
    
    public void setNClob(String parameterName, Reader reader)
        throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterName, reader);
    
    }

    public void setClob(String parameterName, Reader reader)
        throws SQLException {
        this.getUnderlyingCallableStatement().setClob(parameterName, reader);
    
    }

    public void setBlob(String parameterName, InputStream inputStream)
        throws SQLException {
        this.getUnderlyingCallableStatement().setBlob(parameterName, inputStream);
    
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
     */
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.getUnderlyingCallableStatement().setRowId(parameterIndex, x);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
     */
    public void setNString(int parameterIndex, String value)
            throws SQLException {
        this.getUnderlyingCallableStatement().setNString(parameterIndex, value);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
     */
    public void setNCharacterStream(int parameterIndex, Reader value,
            long length) throws SQLException {
        this.getUnderlyingCallableStatement().setNCharacterStream(parameterIndex, value, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
     */
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterIndex, value);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
     */
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        this.getUnderlyingCallableStatement().setClob(parameterIndex, reader, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
     */
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        this.getUnderlyingCallableStatement().setBlob(parameterIndex, inputStream, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
     */
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterIndex, reader, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
     */
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        this.getUnderlyingCallableStatement().setSQLXML(parameterIndex, xmlObject);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
     */
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        this.getUnderlyingCallableStatement().setAsciiStream(parameterIndex, x, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
     */
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        this.getUnderlyingCallableStatement().setBinaryStream(parameterIndex, x, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
     */
    public void setCharacterStream(int parameterIndex, Reader reader,
            long length) throws SQLException {
        this.getUnderlyingCallableStatement().setCharacterStream(parameterIndex, reader, length);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
     */
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        this.getUnderlyingCallableStatement().setAsciiStream(parameterIndex, x);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
     */
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        this.getUnderlyingCallableStatement().setBinaryStream(parameterIndex, x);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
     */
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        this.getUnderlyingCallableStatement().setCharacterStream(parameterIndex, reader);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
     */
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        this.getUnderlyingCallableStatement().setNCharacterStream(parameterIndex, value);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
     */
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.getUnderlyingCallableStatement().setClob(parameterIndex, reader);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
     */
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        this.getUnderlyingCallableStatement().setBlob(parameterIndex, inputStream);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
     */
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.getUnderlyingCallableStatement().setNClob(parameterIndex, reader);
        
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isClosed()
     */
    public boolean isClosed() throws SQLException {
        return this.getUnderlyingCallableStatement().isClosed();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setPoolable(boolean)
     */
    public void setPoolable(boolean poolable) throws SQLException {
        this.getUnderlyingCallableStatement().setPoolable(poolable);
        
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isPoolable()
     */
    public boolean isPoolable() throws SQLException {
        return this.getUnderlyingCallableStatement().isPoolable();
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.getUnderlyingCallableStatement().unwrap(iface);
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getUnderlyingCallableStatement().isWrapperFor(iface);
    }


}
