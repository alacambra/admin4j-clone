${host} -  ${titleArg}

Current Usage Level:            			${currentLevel}
Current Datetime:            				${currentDatetime?datetime}

<#list threadInfoArray as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceText.ftl">
</#list>