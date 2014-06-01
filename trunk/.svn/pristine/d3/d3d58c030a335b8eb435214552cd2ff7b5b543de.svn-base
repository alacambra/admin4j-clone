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
 * limitations 
 */
package net.admin4j.exception;

import net.admin4j.util.annotate.Product;
import net.admin4j.util.annotate.ProductDependencies;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Will track exceptions recorded via Log4j
 * @author D. Ashmore
 * @since 1.0
 */
@ProductDependencies( {Product.LOG4J} )
public class Log4JExceptionAppender extends AppenderSkeleton {
    
    private static Logger logger = LoggerFactory.getLogger(ExceptionTracker.class);

    @Override
    protected void append(LoggingEvent event) {
        /*
         * It's important that this logger not even throw a RuntimeException.
         * Throwing any exception will mask the underlying error and do users a great
         * disservice by masking the root issue.  D. Ashmore -- Aug, 2010.
         */
        try {
            if (event != null 
                    && event.getThrowableInformation() != null 
                    && event.getThrowableInformation().getThrowable() != null) {
                ExceptionTracker.track(event.getThrowableInformation().getThrowable());
            }
        }
        catch (Throwable t) {
            processError(t);
        }
        
    }

    protected void processError(Throwable t) {
        if (logger != null) {
            logger.error("Error tracking logged exception", t);
        }
        else t.printStackTrace();
    }

    public void close() {
        // NoOp
        
    }

    public boolean requiresLayout() {
        return false;
    }

}
