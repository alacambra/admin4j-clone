<#macro formatComposite composite>
  <table border="1">
  	<#list JmxUtils.compositeKeyList(composite) as item>
  		<#assign value=JmxUtils.compositeValue(composite, item)>
  		
  		<#if JmxUtils.isCompositeData(value)>
			<@formatComposite composite=value />
		<#elseif JmxUtils.isTabularData(value)>
			<@formatTabularData tabData=value />
		<#elseif JmxUtils.isArray(value)>
			<@formatArray array=value />
		<#else>
			${JmxUtils.htmlEscape(value)}
		</#if>
  	</#list>
  </table>
</#macro>
<#macro formatTabularData tabData>
	<#list tabData.values() as item>
		<@formatComposite composite=item />
	</#list>
</#macro>
<#macro formatArray array>
	<#list array as item>
		<#if JmxUtils.isCompositeData(value) >
			<@formatComposite composite=value />
		<#elseif JmxUtils.isTabularData(value) >
			<@formatTabularData tabData=value />
		<#elseif JmxUtils.isArray(value) >
			<@formatArray array=value />
		<#else>
			${JmxUtils.htmlEscape(value)}
		</#if>
	</#list>
</#macro>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "JMX Browser">
		<#include "pageHeadTagContent.ftl">
	</head>
	<body>
		<#include "pageHeader.ftl">
		
		<form method="post">
		
			<table border="0">
				<tr>
					<td><b>Jmx Bean:</b></td>
					<td>
						<select name="jmxBean">
							<#list jmxBeanList as jmxBean>
								<#if jmxBean == selectedJmxBeanName>
									<option value="${jmxBean}" selected="selected">${jmxBean}</option>
								<#else>
									<option value="${jmxBean}">${jmxBean}</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td colSpan="2">
						<input type="submit" value="submit"/>
					</td>
				</tr>
			</table>
			
			<hr />
			<table border="5">
			
				<#list mBeanAttributeList as mBeanAttribute>
					<tr>
						<td><b>${mBeanAttribute.name}</b></td>
						<td>
							<#if JmxUtils.isCompositeData(mBeanAttribute.value) >
								<@formatComposite composite=mBeanAttribute.value />
							<#elseif JmxUtils.isTabularData(mBeanAttribute.value) >
								<@formatTabularData tabData=mBeanAttribute.value />
							<#else>
								${JmxUtils.htmlEscape(mBeanAttribute.value)}
							</#if>
						</td>
					</tr>
				</#list>
				
				<#list mBeanOperationList as mBeanOperation>
					<tr>
						<td><b>${mBeanOperation.name}</b></td>
						<td>
							<li>${mBeanOperation.returnType} - ${mBeanOperation.name}${JmxUtils.formatOperationSignature(mBeanOperation)}
								<#if JmxUtils.isOperationExecutable(mBeanOperation) >
									<input name="opExec" type="submit" value="submit" />
								</#if>
							</li>
						</td>
					</tr>
				</#list>
			</table>
			
		</form>
		
		<#include "pageFooter.ftl">
	</body>
</html>