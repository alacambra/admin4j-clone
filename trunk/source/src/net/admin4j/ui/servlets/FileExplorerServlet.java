/*
 * This software is licensed under the Apache License, Version 2.0
 * (the "License") agreement; you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.admin4j.ui.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.fileupload.FileItem;
import net.admin4j.deps.commons.fileupload.FileUploadException;
import net.admin4j.deps.commons.fileupload.disk.DiskFileItemFactory;
import net.admin4j.deps.commons.fileupload.servlet.ServletFileUpload;
import net.admin4j.deps.commons.io.IOUtils;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.deps.commons.lang3.exception.ExceptionUtils;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.util.ServletUtils;
import net.admin4j.vo.FileWrapperVO;


/**
 * Provides a Web-based file explorer for application servers.
 * 
 * <p>You will need to map this servlet to an url pattern (e.g. '/admin4j/file').</p>
 * <p>This servlet does <b>not</b> require other web resources.</p>
 * <p>Init parameters for this servlet are as follows:</p>
 * <li>base.dir.name -- Optional.  Default user.dir system property setting.  Starting current directory.</li>
 * <li>restrict.to.base.dir -- Optional (true/false).  Default true.  Restricts exploring to the base directory and all subdirectories.</li>
 * <li>restrict.from.exec -- Optional (true/false).  Default true.  Restricts user from executing scripts or applications.</li>
 * <li>restrict.from.write -- Optional (true/false).  Default true.  Restricts user from uploading files.</li>
 * @author D. Ashmore
 * @since 1.0
 */
public class FileExplorerServlet extends AdminDisplayServlet {

    private static final long serialVersionUID = -3651856296828821466L;
    
    private FileExplorerRestrictions fileExplorerRestrictions = new FileExplorerRestrictions();
    private String baseDirectoryName = null;
    
    public static final String PUBLIC_HANDLE="fileExplorer";
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String restrictStr = ServletUtils.getConfigurationSetting(
                new String[]{PUBLIC_HANDLE + ".restrict.to.base.dir", 
                        "restrict.to.base.dir"}, config);
        if ("false".equalsIgnoreCase(restrictStr)) {
            this.fileExplorerRestrictions.setRestrictToBase(false);
        }
        else if ( Admin4JConfiguration.getFileExplorerRestrictToBaseDir() != null ) {
            this.fileExplorerRestrictions.setRestrictToBase(Admin4JConfiguration.getFileExplorerRestrictToBaseDir());
        }
        
        String restrictFromExecutionStr = ServletUtils.getConfigurationSetting(
                new String[]{PUBLIC_HANDLE + ".restrict.from.exec", 
                        "restrict.from.exec"}, config);
        if ("false".equalsIgnoreCase(restrictFromExecutionStr)) {
            this.fileExplorerRestrictions.setRestrictFromExecution(false);
        }
        else if ( Admin4JConfiguration.getFileExplorerRestrictFromExec() != null ) {
            this.fileExplorerRestrictions.setRestrictFromExecution(Admin4JConfiguration.getFileExplorerRestrictFromExec());
        }
        
        String restrictFromWriteStr = ServletUtils.getConfigurationSetting(
                new String[]{PUBLIC_HANDLE + ".restrict.from.write", 
                        "restrict.from.write"}, config);
        if ("false".equalsIgnoreCase(restrictFromWriteStr)) {
            this.fileExplorerRestrictions.setRestrictFromWrite(false);
        }
        else if ( Admin4JConfiguration.getFileExplorerRestrictFromWrite() != null ) {
            this.fileExplorerRestrictions.setRestrictFromWrite(Admin4JConfiguration.getFileExplorerRestrictFromWrite());
        }
        
        baseDirectoryName = ServletUtils.getConfigurationSetting(
                new String[]{PUBLIC_HANDLE + ".base.dir.name", 
                        "base.dir.name"}, config);
        if (StringUtils.isEmpty(baseDirectoryName)  && !StringUtils.isEmpty(Admin4JConfiguration.getFileExplorerBaseDirName())) {
            baseDirectoryName = Admin4JConfiguration.getFileExplorerBaseDirName();
        }
        else {
            baseDirectoryName = System.getProperty("user.dir");
        }
        
        File baseDir = new File(baseDirectoryName);
        if ( !baseDir.exists()) {
            throw new Admin4jRuntimeException("Base Directory (base.dir.name) doesn't exist")
                .addContextValue("base.dir.name", baseDirectoryName);
        }
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String displayDirectoryName = request.getParameter("dir");
        if (StringUtils.isEmpty(displayDirectoryName)) {
        	displayDirectoryName = request.getParameter("dirInMkDir");
        }
        String downloadFileName = request.getParameter("download");
        List<FileItem> fileItems = getMultipartRequestItems(request);
        String baseDirectoryNameDuringUpload = getMulitpartRequestParam(fileItems, "dirInUpload");
        
        if (StringUtils.isEmpty(displayDirectoryName) && StringUtils.isEmpty(baseDirectoryNameDuringUpload)) {
            displayDirectoryName = this.baseDirectoryName;
        }
        else if (StringUtils.isEmpty(displayDirectoryName)) {
            displayDirectoryName = baseDirectoryNameDuringUpload;
        }
        
        if (StringUtils.isEmpty(downloadFileName)) {
            File baseDir = new File(displayDirectoryName);
            String requestMessage = processRequest(request, baseDir, fileItems);
            this.presentDirectoryListing(baseDir, requestMessage, request, response); 
        }
        else this.presentFileContent(downloadFileName, request, response);
        
    }
    
    private void presentFileContent(String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {

        File displayFile = new File(filename);
        FileInputStream stream = new FileInputStream(displayFile);
        byte[] content = IOUtils.toByteArray(stream);
        stream.close();
        
        response.setContentType("application/octet-stream"); // or "application/octet-stream", your choice
        
        response.setContentLength(content.length);
        response.setHeader("Content-disposition", "attachment; filename=\"" + displayFile.getName() + "\"");
        try {
            response.getOutputStream().write(content);
        } catch (IOException e) {
            throw new Admin4jRuntimeException("Failed to write out the file.", e);
        }
    }
    
    private void presentDirectoryListing(File baseDir, String requestMessage, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
                
        Set<FileWrapperVO>  subDirectorySet = new TreeSet<FileWrapperVO>();
        Set<FileWrapperVO>  fileSet = new TreeSet<FileWrapperVO>();
        Set<FileWrapperVO>  rootSet = new TreeSet<FileWrapperVO>();
        
        for (File file: baseDir.listFiles()) {
            if (file.isDirectory())   subDirectorySet.add(new FileWrapperVO(file));
            else fileSet.add(new FileWrapperVO(file));
        }
        for (File file: File.listRoots()) {
            rootSet.add(new FileWrapperVO(file));
        }
        
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("restrictions", fileExplorerRestrictions);
        variableMap.put("message", requestMessage);
        variableMap.put("currentDirectory", new FileWrapperVO(baseDir));
        if (baseDir.getParentFile() != null && !fileExplorerRestrictions.isRestrictToBase())
        {
        	variableMap.put("parentDirectory", new FileWrapperVO(baseDir.getParentFile()));
        }
        else if (baseDir.getParentFile() != null && fileExplorerRestrictions.isRestrictToBase() && isWithinBase(baseDir.getParentFile()))
        {
            variableMap.put("parentDirectory", new FileWrapperVO(baseDir.getParentFile()));
        }
        variableMap.put("subdirectoryList", subDirectorySet);
        variableMap.put("rootList", rootSet);
        variableMap.put("fileList", fileSet);
        
        displayFreeMarkerPage(request, response, "fileExplorerServletDisplay.ftl", variableMap);

    }
    
    private boolean isWithinBase(File dir) {
        boolean answer = false;
        File localDir = dir;
        File baseDir = new File(this.baseDirectoryName);
        if (baseDir.equals(dir)) {
            answer = true;
        }
        
        while ( !answer && localDir.getParentFile() != null) {
            if (baseDir.equals(localDir.getParentFile())) {
                answer = true;
            }
            localDir = localDir.getParentFile();
        }
        
        return answer;
    }

    private String processRequest(HttpServletRequest request, File baseDir, List<FileItem> items) {
        String runExecutableName = request.getParameter("run");
        String deleteFileName = request.getParameter("delete");
        String mkDirName = request.getParameter("dirName");
        
        String message = "";
        if ( !StringUtils.isEmpty(runExecutableName)) {
            message = this.runExecutable(runExecutableName);
        }
        else if (ServletFileUpload.isMultipartContent(request)) {
            message = this.processUpload(request, baseDir, items);
        }
        else if ( !StringUtils.isEmpty(deleteFileName)) {
            message = this.processDelete(deleteFileName);
        }
        else if ( !StringUtils.isEmpty(mkDirName)) {
            message = this.processMkdir(mkDirName, baseDir);
        }
        return message;
    }
    
    private String processUpload(HttpServletRequest request, File directory, List<FileItem> items) {
        if (this.fileExplorerRestrictions.isRestrictFromWrite()) {
            return "File Upload not allowed.  Modify restrict.from.write setting to allow file upload.";
        }
        
        String message = "";
        try {
//            FileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            List<FileItem> items = upload.parseRequest(request);
            File uploadedFile;
            for (FileItem item : items) {
                if (!StringUtils.isEmpty(item.getName())) {
                    uploadedFile = new File(directory.getCanonicalPath() + "/"
                            + item.getName());
                    item.write(uploadedFile);
                    message="File " + item.getName() + " (" + item.getSize() + " bytes) Uploaded!";
                }
            }
        } catch (Exception e) {
            message = ExceptionUtils.getStackTrace(e);
        }
        return message;
    }
    
    private String processDelete(String deleteFileName) {
        if (this.fileExplorerRestrictions.isRestrictFromWrite()) {
            return "File delete not allowed.  Modify restrict.from.write setting to allow delete.";
        }
        
        File deleteFile = new File(deleteFileName);
        if (!deleteFile.delete()) {
            return "File not deleted.  Reason not known.  See javadoc for File.delete().";
        }
        
        return "File deleted";
    }
    
    private String processMkdir(String dirName, File baseDirectory) {
        if (this.fileExplorerRestrictions.isRestrictFromWrite()) {
            return "Directory creation not allowed.  Modify restrict.from.write setting to allow directory creation.";
        }
        
        String message = null;
        try {
            File newDir = new File(baseDirectory.getCanonicalPath() + "/"
                    + dirName);
            if (newDir.mkdir()) {
                message = "New Directory created: " + dirName;
            }
            else message = "New Directory not created: " + dirName + ".  Reason unknown.  See javadoc for File.mkdir().";
        } catch (Exception e) {
            message = ExceptionUtils.getStackTrace(e);
        }
        
        return message;
    }
    
    private String runExecutable(String executableName) {

        if (this.fileExplorerRestrictions.isRestrictFromExecution()) {
            return "File execution not allowed.  Modify restrict.from.exec setting to allow execution.";
        }
        
        File executableFile = new File(executableName);
        StringBuffer message = new StringBuffer();
        
        try {
            Process process = Runtime.getRuntime().exec(new String[]{executableName}, null, executableFile.getParentFile());
            
            String error = IOUtils.toString(process.getErrorStream());
            if ( !StringUtils.isEmpty(error)) {
                message.append(error);
            }
            
            String stdOut = IOUtils.toString(process.getInputStream());
            if ( !StringUtils.isEmpty(stdOut)) {
                message.append(stdOut);
            }
        } catch (Throwable e) {
            throw new Admin4jRuntimeException("error executing file.", e);
        }
        
        return message.toString();
    }
    
    public static class FileExplorerRestrictions implements Serializable {

        private static final long serialVersionUID = -6396405187849729874L;
        private boolean restrictToBase = true;
        private boolean restrictFromExecution = true;
        private boolean restrictFromWrite = true;
        
        public boolean isRestrictToBase() {
            return restrictToBase;
        }
        
        public void setRestrictToBase(boolean restrictToBase) {
            this.restrictToBase = restrictToBase;
        }
        
        public boolean isRestrictFromExecution() {
            return restrictFromExecution;
        }
        
        public void setRestrictFromExecution(boolean restrictFromExecution) {
            this.restrictFromExecution = restrictFromExecution;
        }

        public boolean isRestrictFromWrite() {
            return restrictFromWrite;
        }

        public void setRestrictFromWrite(boolean restrictFromWrite) {
            this.restrictFromWrite = restrictFromWrite;
        }
    }
    

    @SuppressWarnings("unchecked")
	private List<FileItem> getMultipartRequestItems(HttpServletRequest request)
    {
    	if (!ServletFileUpload.isMultipartContent(request))
    		return null;
    	
	    List<FileItem> items = new ArrayList<FileItem>();
		try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		} catch (FileUploadException e) {
			throw new RuntimeException("Could not parse multipart request.",e);
		}
		return items;
    }
    
    private String getMulitpartRequestParam(List<FileItem> items, String paramName)
    {
    	if (items == null)
    		return null;
	    for (FileItem item : items) {
	        if (item.isFormField() && paramName.equals(item.getFieldName())) 
	        {
	        	return item.getString();
	        }
	    }
	    return null;
    	
    }
    
    /* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "File Explorer";
    }
}
