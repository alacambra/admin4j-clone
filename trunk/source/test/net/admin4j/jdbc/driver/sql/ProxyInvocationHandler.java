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

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import net.admin4j.util.Admin4jRuntimeException;

public class ProxyInvocationHandler implements InvocationHandler {
    
    private Object returnObject = null;
    private Method calledMethod = null;
    private Object[] calledArguments = null;
    
    public ProxyInvocationHandler() {}
    public ProxyInvocationHandler(Method method, Object[] args) {
        this(method, args, null);
    }
    public ProxyInvocationHandler(Method method, Object[] args, Object returnObj) {
        this.setCalledMethod(method);
        this.setCalledArguments(args);
        this.setReturnObject(returnObj);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        
        if (method == null || calledMethod == null) {
            return returnObject;
        }
        
        if ( !method.getName().equals(this.calledMethod.getName())) {
            throw new Admin4jRuntimeException("Method names not equal")
                .addContextValue("executed Method Name", method.getName())
                .addContextValue("expected Method Name", calledMethod.getName());
        }
        
        if (args == null && calledArguments == null) {
           // No Op
        }
        else if (args != null && args.length > 0 && calledArguments == null) {
            throw new Admin4jRuntimeException("Arguments not the same length")
            .addContextValue("executed arg length", args.length)
            .addContextValue("expected arg length", "null");
        }
        else if (args == null && calledArguments != null && calledArguments.length > 0) {
            throw new Admin4jRuntimeException("Arguments not the same length")
            .addContextValue("executed arg length", "null")
            .addContextValue("expected arg length", calledArguments.length);
        }
        else if (args != null && calledArguments != null && args.length != calledArguments.length) {
            throw new Admin4jRuntimeException("Arguments not the same length")
            .addContextValue("executed arg length", args.length)
            .addContextValue("expected arg length", calledArguments.length);
        }
        else if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null && calledArguments[i] == null) {
                    // No Op
                }
                else if (args[i] != null && calledArguments[i] == null) {
                    throw new Admin4jRuntimeException("Arguments not the same values")
                    .addContextValue("offset", i)
                    .addContextValue("arg value", args[i])
                    .addContextValue("expected arg value", calledArguments[i]);
                }
                else if (args[i] == null && calledArguments[i] != null) {
                    throw new Admin4jRuntimeException("Arguments not the same values")
                    .addContextValue("offset", i)
                    .addContextValue("arg value", args[i])
                    .addContextValue("expected arg value", calledArguments[i]);
                }
                else if ( !(args[i] instanceof Proxy) && !args[i].equals(calledArguments[i])) {
                    throw new Admin4jRuntimeException("Arguments not the same values")
                    .addContextValue("offset", i)
                    .addContextValue("arg value", args[i])
                    .addContextValue("expected arg value", calledArguments[i]);
                }
            }
        }

        return returnObject;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Method getCalledMethod() {
        return calledMethod;
    }

    public void setCalledMethod(Method calledMethod) {
        this.calledMethod = calledMethod;
    }

    public Object[] getCalledArguments() {
        return calledArguments;
    }

    public void setCalledArguments(Object[] calledArguments) {
        this.calledArguments = calledArguments;
    }
    
    public static Object generateProxyValue(Class klass) {
        if (klass.getName().equals("void")) {
            return null;
        }
        else if( klass.isInterface() ) {
            return Proxy.newProxyInstance(
                    ProxyInvocationHandler.class.getClassLoader(), 
                    new Class[]{klass}, new ProxyInvocationHandler());
        }
        else if (klass.getName().equals("boolean")) {
            return Boolean.FALSE;
        }
        else if (klass.getName().equals("long")) {
            return Long.valueOf(0);
        }
        else if (klass.getName().equals("int")) {
            return Integer.valueOf(0);
        }
        else if (klass.getName().equals("double")) {
            return Double.valueOf(0);
        }
        else if (klass.getName().equals("byte")) {
            return Byte.valueOf("1");
        }
        else if (klass.getName().equals("float")) {
            return Float.valueOf("1");
        }
        else if (klass.getName().equals("short")) {
            return Short.valueOf("1");
        }
        else if (klass.getName().equals("java.sql.Timestamp")) {
            return new Timestamp(System.currentTimeMillis());
        }
        else if (klass.getName().equals("java.sql.Time")) {
            return new Time(System.currentTimeMillis());
        }
        else if (klass.getName().equals("java.sql.Date")) {
            return new java.sql.Date(System.currentTimeMillis());
        }
        else if (klass.getName().equals("java.util.Calendar")) {
            return new GregorianCalendar();
        }
        else if (klass.getName().equals("java.net.URL")) {
            try {
                return new URL("http://www.oracle.com");
            } catch (MalformedURLException e) {
                throw new Admin4jRuntimeException(e);
            }
        }
        else if (klass.getName().equals("java.io.InputStream")) {
            return new ByteArrayInputStream("foo".getBytes());
        }
        else if (klass.getName().equals("java.math.BigDecimal")) {
            return BigDecimal.ONE;
        }
        else if (klass.getName().equals("java.io.Reader")) {
            return new StringReader("foo");
        }
        else if (klass.isArray()) {
            return Array.newInstance(klass.getComponentType(), 0);
        }
        else if (String.class.equals(klass)) {
            return "Foo";
        }
        else if (Class.class.equals(klass)) {
            return klass;
        }
        else {
            try {return klass.newInstance();}
            catch (Exception e) {
                throw new Admin4jRuntimeException(e).addContextValue("class", klass.getName());
            }
        }
    }

}
