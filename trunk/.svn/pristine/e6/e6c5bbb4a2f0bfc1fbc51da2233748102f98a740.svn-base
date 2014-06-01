Web Application Error.

Host:			${host}
Request URI:	${request.requestURI}
Error Type:		${errorType}
Error Message:

${errorMessage}

<#if (suppressionIntervalMillis > 0)>
Notification for like errors occurring within the next ${suppressionIntervalMillis} ms. will be suppressed.
</#if>

RootCause:
${rootCauseTrace}

ReportedException:
${reportedCauseTrace}

<#if requestHistory?has_content>
Request History:
<#list requestHistory as historyItem>
${historyItem_index} - ${historyItem.uri}
<#list historyItem.requestParameters as requestParm>
     ${requestParm.name}=${requestParm.value}
</#list>
</#list>
</#if>

Session Attributes:
<#list sessionAttributeList as attribute>
${attribute.name}=${attribute.value}
</#list>

Request Attributes:
<#list requestAttributeList as attribute>
${attribute.name}=${attribute.value}
</#list>