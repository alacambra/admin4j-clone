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
package net.admin4j.util.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Lists packages which contain classes that can be referenced.  This is needed by JDBC drivers and other
 * types of classes which need to limit dependencies.
 * @author D. Ashmore
 * @since 1.0
 */
@Retention(RetentionPolicy.CLASS)
public @interface PackageRestrictions {
    String[] value();
}
