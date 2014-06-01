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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.jdbc.driver.sql.ConnectionWrapper30Base;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Keeps track of all active connections.
 * @author D. Ashmore
 * @since 1.0
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public class ConnectionRegistry {
	
	private static Map<ConnectionWrapper30Base, ConnectionWrapper30Base> registryMap = new ConcurrentHashMap<ConnectionWrapper30Base, ConnectionWrapper30Base>();
	
	public static void register(ConnectionWrapper30Base conn) {
		Validate.notNull(conn, "Null Connection not allowed.");
		registryMap.put(conn, conn);
	}
	
	public static void unRegister(ConnectionWrapper30Base conn) {
		Validate.notNull(conn, "Null Connection not allowed.");
		registryMap.remove(conn);
	}
	
	public static Set<ConnectionWrapper30Base> getCurrentConnectionSet() {
		return new HashSet<ConnectionWrapper30Base>(registryMap.keySet());
	}

}
