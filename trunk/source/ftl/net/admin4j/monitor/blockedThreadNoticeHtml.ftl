<html>
<head>
<#include "messageStyle.ftl">
</head>
<body>
<#assign title = "${host} - Blocked Threads Detected">
<#include "messageHeader.ftl">  

<table>
	<tr>
		<td><b>Current Datetime:</b></td>
		<td><code>${currentDatetime?datetime}</code></td>
	</tr>
</table>

<p><h3>The following threads are currently locking resources need by other threads:</h3></p>
<p>
<#list holdingLockSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceHtml.ftl">
</#list>
</p>

<p><h3>The following threads are currently waiting on Locked resources:</h3></p>
<p>
<#list waitingForLockSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceHtml.ftl">
</#list>
</p>

<p><h3>The following threads are have been blocked for two intervals:</h3></p>
<p>
<#list alsoBlockedLastIntervalSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceHtml.ftl">
</#list>
</p>

</body>
</html>