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
package net.admin4j.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class ServletOutputStreamMock extends ServletOutputStream {
    
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Override
    public void write(int b) throws IOException {
        byteArrayOutputStream.write(b);

    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return byteArrayOutputStream.toString();
    }

}
