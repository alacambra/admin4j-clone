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

import static org.junit.Assert.assertTrue;

import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPropertyUtils {

    private static final String TEST_STRING = "This is ${user.dir} an ${example} and ${user.dir}";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testResolveVariableReferences() {
        Properties props = new Properties();
        
        props.setProperty("foo", "foo-resolved");
        props.setProperty("bar", "Just a normal ${user.dir}");
        props.setProperty("prop3", "Just a normal value - not ${bar}");
        props.setProperty("prop4", "Not Just a normal value ${foo}");
        
        PropertyUtils.resolveVariableReferences(props);
        assertTrue("Prop resolve test 1", props.getProperty("prop4").indexOf("foo-resolved") > 0);
        assertTrue("Prop resolve test 2", props.getProperty("prop3").indexOf("bar") < 0);
        assertTrue("Prop resolve test 3", props.getProperty("bar").indexOf("user.dir") < 0);
        System.out.println(props);
    }

    @Test
    public void testFindSettingsNeedingResolution() {
        Properties props = new Properties();
        
        props.setProperty("prop1", "Just a normal value");
        props.setProperty("prop2", "Just a normal value");
        props.setProperty("prop3", "Just a normal value - not ${bar}");
        props.setProperty("prop4", "Not Just a normal value ${foo}");
        
        Set<String> set = PropertyUtils.findSettingsNeedingResolution(props);
        assertTrue("Finding props test", set.size() == 2);
    }

    @Test
    public void testFindReferencedVariableNames() {
        Set<String> set = PropertyUtils.findReferencedVariableNames(TEST_STRING, 0);
        assertTrue("Referenced Variable Test",set.size() == 2);
        
        set = PropertyUtils.findReferencedVariableNames("", 0);
        assertTrue("Referenced Variable Test 2",set.size() == 0);
    }

    @Test
    public void testReplaceVariableWithValue() {
        String translated = PropertyUtils.replaceVariableWithValue("user.dir", System.getProperty("user.dir"), TEST_STRING);
        System.out.println(TEST_STRING);
        System.out.println(translated);
        
        assertTrue("Variable stubst test", translated.indexOf("user.dir") < 0);
        assertTrue("Variable stubst test", translated.indexOf("example") >= 0);
    }

}
