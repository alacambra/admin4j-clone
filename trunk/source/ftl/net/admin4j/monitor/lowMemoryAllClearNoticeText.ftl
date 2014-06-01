${host} - Memory Usage Normal

Problem Start datetime:          			${startDate?datetime}
Problem End datetime:            			${endDate?datetime}
Memory Available (percent):      			${memory.percentMemoryAvailable}
Maximum Memory Available (Mb):   			${memory.freeAvailableMemoryInMb}
Average Low Watermark Increase (Mb):		${GuiUtils.bytes2Mb(averageLowMemoryIncrease)}
Number of Low Watermark Measurements:		${lowMemoryMeasurementList?size}
Low Watermark Measurement Interval (Millis):${lowWatermarkMonitorIntervalInMillis}

Interval Start Time			Low Memory Watermark (Mb)
-------------------			----------------------------
<#list lowMemoryMeasurementList as measurement>
${GuiUtils.toDate(measurement.firstObservationTime)?datetime}			${GuiUtils.bytes2Mb(measurement.minimum)}
</#list>

<#list threadInfoArray as tInfo>
	<#assign threadInfo = tInfo>
	<#include "stackTraceText.ftl">
</#list>