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
package net.admin4j.vo;

import java.io.File;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.admin4j.deps.commons.lang3.JavaVersion;
import net.admin4j.deps.commons.lang3.SystemUtils;
import net.admin4j.deps.commons.lang3.Validate;
import net.admin4j.util.Admin4jRuntimeException;

/**
 * File wrapper to make display of file information easier.
 * @author D. Ashmore
 * @since 1.0
 */
public class FileWrapperVO extends BaseVO implements Comparable<FileWrapperVO> {
    private static final long serialVersionUID = -3043572642440858281L;
    private static final boolean IS_WINDOWS = SystemUtils.OS_NAME.toLowerCase().indexOf("windows") >= 0;
    private File file;
    
    public FileWrapperVO(File file) {
        Validate.notNull(file, "Null file not allowed.");
        this.file = file;
    }
    
    public String getName() {
        if (this.file.getParent() == null) {
            return this.file.getPath().replace('\\', '/');
        }
        return this.file.getName();
    }
    
    public String getPath() {
        return this.file.getPath().replace('\\', '/');
    }
    
    public String getFullName() {
        return this.file.getAbsolutePath().replace('\\', '/');
    }
    
    public boolean isWritable() {
        return this.file.canWrite();
    }
    
    public boolean isReadable() {
        return this.file.canRead();
    }
    
    public boolean isExecutable() {
        if (IS_WINDOWS && localCanExecute(this.file) && (this.file.getName().endsWith(".cmd") || this.file.getName().endsWith(".bat"))) {
            return true;
        }
        else if (IS_WINDOWS) { // canExecute is not reliable on Windows -;)
            return false;
        }
        else return localCanExecute(this.file);
    }
    
    // Not available on jdk5
    private boolean localCanExecute(File file) {
        if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_6)) {
            try {
                Method canExecuteMethod = File.class.getMethod("canExecute", (Class[])null);
                return (Boolean)canExecuteMethod.invoke(file,(Object[]) null);
            } catch (Exception e) {
                throw new Admin4jRuntimeException("Can't execute File.canExecute()", e);
            } 
        }
        
        return false;
    }
    
    public String getAccessAttributes() {
        String fileAttrs = "";
        if (file.isDirectory()) {
          fileAttrs = fileAttrs + "d";
        }
        else fileAttrs = fileAttrs + "-";
      
        if (this.localCanExecute(file) ) {
          fileAttrs = fileAttrs + "x";
        }
        else fileAttrs = fileAttrs + "-";
      
        if (file.canRead()) {
          fileAttrs = fileAttrs + "r";
        }
        else fileAttrs = fileAttrs + "-";
      
        if (file.canWrite()) {
          fileAttrs = fileAttrs + "w";
        }
        else fileAttrs = fileAttrs + "-";
        
        return fileAttrs;
    }
    
    public String getSize() {
    	if (file.length() > 0L && file.length() < 1024 )
    		return "1 KB";
        return NumberFormat.getIntegerInstance().format(file.length() / 1024) + " KB";
    }
    
    public String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        return format.format(new Date(file.lastModified()));
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(FileWrapperVO o) {
        return this.file.compareTo(o.file);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        FileWrapperVO rhs = null;
        if (obj instanceof FileWrapperVO) {
            rhs = (FileWrapperVO)obj;
            return this.file.equals(rhs.file);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.file.hashCode();
    }

    /* (non-Javadoc)
     * @see net.admin4j.vo.BaseVO#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new FileWrapperVO(this.file);
    }

}
