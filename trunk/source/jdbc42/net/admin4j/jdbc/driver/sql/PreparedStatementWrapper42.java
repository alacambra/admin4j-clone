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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;

import net.admin4j.jdbc.driver.SqlStatementTimerFactory;
import net.admin4j.timer.TaskTimer;
import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.ProductDependencies;
import net.admin4j.util.annotate.Product;

/**
 * Wraps a JDBC V4.2 statement so specifics can be reported to administrators.
 * @author D. Ashmore
 *
 */
@ProductDependencies( {Product.JDBC42} )
@PackageRestrictions({"net.admin4j","java","javax"})
public class PreparedStatementWrapper42 extends PreparedStatementWrapper41Base {
    
    public PreparedStatementWrapper42(ConnectionWrapper30Base connection, PreparedStatement statement) {
        super(connection, statement);
    }
    
	public void setObject(int parameterIndex, Object x, SQLType targetSqlType,
	        int scaleOrLength) throws SQLException {
		this.getUnderlyingPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}
	
	public void setObject(int parameterIndex, Object x, SQLType targetSqlType)
		      throws SQLException {
		this.getUnderlyingPreparedStatement().setObject(parameterIndex, x, targetSqlType);
    }

	public long executeLargeUpdate() throws SQLException {
		return this.getUnderlyingPreparedStatement().executeLargeUpdate();
	}
	
	public long getLargeUpdateCount() throws SQLException {
    	return this.getUnderlyingPreparedStatement().getLargeUpdateCount();
	}
    
    public void setLargeMaxRows(long max) throws SQLException {
    	this.getUnderlyingPreparedStatement().setLargeMaxRows(max);
	}
    
	public long getLargeMaxRows() throws SQLException {
		return this.getUnderlyingPreparedStatement().getLargeMaxRows();
	}
	
	public long[] executeLargeBatch() throws SQLException {
		return this.getUnderlyingPreparedStatement().executeLargeBatch();
	}
	
	public long executeLargeUpdate(String sql) throws SQLException {		
		TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(sql, 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getConnectionWrapper().registerSqlStatement(sql));
            return this.getUnderlyingPreparedStatement().executeLargeUpdate(sql);
        }
        finally {
            if (timer != null) timer.stop();
        }
	}
	
	public long executeLargeUpdate(String sql, int autoGeneratedKeys)
	        throws SQLException {		
		TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(sql, 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getConnectionWrapper().registerSqlStatement(sql));
            return this.getUnderlyingPreparedStatement().executeLargeUpdate(sql, autoGeneratedKeys);
        }
        finally {
            if (timer != null) timer.stop();
        }
	}
	
	public long executeLargeUpdate(String sql, int columnIndexes[]) throws SQLException {	
		TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(sql, 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getConnectionWrapper().registerSqlStatement(sql));
            return this.getUnderlyingPreparedStatement().executeLargeUpdate(sql, columnIndexes);
        }
        finally {
            if (timer != null) timer.stop();
        }
	}
	
	public long executeLargeUpdate(String sql, String columnNames[])
	        throws SQLException {	
		TaskTimer timer = null;
        try {
            timer = SqlStatementTimerFactory.start(sql, 
                    this.getConnectionWrapper().getDriverContext(),
                    this.getConnectionWrapper().registerSqlStatement(sql));
            return this.getUnderlyingPreparedStatement().executeLargeUpdate(sql, columnNames);
        }
        finally {
            if (timer != null) timer.stop();
        }
	}
    
}