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
package net.admin4j.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Generic host utilities.
 * @author D. Ashmore
 * @since 1.0
 */
public class HostUtils {

	public static String deriveServerName(String host) {
		if (host.indexOf(".") > 0)  return host.substring(0, host.indexOf("."));
		else return host;
	}

	public static String getHostName() {
		try {
			String host = InetAddress.getLocalHost().getHostName();
			return deriveServerName(host);
		} catch (UnknownHostException e) {
			throw new Admin4jRuntimeException(e);
		}
	}

}
