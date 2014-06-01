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
package net.admin4j.exception;


import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import net.admin4j.entity.ExceptionInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestExceptionTracker {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMe() throws Exception {
		Exception e = createException("Crap");
		
		ExceptionTracker.addExemptedExceptionClassName("java.sql.SQLException");
		
		for (int i = 0; i < 5; i++) {
			e = createException("Crap");
			ExceptionTracker.track(e);
		}
		
		Exception f = createException("Crap");
		for (int i = 0; i < 5; i++) {
			f = createException("Crap");
			ExceptionTracker.track(f);
		}
		
		Exception g = null;
		for (int i = 0; i < 5; i++) {
            g = createCompositeException("Crap");
            ExceptionTracker.track(g);
        }

		//ExceptionStatistic stat1, stat2;
		
		ExceptionInfo obj = ExceptionTracker.findExceptionInfo(e);
		assertTrue("date test", obj.getFirstOccuranceDt() != null);
		assertTrue("date test", obj.getLastOccuranceDt() != null);
		assertTrue("date test", obj.getTotalNbrExceptions() == 5);
		
		System.out.println("Statistic Set size " + ExceptionTracker.getExceptionStatisticSet().size());
		assertTrue("Statistic Set size test", ExceptionTracker.getExceptionStatisticSet().size() == 2);
		
		ExceptionTrackerCleanupTask task = new ExceptionTrackerCleanupTask ();
		task.run();
		
		assertTrue("lookup test", ExceptionTracker.lookupException("foo") == null);
		ExceptionTracker.deleteException(obj.getAlternateId());
		assertTrue("lookup test", ExceptionTracker.lookupException(obj.getAlternateId()) == null);
		assertTrue("lookup test", ExceptionTracker.findExceptionInfo(e) != null);
		assertTrue("lookup test", ExceptionTracker.lookupException(obj.getAlternateId()) != null);
		
		ExceptionInfo fInfo = ExceptionTracker.findExceptionInfo(f);
		ExceptionTracker.purgeException(fInfo);
		assertTrue("lookup test", ExceptionTracker.lookupException(fInfo.getAlternateId()) == null);
    
	}
	
	private Exception createException(String label) {
		try {throw new Exception(label);}
		catch (Exception e) {
			return e;
		}
	}
	
	private Exception createCompositeException(String label) {
        try {throw new Exception(new SQLException(label));}
        catch (Exception e) {
            return e;
        }
    }

}
