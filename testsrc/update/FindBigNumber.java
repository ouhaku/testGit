package org.bob.test.algorithm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FindBigNumber {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FindBigNumber findbignumber = new FindBigNumber();
        //System.out.println("input target number:");
        BigInteger target = new BigInteger(args[0]);
        BigInteger odd = BigInteger.ONE;
        BigInteger even = BigInteger.ONE;
        BigInteger digit = BigInteger.ONE;
        BigInteger TWO = new BigInteger("2");
        BigInteger FIVE = new BigInteger("5");
        while (target.remainder(BigInteger.TEN).equals(BigInteger.ZERO)) {
            target = target.divide(BigInteger.TEN);
            digit = digit.multiply(BigInteger.TEN);
        }
        while (target.remainder(TWO).equals(BigInteger.ZERO)) {
            target = target.divide(TWO);
            even = even.multiply(TWO);
            odd = odd.multiply(FIVE);
        }
        while (target.remainder(FIVE).equals(BigInteger.ZERO)) {
            target = target.divide(FIVE);
            even = even.multiply(FIVE);
            odd = odd.multiply(TWO);
        }
        BigInteger match = findbignumber.findMatching(target);
        System.out.println(target.multiply(even).multiply(digit) + " * " + match.divide(target).multiply(odd) + " = " + match.multiply(odd).multiply(even).multiply(digit));
    }

    public BigInteger findMatching(BigInteger target) {
        Map<BigInteger,BigInteger> rest = new LinkedHashMap<BigInteger,BigInteger>();
        BigInteger current = new BigInteger("1");
        while(true ) {
            try {
                BigInteger yushu = new BigInteger(current.remainder(target).toString());
                if (yushu.compareTo(BigInteger.ZERO) == 0)
                	return current;
                BigInteger index = new BigInteger(current.toString());
                if (rest.containsKey(yushu))
                    break;
                else {
                    rest.put(yushu, index);
                    current = current.multiply(BigInteger.TEN);
                }               
            } catch (Exception e) {
                System. out.println(e.toString());
            }
        }
        //Iterator iter = rest.entrySet().iterator();
        /*while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>)iter.next();
            //System. out.println("key: " + entry.getKey().toString());
            //System. out.println("value: " + entry.getValue().toString());
            System.out.println(entry.getValue() + " "  + entry.getKey());
        }*/
        List<Map.Entry<BigInteger, BigInteger>> goods = new ArrayList<Map.Entry<BigInteger, BigInteger>>(rest.entrySet());
        /*for (int i = 0; i < temp.size(); i++) {
            String id = temp.get(i).toString();
            System.out.println(id);
        }*/
        BigInteger boundary = new BigInteger("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        //BigInteger boundary = new BigInteger("10000000000");
        BigInteger valueweight;
        Integer looplength = goods.size();
        if (looplength==1) 
            valueweight = BigInteger.TEN;
        else
            valueweight = BigInteger.ONE;
        
        BigInteger result = BigInteger.ZERO;
        BigInteger a[][] = new BigInteger[2][target.add(BigInteger.ONE).intValue()];
        for (int i=0; i<=target.intValue(); i++) {
            a[0][i] = boundary;
        }
        a[0][1] = BigInteger.ONE;
        int flip = 1;
        
        for (int p=2;p>0;p++) {
            String temp = "";
            BigInteger weight = goods.get((p-1)%looplength).getKey();
            BigInteger value = goods.get((p-1)%looplength).getValue().multiply(valueweight);
            for (int q=target.intValue();q>=1;q--) {
                a[1-flip][0] = BigInteger.ZERO;
                if ( q >= weight.intValue())
                    a[flip][q] = a[1-flip][q].min(a[1-flip][q-weight.intValue()].add(value));
                else
                    a[flip][q] = a[1-flip][q];

                temp = a[flip][q].toString() + " " + temp; 
            }
            if ( a[flip][target.intValue()].compareTo(boundary) == -1) {
                result = a[flip][target.intValue()];
                break;
            }
            /*temp = weight.toString() + "      " + temp;
            temp = value.toString() + " " + temp;
            System.out.println(temp);*/
            flip = 1 - flip;
            if (p%looplength == 0)
                valueweight = valueweight.multiply(current);
        }
               
        return result;
    }
}

