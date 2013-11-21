package org.bob.test.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FindNumber {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FindNumber findnumber = new FindNumber();
        //System.out.println("input target number:");
        Integer target = Integer. parseInt(args[0]);
        Integer odd = 1;
        Integer even = 1;
        Integer digit = 1;
        while (target%10 == 0) {
        	target = target/10;
        	digit = digit * 10;
        }
        while (target%2 == 0) {
        	target = target/2;
        	even = even * 2;
        	odd = odd * 5;
        }
        Integer match = findnumber.findMatching(target);
        System.out.println(target*even*digit + " * " + match/target*odd + " = " + match*odd*even*digit);
    }

    public Integer findMatching(Integer target) {
        Map<Integer,Integer> rest = new LinkedHashMap<Integer,Integer>();
        int current = 1;
        while(true ) {
            try {
                Integer yushu = new Integer(current%target);
                Integer index = new Integer(current);
                if (rest.containsKey(yushu))
                    break;
                else {
                    rest.put(yushu, index);
                    current = current*10;
                }               
            } catch (Exception e) {
                System. out.println(e.toString());
            }
        }
        Iterator iter = rest.entrySet().iterator();
        /*while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>)iter.next();
            //System. out.println("key: " + entry.getKey().toString());
            //System. out.println("value: " + entry.getValue().toString());
            System.out.println(entry.getValue() + " "  + entry.getKey());
        }*/
        List<Map.Entry<Integer, Integer>> goods = new ArrayList<Map.Entry<Integer, Integer>>(rest.entrySet());
        /*for (int i = 0; i < temp.size(); i++) {
            String id = temp.get(i).toString();
            System.out.println(id);
        }*/
        Integer boundary = Integer. MAX_VALUE;
        boundary = 2000000000;
        Integer valueweight;
        Integer looplength = goods.size();
        if (looplength==1) 
        	valueweight = 10;
        else
        	valueweight = 1;
        
        Integer result = 0;
        Integer a[][] = new Integer[2][target+1];
        for (int i=0; i<=target; i++) {
            a[0][i] = boundary;
        }
        a[0][1] = 1;
        int flip = 1;
        
        for (int p=2;p>0;p++) {
        	String temp = "";
        	Integer weight = goods.get((p-1)%looplength).getKey();
        	Integer value = goods.get((p-1)%looplength).getValue()*valueweight;
            for (int q=target;q>=1;q--) {
            	a[1-flip][0] = 0;
            	if ( q >= weight)
            		a[flip][q] = (a[1-flip][q]>(a[1-flip][q-weight] + value))?(a[1-flip][q-weight] + value):a[1-flip][q];
            	else
            		a[flip][q] = a[1-flip][q];

                temp = a[flip][q].toString() + " " + temp; 
            }
            if ( a[flip][target] < boundary) {
            	result = a[flip][target];
            	break;
            }
            temp = weight.toString() + "      " + temp;
            temp = value.toString() + " " + temp;
            System.out.println(temp);
            flip = 1 - flip;
            if (p%looplength == 0)
            	valueweight = valueweight * current;
        }
               
        return result;
    }
}