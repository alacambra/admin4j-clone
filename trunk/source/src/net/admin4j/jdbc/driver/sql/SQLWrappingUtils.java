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

import java.lang.reflect.Constructor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.admin4j.deps.commons.lang3.JavaVersion;
import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.jdbc.driver.DriverContext;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.annotate.PackageRestrictions;

/**
 * Will wrap JDBC objects with the appropriate wrapper for tracking and reporting purposes.
 * @author D. Ashmore
 * @since 1.0
 */
@PackageRestrictions({"net.admin4j","java","javax"})
public class SQLWrappingUtils {
    
 // Only used as Commons Lang is an unwanted dependancy for JDBC drivers.
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    private static Map<Class, Constructor> constructorMap = new ConcurrentHashMap<Class, Constructor>();
    
    public static Object wrap(Object sqlObject) {
        return wrap(sqlObject, null, null, null);
    }
    
    public static Object wrap(Object sqlObject, Connection connection) {
        return wrap(sqlObject, connection, null, null);
    }
    
    @SuppressWarnings("rawtypes")
    public static Object wrap(Object sqlObject, Connection connection, String sqlText, StackTraceElement[] stack) {
        Constructor wrapperConstructor = null;
        Object jdbcObject = null;
        try {
            wrapperConstructor = findconstructor(sqlObject);
            
            if (wrapperConstructor != null && wrapperConstructor.getParameterTypes().length == 1) {           
                jdbcObject = wrapperConstructor.newInstance(sqlObject);                
                encodeSqlText(sqlText, stack, jdbcObject);                
                return jdbcObject;
            }
            else if (wrapperConstructor != null && wrapperConstructor.getParameterTypes().length == 2) {           
                jdbcObject = wrapperConstructor.newInstance(connection, sqlObject);
                encodeSqlText(sqlText, stack, jdbcObject);
                return jdbcObject;
            }
        }
        catch (Admin4jRuntimeException are) {
            throw are.addContextValue("className", sqlObject.getClass().getName());
        }
        catch (Exception e) {
            throw new Admin4jRuntimeException("Error wrapping JDBC object", e)
            .addContextValue("className", sqlObject.getClass().getName());
        } 
        
        throw new Admin4jRuntimeException("Error wrapping JDBC object")
        .addContextValue("className", sqlObject.getClass().getName());
    }

    private static Constructor findconstructor(Object sqlObject)
            throws ClassNotFoundException, NoSuchMethodException {
        Constructor wrapperConstructor = constructorMap.get(sqlObject.getClass());
        if (wrapperConstructor != null) {
            return wrapperConstructor;
        }
        
        if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
            wrapperConstructor = findWrapperConstructor18(sqlObject);
        }
        else if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_7)) {
            wrapperConstructor = findWrapperConstructor17(sqlObject);
        }
        else if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_6)) {
            wrapperConstructor = findWrapperConstructor16(sqlObject);
        }
        else {
            wrapperConstructor = findWrapperConstructor15(sqlObject);
        }
        constructorMap.put(sqlObject.getClass(), wrapperConstructor);
        return wrapperConstructor;
    }

    private static void encodeSqlText(String sqlText, StackTraceElement[] stack, Object jdbcObject) {
        if (sqlText != null && jdbcObject instanceof SqlContext) {
            ((SqlContext)jdbcObject).setSqlText(sqlText);
            ((SqlContext)jdbcObject).setExecutionStack(stack);
        }
    }
    
    public static void encodeDriverContext(Object jdbcObject, String driverClassName, String poolName, Integer driverMajorVersion, Integer driverMinorVersion) {
        if (jdbcObject instanceof DriverContext) {
            ((DriverContext)jdbcObject).setDriverClassname(driverClassName);
            ((DriverContext)jdbcObject).setDriverMajorVersion(driverMajorVersion);
            ((DriverContext)jdbcObject).setDriverMinorVersion(driverMinorVersion);
            ((DriverContext)jdbcObject).setPoolName(poolName);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Constructor findWrapperConstructor18(Object sqlObject) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Class wrapperClass = null;
        if (sqlObject instanceof Connection) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.ConnectionWrapper42");
            return wrapperClass.getConstructor(Connection.class);
        }
        else if (sqlObject instanceof CallableStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.CallableStatementWrapper42");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, CallableStatement.class);
        }
        else if (sqlObject instanceof PreparedStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.PreparedStatementWrapper42");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, PreparedStatement.class);
        }
        else if (sqlObject instanceof Statement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.StatementWrapper42");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, Statement.class);
        }
        else {
            throw new Admin4jRuntimeException("Error wrapping JDBC object")
            .addContextValue("className", sqlObject.getClass().getName());
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Constructor findWrapperConstructor17(Object sqlObject) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Class wrapperClass = null;
        if (sqlObject instanceof Connection) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.ConnectionWrapper41");
            return wrapperClass.getConstructor(Connection.class);
        }
        else if (sqlObject instanceof CallableStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.CallableStatementWrapper41");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, CallableStatement.class);
        }
        else if (sqlObject instanceof PreparedStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.PreparedStatementWrapper41");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, PreparedStatement.class);
        }
        else if (sqlObject instanceof Statement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.StatementWrapper41");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, Statement.class);
        }
        else {
            throw new Admin4jRuntimeException("Error wrapping JDBC object")
            .addContextValue("className", sqlObject.getClass().getName());
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Constructor findWrapperConstructor16(Object sqlObject) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Class wrapperClass = null;
        if (sqlObject instanceof Connection) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.ConnectionWrapper40");
            return wrapperClass.getConstructor(Connection.class);
        }
        else if (sqlObject instanceof CallableStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.CallableStatementWrapper40");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, CallableStatement.class);
        }
        else if (sqlObject instanceof PreparedStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.PreparedStatementWrapper40");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, PreparedStatement.class);
        }
        else if (sqlObject instanceof Statement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.StatementWrapper40");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, Statement.class);
        }
        else {
            throw new Admin4jRuntimeException("Error wrapping JDBC object")
            .addContextValue("className", sqlObject.getClass().getName());
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Constructor findWrapperConstructor15(Object sqlObject) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        Class wrapperClass = null;
        if (sqlObject instanceof Connection) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.ConnectionWrapper30");
            return wrapperClass.getConstructor(Connection.class);
        }
        else if (sqlObject instanceof CallableStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.CallableStatementWrapper30");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, CallableStatement.class);
        }
        else if (sqlObject instanceof PreparedStatement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.PreparedStatementWrapper30");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, PreparedStatement.class);
        }
        else if (sqlObject instanceof Statement) {
            wrapperClass = Class.forName("net.admin4j.jdbc.driver.sql.StatementWrapper30");
            return wrapperClass.getConstructor(ConnectionWrapper30Base.class, Statement.class);
        }
        else {
            throw new Admin4jRuntimeException("Error wrapping JDBC object")
            .addContextValue("className", sqlObject.getClass().getName());
        }
    }

    /**
     * Only used as Commons Lang is an unwanted dependancy for JDBC drivers.
     * @param objArray
     * @return
     */
    protected static String array2String(Object[] objArray) {
        StringBuffer buffer = new StringBuffer();
        for (Object obj: objArray) {
            buffer.append(obj.toString());
            buffer.append(SystemUtils.LINE_SEPARATOR);
        }
        return buffer.toString();
    }
    
    // Only used as Commons Lang is an unwanted dependancy for JDBC drivers.
    protected static void notNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
