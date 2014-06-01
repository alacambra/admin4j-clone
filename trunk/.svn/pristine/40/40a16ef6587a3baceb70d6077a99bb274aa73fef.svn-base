<html>
<head>
<#include "messageStyle.ftl">
<title>Admin4J Error Notification</title>
</head>
<body>
<#assign title = "Web Application Error">
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
		<td><b>Error Type:</b></td>
		<td><code>${errorType}</code></td>
	</tr>
	<tr>
		<td><b>Error Message:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		<td><code>${GuiUtils.htmlEscape(errorMessage)}</code></td>
	</tr>	
	<#if (suppressionIntervalMillis > 0)>
	<tr>
		<td><b>Suppression:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
		<td><i>Notification for like errors occurring within the next ${suppressionIntervalMillis} ms. will be suppressed.</i></td>
	</tr>
	</#if>
</table>

<div style="font-size: 10px">
	<table summary="Table of Contents">
		<tr>
			<td><a href="#RootCause">Root Cause</a></td>
			<td><a href="#ReportedException">Reported Exception</a></td>
		</tr>
		<tr>
			<td><a href="#Session">Session Attributes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><a href="#Request">Request Parameters</a></td>
		</tr>
		
		<tr>
			<td><a href="#RequestHistory">Request History</a></td>
			<td />
		</tr>
	</table>
</div>

<hr />
<a name="RootCause" /><h2>RootCause</h2>
<p><code>
${rootCauseTrace}
</code></p>

<hr />
<a name="ReportedException" /><h2>ReportedException</h2>
<p><code>
${reportedCauseTrace}
</code></p>

<#if requestHistory?has_content>
<hr />
<a name="RequestHistory" /><h2>Request History (Chronological Order)</h2>
<table border="1" summary="Request Parameters">
	<tr>
		<th>Request</th>
		<th>Request Parameters (Parameter/Value)</th>
	</tr>
<#list requestHistory as historyItem>
	<tr>
		<td>${historyItem.uri}</td>
		<td>
<#if historyItem.requestParameters?has_content && historyItem.requestParameters?size gt 0>
			<table border="0" summary="Parameters" style="width:100%;border: 1px solid black">
<#list historyItem.requestParameters as requestParm>
				<tr>
					<td>${requestParm.name}</td>
					<td>${requestParm.value}</td>
      			</tr>
</#list>
			</table>
</#if>
		</td>
	</tr>
</#list>
</table>
</#if>

<#if sessionAttributeList?has_content && sessionAttributeList?size gt 0>
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
</#if>

<#if requestAttributeList?has_content && requestAttributeList?size gt 0>
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
</#if>

</body>
</html>