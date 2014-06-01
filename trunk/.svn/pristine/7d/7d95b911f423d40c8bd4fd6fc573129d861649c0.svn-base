<html>
<head>
<#include "messageStyle.ftl">
<title>Admin4J Web Application Performance Notification</title>
</head>
<body>
<#assign title = "Web Application Performance Notification">
<#include "messageHeader.ftl">  
<table summary="Error Summary">
	<tr>
		<td><b>Host:</b></td>
		<td><code>${host}</code></td>
	</tr>
	<tr>
		<td><b>Request URI:</b></td>
		<td><code>${request.requestURI}</code></td>
	</tr>
	<tr>
		<td><b>Request Execution Time (seconds):</b></td>
		<td><code>${timeInSeconds}</code></td>
	</tr>
</table>

<div style="font-size: 10px">
	<table summary="Error Detail Links">
		<tr>
			<td><a href="#Session">Session Attributes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><a href="#Request">Request Parameters</a></td></tr>
	</table>
</div>

<hr />
<a name="Session" /><h2>Session Attributes</h2>
<table border="1" summary="Session Attributes">
	<tr>
		<th>Attribute</th>
		<th>Value</th>
	</tr>
	
	<#list sessionAttributeList as attribute>
	<tr>
		<td>${attribute.name}</td>
		<td><pre>${attribute.value}</pre></td>
	</tr>
	</#list>
</table>

<hr />
<a name="Request" /><h2>Request Attributes</h2>
<table border="1" summary="Request Attributes">
	<tr>
		<th>Attribute</th>
		<th>Value</th>
	</tr>
	
	<#list requestAttributeList as attribute>
	<tr>
		<td>${attribute.name}</td>
		<td><pre>${attribute.value}</pre></td>
	</tr>
	</#list>
</table>
</body>
</html>