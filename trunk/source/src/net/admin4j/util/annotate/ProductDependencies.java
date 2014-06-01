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
 * Documents product dependencies a class has.  This will be used in test cases to
 * ensure that no inappropriate class references exist.  
 * 
 * <p>For instance, only classes that list Log4J as a product dependency should be importing 
 * or directly referencing Log4J classes.</p>
 * @author D. Ashmore
 * @since 1.0
 */
@Retention(RetentionPolicy.CLASS)
public @interface ProductDependencies {
    Product[] value();
}
