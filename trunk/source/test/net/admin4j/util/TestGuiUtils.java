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

import java.util.Date;

import junit.framework.Assert;
import net.admin4j.deps.commons.lang3.StringUtils;

import org.junit.Test;

public class TestGuiUtils {
    
    @Test
    public void testHtmlEscape() {
        String foo = GuiUtils.htmlEscape("\"Bread\" & \"Butter\"");
        
        System.out.println(foo);
        Assert.assertTrue("Html Escape Test", foo.indexOf("quot") > 0);
        Assert.assertTrue("Html Escape Test 2", foo.indexOf("&amp;") > 0);
        
        foo = GuiUtils.htmlEscape(null);
        Assert.assertTrue("Null Test", StringUtils.isEmpty(foo));
        
        foo = GuiUtils.htmlEscape(Integer.valueOf(0));
        Assert.assertTrue("Non-string Test", "0".equals(foo));
    }
    
    @Test
    public void testJavaScriptEscape() {
        String foo = GuiUtils.javascriptEscape("select 'App' as type, App_Id as value");     
        System.out.println(foo);
        Assert.assertTrue("Apostrophe test", StringUtils.countMatches(foo, "\\") == 2);
        
        foo = GuiUtils.javascriptEscape("select \"App\" as type, App_Id as value");
        System.out.println(foo);
        Assert.assertTrue("Quote test", StringUtils.countMatches(foo, "\\") == 2);
        
        foo = GuiUtils.javascriptEscape(null);
        Assert.assertTrue("Null Test", StringUtils.isEmpty(foo));
        
        foo = GuiUtils.javascriptEscape(Integer.valueOf(0));
        Assert.assertTrue("Non-string Test", "0".equals(foo));
    }
    
    @Test
    public void testObjectId() {
        String foo = GuiUtils.objectId(null);
        Assert.assertTrue("Null Test", foo.equals("-1"));
        
        foo = GuiUtils.objectId("Foo");
        System.out.println(foo);
        
        Assert.assertTrue("Basic Test", !StringUtils.isEmpty(foo));
    }
    
    @Test
    public void testToDate() {
        Date foo = GuiUtils.toDate(null);
        Assert.assertTrue("Null Test", foo == null);
        
        foo = GuiUtils.toDate(System.currentTimeMillis());
        System.out.println(foo);
        Assert.assertTrue("basic Test", foo != null);
    }
    
    @Test
    public void testBytes2Mb() {
        Double foo = GuiUtils.bytes2Mb(null);
        Assert.assertTrue("Null Test", foo == null);
        
        foo = GuiUtils.bytes2Mb(1024000L);
        System.out.println(foo);
        Assert.assertTrue("Basic Test", foo == 1.0);
    }
    
    @Test
    public void testAbbreviate() {
        String foo = GuiUtils.abbreviate(null, 6);
        Assert.assertTrue("Null Test", StringUtils.isEmpty(foo));
        
        foo = GuiUtils.abbreviate("123456789", 6);
        System.out.println(foo);
        Assert.assertTrue("basic test 1", "123456...".equals(foo));
        
        foo = GuiUtils.abbreviate("123456789", 6, ";;;");
        System.out.println(foo);
        Assert.assertTrue("basic test 2", "123456;;;".equals(foo));
    }

}
