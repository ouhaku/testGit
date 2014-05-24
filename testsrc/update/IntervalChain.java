package org.bob.test.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
//import java.util.Map.Entry;

public class IntervalChain {
	public static MyEntry[] intervals;
	public static ArrayList<MyEntry> result;
	//public static Set<Map.Entry<Integer, Integer>> result;
	//public static ArrayList<Map.Entry<Integer, Integer>> intervalslist;
	
	public static class MyEntry implements Map.Entry<Integer, Integer>{

		private Integer key;
		private Integer value;
		@Override
		public Integer getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public Integer getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public Integer setValue(Integer value) {
			// TODO Auto-generated method stub
			return this.value = value;
		}
		
		public Integer setKey(Integer key) {
			// TODO Auto-generated method stub
			return this.key = key;
		}
		
		public void setInterval(Integer key, Integer value) {
			// TODO Auto-generated method stub
			this.key = key;
			this.value = value;
		}

	}
	
	public static class MyComparator implements Comparator<MyEntry>{
		public int compare(MyEntry op1, MyEntry op2){
			return op1.getValue()>op2.getValue()?1:(op1.getValue()==op2.getValue()?0:-1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initial();
		MyComparator com = new MyComparator();
		Arrays.sort(intervals, com);
		//java.util.Arrays.sort(intervalslist.toArray(intervals), com);
		Integer curlast = intervals[0].getValue();
		result.add(intervals[0]);
		for (int i=1;i<intervals.length;i++){
			if(intervals[i].getKey()>=curlast){
				result.add(intervals[i]);
				curlast = intervals[i].getValue();
			}				
		}
		Iterator<MyEntry> itr = result.iterator();
		while(itr.hasNext()){
			MyEntry tmp = (MyEntry)itr.next();
			System.out.print("["+tmp.getKey()+","+tmp.getValue()+"],");
		}
		System.out.println("");
		for(Map.Entry<Integer, Integer> tmp: result){
			System.out.print("["+tmp.getKey()+","+tmp.getValue()+"],");
		}
	}
	
	public static void initial(){
		intervals = new MyEntry[10];
		result = new ArrayList<MyEntry>();
		//result = new HashSet<Map.Entry<Integer, Integer>>();
		//MyComparator[] test = new MyComparator[10];
		for(int i=0;i<intervals.length;i++){
			intervals[i] = new MyEntry();
		}
		intervals[0].setInterval(9,13);
		intervals[1].setInterval(1,5);	
		intervals[2].setInterval(0,2);
		intervals[3].setInterval(2,5);
		intervals[4].setInterval(0,2);
		
		intervals[5].setInterval(4,7);
		
		
		intervals[6].setInterval(6,10);
		intervals[7].setInterval(2,8);
		intervals[8].setInterval(5,7);
		intervals[9].setInterval(3,4);
	}

}
