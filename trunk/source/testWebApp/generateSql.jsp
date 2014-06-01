<%@ page session="false"%>
<jsp:directive.page import="org.slf4j.LoggerFactory, 
org.slf4j.Logger, 
java.util.Properties, 
java.sql.Driver, 
java.sql.Connection, 
java.sql.Statement,
java.sql.PreparedStatement,
java.sql.CallableStatement,
java.sql.ResultSet,
net.admin4j.deps.commons.lang3.SystemUtils, 
net.admin4j.deps.commons.lang3.JavaVersion"/>
<html>
<h1>Admin4J Sample SQL Statement Generation Page</h1>

<% 
Logger logger = LoggerFactory.getLogger(this.getClass());
Driver driver = null;
Connection admin4JConnection = null;

if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_7)) {
    driver = (Driver)Class.forName("net.admin4j.jdbc.driver.Admin4jJdbcDriverJdk7").newInstance();
}
else {
    driver = (Driver)Class.forName("net.admin4j.jdbc.driver.Admin4jJdbcDriverJdk5").newInstance();
}

admin4JConnection = driver.connect("jdbcx:admin4j:driver=org.hsqldb.jdbcDriver,poolName=mainPoolDB::jdbc:hsqldb:mem:Admin4JTestDb", new Properties());

Statement stmt = admin4JConnection.createStatement();
PreparedStatement pStmt;
ResultSet rSet = null;
CallableStatement cstmt = null;

logger.info("Creating table foo");
stmt.execute("create table foo (fooId integer)");
stmt.close();

logger.info("Inserting into table foo");
for (int i = 0; i < 10000; i++) {
    pStmt = admin4JConnection.prepareStatement("insert into foo values (?)");
    pStmt.setInt(1, i);
    pStmt.executeUpdate();
    admin4JConnection.commit();
    pStmt.close();
}

logger.info("selecting from table foo");
Integer tempInt;
for (int i = 0; i < 10000; i++) {
    pStmt = admin4JConnection.prepareStatement("select * from foo");
    rSet = pStmt.executeQuery();
    
    while (rSet.next()) {
        tempInt = rSet.getInt(1);
    }

    rSet.close();
    pStmt.close();
}

logger.info("Executing proc");
String tempStr;
for (int i = 0; i < 10000; i++) {
    cstmt = admin4JConnection.prepareCall("call CURRENT_USER");
    rSet = cstmt.executeQuery();
    if (rSet.next()) {
        tempStr = rSet.getString(1);
        if (tempStr == null) {
            System.out.println("Hey");
        }
    }
    
    rSet.close();
}

stmt = admin4JConnection.createStatement();
stmt.execute("drop table foo");
stmt.close();

admin4JConnection.close();

%>

<p>SQL Statements have been generated.  Go <a href="admin4j/index/sql">browse</a> them.</p>

</html>