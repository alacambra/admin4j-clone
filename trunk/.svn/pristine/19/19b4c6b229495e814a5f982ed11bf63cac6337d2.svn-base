<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "Hot Spot Display">
		<#include "pageHeadTagContent.ftl">
		<#include "rollOverRowJS.ftl">
	</head>
	<body>
		<#include "pageHeader.ftl">
		<div class="text">
			Note: These statistics are <b>not</b> persisted and will reset on container recycle.<br/><br/><br/>
		</div>
		
		<form id="hotSpotForm" method="post">
			<input name="Refresh" id="Refresh" type="hidden" value="">
			<input name="Reset" id="Reset" type="hidden" value="">
			<input name="DisplayClass" id="DisplayClass" type="hidden" value="">
			</input>
			
			<table border="0">
				<tr>
					<td><b>Number to Display:</b></td>
					<td>
						<select name="displayOption" onchange="document.getElementById('hotSpotForm').submit();">
							<#list displayOptionList as displayOption>
								<#if displayOption == selectedDisplayOption>
									<option value="${displayOption}" selected="selected">${displayOption}</option>
								<#else>
									<option value="${displayOption}">${displayOption}</option>
								</#if>
							</#list>
						</select>
					</td>
					<td class="text"><a class="link" onclick="document.getElementById('Refresh').value='Refresh';document.getElementById('hotSpotForm').submit();">[refresh]</a></td>
					<td class="text"><a class="link" onclick="document.getElementById('Reset').value='Reset';document.getElementById('hotSpotForm').submit();">[reset]</a></td>
				</tr>
			</table>
			
			<#if displayedExecutionPointList?exists>
				<hr/>
				<p><b>Execution Point Detail</b></p>
				<#assign execList=displayedExecutionPointList>
				<#include "hotSpotClassList.ftl">
			</#if>
			
			<#if (blockedExecutionPointList?size > 0)>
				<div style="font-size: 10px">
					<table>
						<tr>
							<td><a href="#HotSpots">Hot Spots</a></td>
							<td><a href="#BlockedExecs">Blocked Execution Points</a></td>
						</tr>
					</table>
				</div>
			</#if>
			
			<hr/>
			<p><a name="HotSpots"><b>Hot Spots</b></a></p>
			<#assign execList=executionPointList>
			<#include "hotSpotClassList.ftl">
			
			<#if (blockedExecutionPointList?size > 0)>
				<hr/>
				<p><a name="BlockedExecs"><b>Blocked Execution Points</b></a></p>
				<#assign execList=blockedExecutionPointList>
				<#include "hotSpotClassList.ftl">
			</#if>
			
		</form>
		
		<#include "pageFooter.ftl">
	</body>
</html>