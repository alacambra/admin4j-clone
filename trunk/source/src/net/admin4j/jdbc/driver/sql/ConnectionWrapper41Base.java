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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

/**
 * Wraps a JDBC V4.1 connection so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0.3
 */
public abstract class ConnectionWrapper41Base extends ConnectionWrapper40Base {

	public ConnectionWrapper41Base(Connection conn) {
		super(conn);
	}
	
	public void setSchema(String schema) throws SQLException {
        this.getConnection().setSchema(schema);

    }

    public String getSchema() throws SQLException {
        return this.getConnection().getSchema();
    }

    public void abort(Executor executor) throws SQLException {
        this.getConnection().abort(executor);

    }

    public void setNetworkTimeout(Executor executor, int milliseconds)
            throws SQLException {
        this.getConnection().setNetworkTimeout(executor, milliseconds);

    }

    public int getNetworkTimeout() throws SQLException {
        return this.getConnection().getNetworkTimeout();
    }

}
