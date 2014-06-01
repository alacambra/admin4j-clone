${host} - Blocked Threads Detected.

Current Datetime:               ${currentDatetime?datetime}

The following threads are currently locking resources need by other threads:
<#list holdingLockSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceText.ftl">
</#list>

The following threads are currently waiting on Locked resources:
<#list waitingForLockSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceText.ftl">
</#list>

The following threads are have been blocked for two intervals:
<#list alsoBlockedLastIntervalSet as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceText.ftl">
</#list>