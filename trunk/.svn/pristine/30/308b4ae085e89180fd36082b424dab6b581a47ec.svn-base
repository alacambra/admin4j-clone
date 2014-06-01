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
package net.admin4j.util.notify;

import net.admin4j.deps.commons.lang3.StringUtils;


/**
 * Used to support SMS messages via a Email interface.  Message will be restricted to 70 bytes.
 * <p>Note: If the logger for this class is DEBUG, the debug setting will
 * be passed onto the java mail api.</p>
 * @author D. Ashmore
 *
 */
public class SmsTextEmailNotifier extends TextEmailNotifier {
    
    private static final int MAX_SMS_MESSAGE_LENGTH = 70;

    /* (non-Javadoc)
     * @see net.admin4j.util.notify.TextEmailNotifier#notify(java.lang.String, java.lang.String)
     */
    @Override
    public void notify(String subject, String message) {
        super.notify(subject, StringUtils.abbreviate(message, MAX_SMS_MESSAGE_LENGTH));
    }

    /* (non-Javadoc)
     * @see net.admin4j.util.notify.BaseEmailNotifier#supportsSMS()
     */
    @Override
    public boolean supportsSMS() {
        return true;
    }

}
