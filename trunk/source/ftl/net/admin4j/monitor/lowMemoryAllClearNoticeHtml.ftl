<html>
<head>
<#include "messageStyle.ftl">
</head>
<body>
<#assign title = "${host} - Memory Usage Normal">
<#include "messageHeader.ftl">  
<table>
	<tr>
		<td><b>Problem Start datetime:</b></td>
		<td><code>${startDate?datetime}</code></td>
	</tr>
	<tr>
		<td><b>Problem End datetime:</b></td>
		<td><code>${endDate?datetime}</code></td>
	</tr>
	<tr>
		<td><b>Memory Available (percent):</b></td>
		<td><code>${memory.percentMemoryAvailable}</code></td>
	</tr>
	<tr>
		<td><b>Maximum Memory Available (Mb):</b></td>
		<td><code>${memory.freeAvailableMemoryInMb}</code></td>
	</tr>	
	<tr>
		<td><b>Average Low Watermark Increase (Mb):</b></td>
		<td><code>${GuiUtils.bytes2Mb(averageLowMemoryIncrease)}</code></td>
	</tr>
	<tr>
		<td><b>Number of Low Watermark Measurements:</b></td>
		<td><code>${lowMemoryMeasurementList?size}</code></td>
	</tr>
	<tr>
		<td><b>Low Watermark Measurement Interval (Millis):</b></td>
		<td><code>${lowWatermarkMonitorIntervalInMillis}</code></td>
	</tr>
</table>

<p>
<table border="3">
	<tr>
		<th>Interval Start Time</th>
		<th>Low Memory Watermark (Mb)</th>
		<th>Interval Start Time</th>
		<th>Low Memory Watermark (Mb)</th>
	</tr>
<#assign rowClass="even">
<#list lowMemoryMeasurementList as measurement>
	<#if rowClass?starts_with("even")>
	<tr>		
	</#if>
		<td><code>${GuiUtils.toDate(measurement.firstObservationTime)?datetime}</code></td>
		<td>${GuiUtils.bytes2Mb(measurement.minimum)}</td>
	<#if rowClass?starts_with("odd")>
	</tr>
	</#if>
	
	<#if rowClass?starts_with("even")>
		<#assign rowClass="odd">
	<#else>
		<#assign rowClass="even">
	</#if>
</#list>
</table>
	
</p>

<p>
<#list threadInfoArray as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceHtml.ftl">
</#list>
</p>
</body>
</html>