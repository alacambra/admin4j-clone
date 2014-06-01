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
package net.admin4j.ui.filters;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.io.IOUtils;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.util.Admin4jRuntimeException;

/**
 *
 * This filter will record the server-side time for this request as a comment at the bottom of any generated HTML.
 * 
 * @author D. Ashmore
 * @since 1.0
 */
public class PerformanceTimeStampingFilter extends BaseFilter implements Filter {
    
    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {
        new Admin4JConfiguration();
        
    }

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		long beginTime = System.currentTimeMillis();
		
		try {
			
			ContentTrappingResponseWrapper wrapper = new ContentTrappingResponseWrapper((HttpServletResponse)response);
			filterChain.doFilter(request, wrapper);
			
			try {
    			String responseStr = wrapper.toString();
    			
    			double durationInSeconds = (System.currentTimeMillis() - beginTime) / 1000.000;
    			if (response.getContentType() != null && response.getContentType().toLowerCase().indexOf("html") >= 0 && this.isOuterExecution()) {
    				responseStr = StringUtils.replaceOnce(responseStr, ">", "> <!-- server side time: " + durationInSeconds + " seconds -->");
    				
    				PrintWriter out = response.getWriter();
                    try {
                        CharArrayWriter caw = new CharArrayWriter();
                        caw.write(responseStr);
                        
                        response.setContentLength(caw.toString().length());
                        out.write(caw.toString());
                    }
                    finally {
                        IOUtils.closeQuietly(out);
                    }
    			}
    			else {
    			    response.getOutputStream().write(wrapper.toByteArray());
    			    response.getOutputStream().flush();
    			    response.getOutputStream().close();
    			}
    			
			}
			catch (Throwable fubar) {
                logger.error("Error providing performance timestamp.", fubar);
            }
        } catch (ServletException se) {
        	throw se;
        } catch (Throwable ex) {
            throw new ServletException(ex);
        }
	}
	
	private boolean isOuterExecution() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        int nbrPresent = 0;
        
        for (int i = 0; i < stack.length; i++) {
            if (stack[i].getClassName().equals(PerformanceTimeStampingFilter.class.getName()) && stack[i].getMethodName().equals("doFilter")) {
                nbrPresent++;
            }
        }
        
        return nbrPresent == 1;
    }
	
	public void destroy() {	}	
	
	private static class ContentTrappingResponseWrapper extends HttpServletResponseWrapper {
		private ByteArrayOutputStream stream = null;
		protected PrintWriter writer = null;
		private HttpServletResponse origResponse = null;
		
		public ContentTrappingResponseWrapper(HttpServletResponse response) {
			super(response);
	        origResponse = response;
	        stream = new ByteArrayOutputStream(3000000);
	    }
		
		public byte[] toByteArray() {
		    return stream.toByteArray();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletOutputStreamWrapper(stream);
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (this.writer == null) {
				this.writer = new PrintWriter(stream);
			}
			return this.writer;
		}
		
		@Override
		public String toString(){
	    	try{
	    		if (this.writer != null) {
	    			this.writer.flush();
	    		}
	    		String responseStr = stream.toString(this.origResponse.getCharacterEncoding());
	    		return responseStr;
	    	}catch(UnsupportedEncodingException e){
	    		// an attempt to set an invalid character encoding would have caused this exception before
	    		throw new RuntimeException("PageTokenResponseWrapper response accepted invalid character encoding " + getCharacterEncoding());
	    	}
	    }

		@Override
		public void flushBuffer() throws IOException {
			stream.flush();
		}
		
		


	}
	
	private static class ServletOutputStreamWrapper extends ServletOutputStream {
		private OutputStream outputStream;

		public ServletOutputStreamWrapper(OutputStream outputStream){
			this.outputStream = outputStream;
		}

		public void write(int b){
			try {
				this.outputStream.write( b );
			} catch (IOException e) {
				throw new Admin4jRuntimeException(e);
			}
		}

		public void write(byte[] bytes) throws IOException{
			try {
				this.outputStream.write( bytes );
			} catch (IOException e) {
				throw new Admin4jRuntimeException(e);
			}
		}

		public void write(byte[] bytes, int off, int len){
			try {
				this.outputStream.write( bytes, off, len );
			} catch (IOException e) {
				throw new Admin4jRuntimeException(e);
			}
		}

    }
}

