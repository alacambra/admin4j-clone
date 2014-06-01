<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "Exception Statistics">
		<#include "pageHeadTagContent.ftl">
		
		<#include "rollOverRowJS.ftl">
		
	</head>
	<body>
		<#include "pageHeader.ftl">
		
		<form id="exceptionForm" method="post">
			<span class="text"><a class="link" onclick="document.getElementById('exceptionForm').submit();">[refresh]</a></span>
			<input name="exceptionId" id="exceptionId" type="hidden" value="">
			<input name="exceptionUrl" id="exceptionUrl" type="hidden" value="">
			<input name="action" id="action" type="hidden" value="">
			</input>
		
		<#if configuration.getExceptionInformationXmlFileName() != "">
			<p>Note: Exception summary information is saved in file '${configuration.getExceptionInformationXmlFileName()}'.</p>
		<#else>
			<p>Note: Exception summary information is not persisted per your configuration and will reset on container recycle.</p>
		</#if>
		
		<#if exceptionList?size == 0>
			<p>There are no exceptions to report.</p>
		</#if>
		
		<#if (exceptionList?size > 0)>
			<table border='0' cellpadding='2' cellspacing='1' >
				<tr>
					<th>Exception</th>
					<th>Message from Last Occurrence</th>
					<th>Total Nbr of Exceptions</th>
					<th>First Reported</th>
					<th>Last Reported</th>
				</tr>
				<#assign rowClass="even">
				<#list exceptionList as exception>
					<#if rowClass?starts_with("even")>
						<#assign rowClass="odd">
					<#else>
						<#assign rowClass="even">
					</#if>

					<tr class="${rowClass}"  >
						<td title="Exception thrown"><code>${exception.exceptionClassName}</td>
						<td title="Message from last occurrence" style="width:300px;"><code>${GuiUtils.htmlEscape(exception.lastOccurrenceMessage)}</td>
						<td title="${exception.totalNbrExceptions} occurrences total"><code>${exception.totalNbrExceptions}</td>
						<td title="Datetime when this first occurred"><code>${exception.firstOccuranceDt?datetime}</td>
						<td title="Datetime when this last occurred"><code>${exception.lastOccuranceDt?datetime}</code></td>
					</tr>
					<tr class="${rowClass}" >
						<td class="textTopCentered"><a class="link" onclick="document.getElementById('exceptionId').value='${exception.id}';document.getElementById('action').value='delete';document.getElementById('exceptionForm').submit();" id="delete${exception.id}">[delete]</a>    </td>
						<td style="width:300px;"><code></code></td>
						<td colSpan="3">

								<code>
								<#list exception.stackTrace as trace>
									${trace}<br/>
								</#list>
								</code>

						</td>
					</tr>

				</#list>
			</table>
		</#if>
		
		</form>
		
		<#include "pageFooter.ftl">
	</body>
</html>