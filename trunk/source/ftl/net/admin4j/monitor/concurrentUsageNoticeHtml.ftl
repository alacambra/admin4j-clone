<html>
<head>
<#include "messageStyle.ftl">
</head>
<body>
<#assign title = "${host} - ${titleArg}">
<#include "messageHeader.ftl">  
<table>
	<tr>
		<td><b>Current Usage Level:</b></td>
		<td><code>${currentLevel}</code></td>
	</tr>
	<tr>
		<td><b>Current Datetime:</b></td>
		<td><code>${currentDatetime?datetime}</code></td>
	</tr>
</table>

<p>
<#list threadInfoArray as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceHtml.ftl">
</#list>
</p>
</body>
</html>