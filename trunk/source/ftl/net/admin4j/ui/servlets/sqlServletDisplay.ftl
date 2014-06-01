<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "SQL Statement Statistics">
		<#include "pageHeadTagContent.ftl">
		<#include "rollOverRowJS.ftl">

	</head>
	<body>
		<#include "pageHeader.ftl">
				
		<form id="sqlPerformanceForm" method="post">
			<input name="Refresh" id="Refresh" type="hidden" value="">
		
		<div class="text">
			<a class="link" onclick="document.getElementById('Refresh').value='Refresh';document.getElementById('sqlPerformanceForm').submit();">[reset]</a>
		
			<br/><br/>Note: These statistics are <b>not</b> persisted and will reset on container recycle.<br/>
		</div>
		
		<#if sqlSummaryList?size == 0>
			<p>There are no performance statistics to report.</p>
		</#if>
		
		<#if (sqlSummaryList?size > 0)>
			<table border='0' cellpadding='2' cellspacing='1' >
				
				<tr>
					<th>Pool Name</th>
					<th />
					<th>SQL text</th>
					<th>Driver</th>
					<th>Nbr Executions</th>
					<th>Total (ms)</th>
					<th>Average (ms)</th>
					<th>Min (ms)</th>
					<th>Max (ms)</th>
					<th>First Obs Time</th>
					<th>Last Obs Time</th>
					
				</tr>
				
				<#assign rowClass="even">
				<#list sqlSummaryList as sqlSummary>
					<#if rowClass?starts_with("even")>
						<#assign rowClass="odd">
					<#else>
						<#assign rowClass="even">
					</#if>
					<tr class="${rowClass}" onMouseOver='rollOnRow(this, null)' onMouseOut='rollOffRow(this)'>
						<td>${sqlSummary.poolName}</td>
						<td title="Copy to clipboard"><a class="link" onClick="window.prompt ('Copy to clipboard: Ctrl+C, Enter', '${GuiUtils.javascriptEscape( sqlSummary.sqlText )}');">[copy]</a></td>
						<td title="${sqlSummary.sqlText}">${GuiUtils.abbreviate(sqlSummary.sqlText, 80)}</td>
						<td>${sqlSummary.driverClass} (${sqlSummary.majorVersion}.${sqlSummary.minorVersion})</td>
						<td title="Number of Executions"><code>${sqlSummary.summary.nbrDataItems}</td>
						<td title="Total"><code>${sqlSummary.summary.total}</td>
						<td title="Average in Millis"><code>${sqlSummary.summary.average}</td>
						<td title="Min in Millis"><code>${sqlSummary.summary.minimum}</td>
						<td title="Max in Millis"><code>${sqlSummary.summary.maximum}</td>
						<#if sqlSummary.summary.firstObservationDate?has_content >
							<td title="First Observation Time"><code>${sqlSummary.summary.firstObservationDate?datetime}</td>
						<#else>
							<td />
						</#if>
						<#if sqlSummary.summary.lastObservationDate?has_content >
							<td title="Last Observation Time"><code>${sqlSummary.summary.lastObservationDate?datetime}</td>
						</#if>
						
					</tr>
				</#list>
				
			</table>
		</#if>
		
		</form>
		<#include "pageFooter.ftl">
	</body>
</html>