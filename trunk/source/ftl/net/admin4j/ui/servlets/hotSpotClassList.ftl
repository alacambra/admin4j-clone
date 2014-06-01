			<table cellspacing="3" cellpadding="3">
				<tr>
					<th style="text-align:center;">Class Name</th>
					<th style="text-align:center;">Method Name</th>
					<th>Nbr of Executions</th>
					<th>Nbr of Blocked Executions</th>
					<th>Execution Pct</th>
					<th>Called by Execution Points</th>
					<th>Calling Execution Points</th>
					<th>Blocking Synchronization Objects</th>
				</tr>
				<#assign rowClass="even">
				<#list execList as point>
				
					<#if rowClass?starts_with("even")>
						<#assign rowClass="odd">
					<#else>
						<#assign rowClass="even">
					</#if>
				<tr class="${rowClass}" onMouseOver='rollOnRow(this, null)' onMouseOut='rollOffRow(this)'>
					<td class="text" title="Executed class">${point.stackTraceElement.className}</td>
					<td class="text" title="Executed method">${point.stackTraceElement.methodName}()</td>
					<td class="text" title="Number of Times Executed">${point.nbrExecutions}</td>
					<td class="text" title="Number of Times Executed">${point.nbrBlockedExecutions}</td>
					<td class="text" title="Number of Times Percentage">${hotSpotUtils.computeExecutionPct(executionPointList, point)}%</td>
					<td class="text">
						<#list point.calledStackTraceElementList as calledByPoint>
						<table cellspacing="3" cellpadding="3">
							<tr>
								<td class="text" title="Called by class - click for detail"><a class="link" onclick="document.getElementById('DisplayClass').value='${calledByPoint.key.className}:${calledByPoint.key.methodName}:${calledByPoint.key.fileName}';document.getElementById('hotSpotForm').submit();">${calledByPoint.key.className}</a></td>
								<td class="text" title="Called by method">${calledByPoint.key.methodName}()</td>
								<td class="text" title="Number of 'Called By' executions">${calledByPoint.value}</td>
							</tr>
						</table>
						</#list>
					</td>
					<td class="text">
						<#list point.callingStackTraceElementList as callingPoint>
						<table cellspacing="3" cellpadding="3">
							<tr>
								<td class="text" title="Calling class - click for detail"><a class="link" onclick="document.getElementById('DisplayClass').value='${callingPoint.key.className}:${callingPoint.key.methodName}:${callingPoint.key.fileName}';document.getElementById('hotSpotForm').submit();">${callingPoint.key.className}</a></td>
								<td class="text" title="Calling method">${callingPoint.key.methodName}()</td>
								<td class="text" title="Number of 'Calling' executions">${callingPoint.value}</td>
							</tr>
						</table>
						</#list>
					</td>
					<td class="text">
						<#list point.blockingSynchronizedClassList as blockingClass>
						<table cellspacing="3" cellpadding="3">
							<tr>
								<td class="text" title="Blocking class"><a class="link">${blockingClass.key}</td>
								<td class="text" title="Number of 'Blocking' executions">${blockingClass.value}</td>
							</tr>
						</table>
						</#list>
					</td>
				</tr>
				</#list>
			</table>