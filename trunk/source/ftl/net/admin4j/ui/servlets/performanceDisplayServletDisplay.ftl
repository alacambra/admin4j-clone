<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "Performance Statistics">
		<#include "pageHeadTagContent.ftl">
		<#include "rollOverRowJS.ftl">

	</head>
	<body>
		<#include "pageHeader.ftl">
		
		<form method="post" id="performanceForm">
			<span class="text"><a class="link" onclick="document.getElementById('performanceForm').submit();">[refresh]</a></span>
			<input name="action" id="action" type="hidden" value="">
			<input name="sortField" id="sortField" type="hidden" value="">
			<input name="perfLabel" id="perfLabel" type="hidden" value="">
		
		<#if configuration.getPerformanceInformationXmlFileName()!= "">
			<p>Note: Performance information is saved in file ${configuration.getPerformanceInformationXmlFileName()}.</p>
		<#else>
			<p>Note: Performance information is not persisted per your configuration and will reset on container recycle.</p>
		</#if>
		<#if performanceSummaryList?size == 0>
			<p>There are no performance statistics to report.</p>
		</#if>
		
		<#if (performanceSummaryList?size > 0)>
			<table border='0' cellpadding='2' cellspacing='1' >
				<tr>
					<th rowSpan="2" class="rhsBorder"></th>
					<th rowSpan="2" class="rhsBorder" title="click to sort by label ascending" onclick="document.getElementById('sortField').value='label';document.getElementById('performanceForm').submit();">Performance Measurement Label</th>
					<th colSpan="8" class="rhsBorder">All Observations</th>
					<th colSpan="8" class="rhsBorder">Last 100 Observations</th>
				</tr>
				<tr>
					<th title="click to sort by Hits descending" onclick="document.getElementById('sortField').value='summaryMeasurement.nbrDataItems';document.getElementById('performanceForm').submit();">Hits</th>
					<th title="click to sort by Average descending" onclick="document.getElementById('sortField').value='summaryMeasurement.average';document.getElementById('performanceForm').submit();">Average (ms)</th>
					<th title="click to sort by Minimum descending" onclick="document.getElementById('sortField').value='summaryMeasurement.minimum';document.getElementById('performanceForm').submit();">Min (ms)</th>
					<th title="click to sort by Maximum descending" onclick="document.getElementById('sortField').value='summaryMeasurement.maximum';document.getElementById('performanceForm').submit();">Max (ms)</th>
					<th title="click to sort by First Obs Date descending" onclick="document.getElementById('sortField').value='summaryMeasurement.firstObservationDate';document.getElementById('performanceForm').submit();">First Obs Time</th>
					<th title="click to sort by Last Obs Date descending" onclick="document.getElementById('sortField').value='summaryMeasurement.lastObservationDate';document.getElementById('performanceForm').submit();">Last Obs Time</th>
					<th title="click to sort by Total Time descending" onclick="document.getElementById('sortField').value='summaryMeasurement.total';document.getElementById('performanceForm').submit();">Total (ms)</th>
					<th title="click to sort by Standard Deviation descending" class="rhsBorder" onclick="document.getElementById('sortField').value='summaryMeasurement.standardDeviation';document.getElementById('performanceForm').submit();">Std. Dev (ms)</th>
					<th title="click to sort by Hits descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.nbrDataItems';document.getElementById('performanceForm').submit();">Hits</th>
					<th title="click to sort by Average descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.average';document.getElementById('performanceForm').submit();">Average (ms)</th>
					<th title="click to sort by Minimum descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.minimum';document.getElementById('performanceForm').submit();">Min (ms)</th>
					<th title="click to sort by Maximum descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.maximum';document.getElementById('performanceForm').submit();">Max (ms)</th>
					<th title="click to sort by First Obs Date descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.firstObservationDate';document.getElementById('performanceForm').submit();">First Obs Time</th>
					<th title="click to sort by Last Obs Date descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.lastObservationDate';document.getElementById('performanceForm').submit();">Last Obs Time</th>
					<th title="click to sort by Total Time descending" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.total';document.getElementById('performanceForm').submit();">Total (ms)</th>
					<th title="click to sort by Standard Deviation descending" class="rhsBorder" onclick="document.getElementById('sortField').value='rollingNbrObservationsMeasurement.standardDeviation';document.getElementById('performanceForm').submit();">Std. Dev (ms)</th>
				</tr>
				<#assign rowClass="even">
				<#list performanceSummaryList as performanceSummary>
				  <#if (performanceSummary.summaryMeasurement.nbrDataItems != 0)>
					<#if rowClass?starts_with("even")>
						<#assign rowClass="odd">
					<#else>
						<#assign rowClass="even">
					</#if>

					<tr class="${rowClass}" onMouseOver='rollOnRow(this, null)' onMouseOut='rollOffRow(this)' >
						<td title="Delete Performance Measurement Label" class="text"><code><a class="link" onclick="document.getElementById('perfLabel').value='${performanceSummary.label}';document.getElementById('action').value='delete';document.getElementById('performanceForm').submit();">[delete]</a></code></td>
						<td title="Performance Measurement Label" class="rhsBorder"><code>${performanceSummary.label}</td>	
						<td title="Total number of hits - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.nbrDataItems}</td>
						<td title="Average in Millis - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.average}</td>
						<td title="Min in Millis - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.minimum}</td>
						<td title="Max in Millis - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.maximum}</td>
						<#if performanceSummary.summaryMeasurement.firstObservationDate?has_content >
						<td title="First Observation Time - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.firstObservationDate?datetime}</td>
						<#else>
						<td />
						</#if>
						<#if performanceSummary.summaryMeasurement.lastObservationDate?has_content >
						<td title="Last Observation Time - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.lastObservationDate?datetime}</td>
						</#if>
						<td title="Total - All hits - ${performanceSummary.label}"><code>${performanceSummary.summaryMeasurement.total}</td>
						<td title="Standard Deviation - All hits - ${performanceSummary.label}" class="rhsBorder"><code>${performanceSummary.summaryMeasurement.standardDeviation}</td>	
						<td title="Total number of hits - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.nbrDataItems}</td>
						<td title="Average in Millis - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.average}</td>
						<td title="Min in Millis - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.minimum}</td>
						<td title="Max in Millis - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.maximum}</td>
						<#if performanceSummary.rollingNbrObservationsMeasurement.firstObservationDate?has_content >
						<td title="First Observation Time - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.firstObservationDate?datetime}</td>
						<#else>
						<td />
						</#if>
						<#if performanceSummary.rollingNbrObservationsMeasurement.lastObservationDate?has_content >
						<td title="Last Observation Time - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.lastObservationDate?datetime}</td>
						<#else>
						<td />
						</#if>
						<td title="Total in Millis - Last 100 hits - ${performanceSummary.label}"><code>${performanceSummary.rollingNbrObservationsMeasurement.total}</td>
						<td title="Standard Deviation in Millis - Last 100 hits - ${performanceSummary.label}" class="rhsBorder"><code>${performanceSummary.rollingNbrObservationsMeasurement.standardDeviation}</td>				
					</tr>
				  </#if>
				</#list>
			</table>
		</#if>
		
		</form>
		<#include "pageFooter.ftl">
	</body>
</html>