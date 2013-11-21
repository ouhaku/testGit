package org.bob.test.algorithm;

import java.util.AbstractMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class FindAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] target = {100,60,40,50,40,55,80,60,50,30,35,20,15,20,30,70};
		Map<Map.Entry<Integer,Integer>,Map.Entry<Integer,Integer>> result = findLeft(target);
		//String str="";
		for(Entry<Map.Entry<Integer,Integer>,Map.Entry<Integer,Integer>> i : result.entrySet()){
			System.out.println("left side: " + i.getKey().getKey() + " right side: " + i.getValue().getKey());
		}

	}
	
	public static Map<Map.Entry<Integer,Integer>,Map.Entry<Integer,Integer>> findLeft(Integer[] target){
		Map.Entry<Integer,Integer> current,leftside;
		Stack<Map.Entry<Integer,Integer>> buffer = new Stack<Map.Entry<Integer,Integer>>();
		Map<Map.Entry<Integer,Integer>,Map.Entry<Integer,Integer>> result = new HashMap<Map.Entry<Integer,Integer>,Map.Entry<Integer,Integer>>();
		current = null;
		if(target[0]>target[1]){
			current = new AbstractMap.SimpleEntry<Integer,Integer>(0,target[0]);
			buffer.add(current);
		}

		for (int i=1;i<target.length;i++){
			while(!buffer.isEmpty()&& buffer.peek().getValue() <= target[i]){				
				leftside = new AbstractMap.SimpleEntry<Integer,Integer>(i,target[i]);
				result.put(buffer.pop(), leftside);				
			}
			if(i==target.length-1)
				break;
			if((target[i]>=target[i-1] && target[i]>target[i+1])||
			   (target[i]>target[i-1] && target[i]>=target[i+1])){
				current = new AbstractMap.SimpleEntry<Integer,Integer>(i,target[i]);
				buffer.add(current);
			}						
		}
		
		/*if(target[0]>target[1]){
			current = new AbstractMap.SimpleEntry<Integer,Integer>(0,target[0]);
			buffer.add(current);
		}*/
		
		return result;
	}

}
