<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "Log Level Management">
		<#include "pageHeadTagContent.ftl">
		<script type="text/javascript">
		function changeLogging(loggerType, loggerName, changeType)
		{
			document.getElementById('loggerType').value = loggerType;
			document.getElementById('loggerName').value = loggerName;
			document.getElementById('changeType').value = changeType;
			document.getElementById('logLevelForm').submit();
		}
		</script>
	</head>
	<body>
		<#include "pageHeader.ftl">
		<div class="text">
			Note: Changes made on this page are for the current runtime and are NOT persisted.<br/>
			Logging will return to the originally configured levels after a restart.<br/>
		</div>
		<code>[<a href="#" onclick="changeLogging('','','')">Refresh<a>]</code><br/>		
		
		<b><blockquote><pre>${message}</pre></blockquote></b><br/>
		
		<form id="logLevelForm" method="post">
			<input type="hidden" id="loggerType" name="loggerType" value=""/>
			<input type="hidden" id="loggerName" name="loggerName" value=""/>
			<input type="hidden" id="changeType" name="changeType" value=""/>
		</form>

		<table cellspacing="0" cellpadding="0">
		  <#list loggerList as logProduct>
			<tr>
				<td colspan="3" class="textBold">
					${logProduct.loggingProductName} Loggers
				</td>
			</tr>
			<tr>
				<td class="textBold">Logger Name</td>
				<td class="textBold" style="text-align:center;">Current Level</td>
				<td class="textBold" style="text-align:center;">Actions</td>
			</tr>
			<#list logProduct.loggerList as logger>
				<tr>
					<td class="text">${logger.loggerName}</td>
					<td class="text" style="background-color:#eeeeee;">&nbsp;${logger.level}</td>
					<td class="text">&nbsp;
						[<a href="#" onclick="changeLogging('${logger.type}', '${logger.loggerName}','showMore')">show more<a>
						| <a href="#" onclick="changeLogging('${logger.type}', '${logger.loggerName}','showLess')">show less<a>
						| <a href="#" onclick="changeLogging('${logger.type}', '${logger.loggerName}','clear')">clear<a>]
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="3">
					&nbsp;
				</td>
			</tr>
		  </#list>
		</table>			

		<#include "pageFooter.ftl">
	</body>
</html>