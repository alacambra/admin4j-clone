<%@ page session="false"%>
<jsp:directive.page import="org.slf4j.LoggerFactory, org.slf4j.Logger, java.sql.SQLException"/>
<html>
<h1>Admin4J Sample Exception Generation Page</h1>

<% 
Logger logger = LoggerFactory.getLogger(this.getClass());

for (int i = 0; i < 50; i++) {
	logger.error("Sample null pointer", new NullPointerException());
}

for (int i = 0; i < 40; i++) {
	logger.error("Sample SQL Exception", new SQLException("Sample SQL Exception"));
}

for (int i = 0; i < 10; i++) {
	logger.error("Sample class cast exception", new ClassCastException("org.jmu.NonExistentClass"));
}

for (int i = 0; i < 3; i++) {
	logger.error("Sample null pointer", new NullPointerException());
}
%>

<p>Exceptions have been generated.  Go <a href="admin4j/index/exception">browse</a> them.</p>

</html>