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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConnection extends BaseJdbcTestSupport {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInterfaceProxies() throws Exception {
        Method[] connectionMethod = Connection.class.getMethods();
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        Connection proxyConnection = (Connection)Proxy.newProxyInstance(
                this.getClass().getClassLoader(), 
                new Class[]{Connection.class}, handler);
        Connection wrappedConnection = (Connection)SQLWrappingUtils.wrap(proxyConnection);
        
        testJdbcInterfaceImplementation(connectionMethod, handler,
                wrappedConnection);
        
        proxyConnection = (Connection)Proxy.newProxyInstance(
                this.getClass().getClassLoader(), 
                new Class[]{Connection.class}, new ProxyInvocationHandler());
        
        Method[] statementMethod = Statement.class.getMethods();
        Statement proxyStatement = (Statement)Proxy.newProxyInstance(
                this.getClass().getClassLoader(), 
                new Class[]{Statement.class}, handler);
        Statement wrappedStatement = (Statement)SQLWrappingUtils.wrap(proxyStatement, wrappedConnection, "select something", Thread.currentThread().getStackTrace());
        testJdbcInterfaceImplementation(statementMethod, handler,
                wrappedStatement);
        
        Method[] preparedStatementMethod = PreparedStatement.class.getMethods();
        Statement proxyPreparedStatement = (Statement)Proxy.newProxyInstance(
                this.getClass().getClassLoader(), 
                new Class[]{PreparedStatement.class}, handler);
        Statement wrappedPreparedStatement = (Statement)SQLWrappingUtils.wrap(proxyPreparedStatement, wrappedConnection, "select something", Thread.currentThread().getStackTrace());
        testJdbcInterfaceImplementation(preparedStatementMethod, handler,
                wrappedPreparedStatement);
        
        Method[] calalbleStatementMethod = CallableStatement.class.getMethods();
        Statement proxyCallableStatement = (Statement)Proxy.newProxyInstance(
                this.getClass().getClassLoader(), 
                new Class[]{CallableStatement.class}, handler);
        Statement wrappedCallableStatement = (Statement)SQLWrappingUtils.wrap(proxyCallableStatement, wrappedConnection, "select something", Thread.currentThread().getStackTrace());
        testJdbcInterfaceImplementation(calalbleStatementMethod, handler,
                wrappedCallableStatement);
    }

}
