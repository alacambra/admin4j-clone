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

import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.ProductDependencies;
import net.admin4j.util.annotate.Product;

/**
 * Wraps a JDBC V2.0 statement so specifics can be reported to administrators.
 * @author D. Ashmore
 *
 */
@ProductDependencies( {Product.JDBC40} )
@PackageRestrictions({"net.admin4j","java","javax"})
public class PreparedStatementWrapper40 extends PreparedStatementWrapper40Base {
    
    public PreparedStatementWrapper40(ConnectionWrapper30Base connection, PreparedStatement statement) {
        super(connection, statement);
    }
    
}