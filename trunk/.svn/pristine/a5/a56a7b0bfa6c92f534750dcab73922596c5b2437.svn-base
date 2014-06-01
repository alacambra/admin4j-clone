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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.admin4j.deps.commons.lang3.JavaVersion;
import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.util.Admin4jRuntimeException;

public class BaseJdbcTestSupport {

    protected void testJdbcInterfaceImplementation(Method[] classMethod, ProxyInvocationHandler handler,
            Object wrappedJdbcObject) {
                for (Method method: classMethod) {
                	
                    handler.setCalledMethod(method);
                    handler.setReturnObject(ProxyInvocationHandler.generateProxyValue(method.getReturnType()));
                    
                    Object[] args = new Object[method.getGenericParameterTypes().length];
                    for (int i = 0; i < method.getParameterTypes().length; i++) {
                        args[i] = ProxyInvocationHandler.generateProxyValue(method.getParameterTypes()[i]);
                    }
                    handler.setCalledArguments(args);
                    
                    Object returnObj = null;
                    try {
                        if (args.length > 0) {
                            returnObj = method.invoke(wrappedJdbcObject, args);
                        }
                        else {
                            returnObj = method.invoke(wrappedJdbcObject);
                        }
                    } catch (Admin4jRuntimeException are) {
                        throw are;
                    }
                      catch (Exception e) {
                        throw new Admin4jRuntimeException(e)
                            .addContextValue("method", method.toGenericString());
                    }
                    
                    if (handler.getReturnObject() == null && returnObj == null) {
                        // No Op
                    }
                    else if ( !(handler.getReturnObject() instanceof Proxy) &&  !handler.getReturnObject().equals(returnObj)) {
                        throw new Admin4jRuntimeException("Returned objects not identical")
                            .addContextValue("method", method.toGenericString())
                            .addContextValue("received return", returnObj)
                            .addContextValue("expected return", handler.getReturnObject());
                    }

                }
            }

}
