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
import java.sql.SQLException;

/**
 * Wraps a JDBC V4.1 callable statement so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
public abstract class CallableStatementWrapper41Base extends
		CallableStatementWrapper40Base {

	public CallableStatementWrapper41Base(ConnectionWrapper30Base connection,
			CallableStatement statement) {
		super(connection, statement);
	}
	
	public <T> T getObject(int parameterIndex, Class<T> type)
	            throws SQLException {
        return this.getUnderlyingCallableStatement().getObject(parameterIndex, type);
    }

    public <T> T getObject(String parameterName, Class<T> type)
            throws SQLException {
        return this.getUnderlyingCallableStatement().getObject(parameterName, type);
    }

    public void closeOnCompletion() throws SQLException {
        this.getUnderlyingCallableStatement().closeOnCompletion();

    }

    public boolean isCloseOnCompletion() throws SQLException {
        return this.getUnderlyingCallableStatement().isCloseOnCompletion();
    }

}
