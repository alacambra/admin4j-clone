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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Generic numeric utilities not provided by jdk or Commons Lang.
 * @author D. Ashmore
 * @since 1.0
 */
public class NumberUtils {
    
    private static final BigDecimal DOUBLE_MAX_VALUE = new BigDecimal(Double.MAX_VALUE);
    private static final BigInteger INTEGER_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    
    public static Number min(List<Number> numberList) {
        Number answer = null;
        double answerValue = Double.MAX_VALUE;
        
        for (Number number: numberList) {
            if (number.doubleValue() < answerValue) {
                answerValue = number.doubleValue();
                answer = number;
            }
        }
        
        return answer;
    }
    
    public static Number max(List<Number> numberList) {
        Number answer = null;
        double answerValue = Double.MIN_VALUE;
        
        for (Number number: numberList) {
            if (number.doubleValue() > answerValue) {
                answerValue = number.doubleValue();
                answer = number;
            }
        }
        
        return answer;
    }
    
    public static Number sum(List<Number> numberList) {
        if (hasFractions(numberList)) {
            return sumDecimals(numberList);
        }
        else return sumIntegers(numberList);
    }
    
    public static Number average(List<Number> numberList) {
        return sum(numberList).doubleValue() / (double)numberList.size();
    }
    
    public static Number standardDeviation(List<Number> numberList) {
        return Math.sqrt(variance(numberList).doubleValue());
    }
    
    public static Number variance(List<Number> numberList) {
        double firstPart = (sumSquares(numberList).doubleValue() / (double)(numberList.size()));
        double secondPart = Math.pow(average(numberList).doubleValue(), 2);
        return firstPart 
            - secondPart;
    }
    
    private static Number sumIntegers(List<Number> numberList) {
        BigInteger total = BigInteger.ZERO;
        for (Number number: numberList) {
            if (number instanceof BigInteger) {
                total = total.add((BigInteger)number);
            }
            else {
                total = total.add(BigInteger.valueOf(number.longValue()));
            }
        }
        
        if (total.compareTo(INTEGER_MAX_VALUE) < 0) {
            return total.longValue();
        }
        
        return total;
    }
    
    private static Number sumDecimals(List<Number> numberList) {
        BigDecimal total = new BigDecimal(0);
        for (Number number: numberList) {
            if (number instanceof BigDecimal) {
                total = total.add((BigDecimal)number);
            }
            else {
                total = total.add(new BigDecimal( number.doubleValue()));
            }
        }
        
        if (total.compareTo(DOUBLE_MAX_VALUE) < 0) {
            return total.doubleValue();
        }
        
        return total;
    }
    
    private static Number sumSquares(List<Number> numberList) {
        BigDecimal total = new BigDecimal(0);
        for (Number number: numberList) {
            if (number instanceof BigDecimal) {
                total = total.add( ((BigDecimal)number).pow(2));
            }
            else {
                total = total.add(new BigDecimal( number.doubleValue()).pow(2));
            }
        }
        
        if (total.compareTo(DOUBLE_MAX_VALUE) < 0) {
            return total.doubleValue();
        }
        
        return total;
    }
    
    private static boolean hasFractions(List<Number> numberList) {
        boolean answer = false;
        for (Number number: numberList) {
            if (number instanceof Float || number instanceof Double || number instanceof BigDecimal) {
                answer = true;
            }
        }
        
        return answer;
    }

}
