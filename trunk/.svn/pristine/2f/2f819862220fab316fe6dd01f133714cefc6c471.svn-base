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

import net.admin4j.util.annotate.PackageRestrictions;
import net.admin4j.util.annotate.ProductDependencies;
import net.admin4j.util.annotate.Product;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Wraps a JDBC V4.0 driver so specifics can be reported to administrators.
 * For install instructions, see Admin4jJdbcDriverJdk5Base.
 * @author D. Ashmore
 * @see Admin4jJdbcDriverJdk5Base
 */
@ProductDependencies( {Product.JDBC40} )
@PackageRestrictions({"net.admin4j","java","javax"})
public class Admin4jJdbcDriverJdk5 extends Admin4jJdbcDriverJdk5Base {
    
    static {
        try {
            // Register this with the DriverManager
            DriverManager.registerDriver(new Admin4jJdbcDriverJdk5());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}