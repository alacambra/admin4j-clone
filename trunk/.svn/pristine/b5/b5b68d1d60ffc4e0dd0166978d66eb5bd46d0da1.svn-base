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

import java.sql.SQLException;
import java.sql.Statement;

import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.Product;
import net.admin4j.util.annotate.ProductDependencies;

/**
 * Wraps a JDBC V3.0 statement so specifics can be reported to administrators.
 * @author D. Ashmore
 * @since 1.0
 */
@ProductDependencies( {Product.JDBC41} )
@PackageRestrictions({"net.admin4j","java","javax"})
public abstract class StatementWrapper41Base extends StatementWrapper40Base {

	public StatementWrapper41Base(ConnectionWrapper30Base connection,
			Statement statement) {
		super(connection, statement);
	}
	
	 public void closeOnCompletion() throws SQLException {
	        this.getUnderlyingStatement().closeOnCompletion();

    }

    public boolean isCloseOnCompletion() throws SQLException {
        return this.getUnderlyingStatement().isCloseOnCompletion();
    }

}
