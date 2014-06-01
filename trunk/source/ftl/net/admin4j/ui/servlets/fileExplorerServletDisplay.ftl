<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<#assign title = "Admin4J File Explorer">
		<#include "pageHeadTagContent.ftl">
		<script type="text/javascript">
		function submitForm(formName)
		{
			document.getElementById(formName).submit();
		}
		</script>		
	</head>
	<body>
		<#include "pageHeader.ftl">
		
		<b><blockquote><pre>${message}</pre></blockquote></b>
		<b>Directory: <code>${currentDirectory.fullName}</code></b>
		
		<#if !restrictions.restrictFromWrite>
			<form id="uploadOp" name="uploadOp" method="post" enctype="multipart/form-data">
				<input type="hidden" name="dirInUpload" id="dirInUpload" value="${currentDirectory.fullName}"></input>
				<input type="hidden" name="upload" id="upload" value=""></input>
				<input type="file"   name="uploadedFile" id="uploadFile" size="100"></input>
				<input type="submit" name="AddFile" id="AddFile" value="Add File"></input>
			</form>
	
			<form id="mkdirOp" name="mkdirOp" method="post">
				<input type="hidden" name="dirInMkDir" id="dirInMkDir" value="${currentDirectory.fullName}"></input>
				<input type="hidden" name="mkdir" id="mkdir" value="Y"></input>
				<input type="text"   name="dirName" id="dirName"></input>
				<input type="submit" name="Add Directory" value="Add Directory"></input>
			</form>
		</#if>
		
		<hr />
		<form id="fileOp" name="fileOp" method="post">
			<input type="hidden" name="dir" id="dir" value="${currentDirectory.fullName}"></input>
			<input type="hidden" name="download" id="download" value=""></input>
			<input type="hidden" name="run" id="run" value=""></input>
			<input type="hidden" name="delete" id="delete" value=""></input>
		</form>
			
			<table border="0">
				<tr>
					<td style="width:300px;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						&nbsp;Name
					</td>
					<td style="width:45px;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						&nbsp;Perm
					</td>
					<td style="width:80px;text-align:right;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						Size&nbsp;
					</td>
					<td style="width:125px;text-align:right;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						Date Modified&nbsp;
					</td>
					<td style="width:70px;text-align:center;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						&nbsp;
					</td>
					<td style="width:70px;text-align:center;background-color:#EBEADB;border-bottom:2px solid #D6D2C2;border-right:1px solid #D6D2C2;">
						&nbsp;
					</td>
				</tr>
				<#if parentDirectory??>
					<tr>
						<td style="width:300px;background-color:#f7f7f7;outline:1px solid #f7f7f7;">
							<#if parentDirectory.readable>
								<a class="explorer" href="#" onclick="document.getElementById('dir').value='${parentDirectory.fullName}';document.getElementById('download').value='';document.getElementById('run').value='';document.getElementById('delete').value='';submitForm('fileOp');" title="View Parent Directory">&lt;..&gt;</a>
							<#else>
								&lt;..&gt;
							</#if>
						</td>
						<td style="width:45px;">
							${parentDirectory.accessAttributes}
						</td>
						<td style="width:80px;text-align:right;"></td>
						<td style="width:125px;text-align:right;"></td>
						<td style="width:70px;text-align:center;"></td>
						<td style="width:70px;text-align:center;"></td>
					</tr>
				</#if>
				<#list subdirectoryList as subDir>
					<tr>
						<td style="width:300px;background-color:#f7f7f7;outline:1px solid #f7f7f7;">
							<#if subDir.readable>
								<a class="explorer" href="#" onclick="document.getElementById('dir').value='${subDir.fullName}';document.getElementById('download').value='';document.getElementById('run').value='';document.getElementById('delete').value='';submitForm('fileOp');" title="View '${subDir.name}' Folder">&lt;${subDir.name}&gt;</a>
							<#else>
								&lt;${subDir.name}&gt;
							</#if>
						</td>
						<td style="width:45px;">
							${subDir.accessAttributes}
						</td>
						<td style="width:80px;text-align:right;"></td>
						<td style="width:125px;text-align:right;">
							${subDir.dateTime}
						</td>
						<td style="width:70px;text-align:center;"></td>
						<td style="width:70px;text-align:center;">
							<#if subDir.writable><#if !restrictions.restrictFromWrite>
								[<a class="explorer" href="#" onclick="if(!confirm('Delete this directory?\n\n<${subDir.name}>')) return false;document.getElementById('download').value='';document.getElementById('run').value='';document.getElementById('delete').value='${subDir.fullName}';submitForm('fileOp');" title="Delete the '${subDir.name}' directory.">Delete</a>]
							</#if></#if>
						</td>
					</tr>
				</#list>
			    <#list fileList as file>
					<tr>
						<td style="width:300px;background-color:#f7f7f7;outline:1px solid #f7f7f7;">
							<#if file.readable>
								<a class="explorer" href="#" onclick="document.getElementById('download').value='${file.fullName}';document.getElementById('run').value='';document.getElementById('delete').value='';submitForm('fileOp');" title="Download File '${file.name}'">${file.name}</a>
							<#else>
								${file.name}
							</#if>
						</td>
						<td style="width:45px;">
							${file.accessAttributes}
						</td>
						<td style="width:80px;text-align:right;">
							${file.size}
						</td>
						<td style="width:125px;text-align:right;">
							${file.dateTime}
						</td>
						<td style="width:70px;text-align:center;">
							<#if file.executable><#if !restrictions.restrictFromExecution>
								[<a class="explorer" href="#" onclick="document.getElementById('download').value='';document.getElementById('run').value='${file.fullName}';document.getElementById('delete').value='';submitForm('fileOp');" title="Execute '${file.name}' on the server.">Execute</a>]
							</#if></#if>
						</td>
						<td style="width:70px;text-align:center;">
							<#if file.writable><#if !restrictions.restrictFromWrite>
								[<a class="explorer" href="#" onclick="if(!confirm('Delete this file?\n\n${file.name}')) return false;document.getElementById('download').value='';document.getElementById('run').value='';document.getElementById('delete').value='${file.fullName}';submitForm('fileOp');" title="Delete the '${file.name}' file.">Delete</a>]
							</#if></#if>
						</td>
					</tr>
				</#list>
			</table>
		
		<#include "pageFooter.ftl">
	</body>
</html>