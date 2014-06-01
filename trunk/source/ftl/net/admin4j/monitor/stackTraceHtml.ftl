<code>
"${threadInfo.threadName}" Id=${threadInfo.threadId} ${threadInfo.threadState}
<#if (threadInfo.lockName?length > 1)>
 on ${threadInfo.lockName}
</#if>
<#if (threadInfo.lockOwnerName?length > 1)>
 owned by ${threadInfo.lockOwnerName}
</#if>
<#if (threadInfo.suspended)>
 (suspended)
</#if>
<#if (threadInfo.inNative)>
 (native)
</#if>
<#if (threadInfo.lockInfo?is_method)>
 locks ${threadInfo.lockInfo}
</#if>
<br />
<#list threadInfo.stackTrace as traceLine>
  at ${traceLine}<br />
</#list>
<#if (threadInfo.lockedMonitors?is_method) && (threadInfo.lockedMonitors?length > 0)>
Monitors are:<br />
 <#list threadInfo.lockedMonitors as monitor>
 ${monitor}
 </#list>
</#if>
<#if (threadInfo.lockedSynchronizers?is_method) && (threadInfo.lockedSynchronizers?length > 0)>
Synchronizers are:<br />
 <#list threadInfo.lockedSynchronizers as syncer>
 ${syncer}
 </#list>
</#if>
</code>
<br />