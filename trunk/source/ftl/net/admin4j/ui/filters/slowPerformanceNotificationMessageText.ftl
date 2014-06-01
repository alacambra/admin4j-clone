Web Application Performance Notification.

Host:									${host}
Request URI:							${request.requestURI}
Request Execution Time (seconds):		${timeInSeconds}

Session Attributes:
<#list sessionAttributeList as attribute>
${attribute.name}=${attribute.value}
</#list>

Request Attributes:
<#list requestAttributeList as attribute>
${attribute.name}=${attribute.value}
</#list>