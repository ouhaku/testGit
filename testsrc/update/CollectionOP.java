package org.bob.test.syntax;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class CollectionOP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,String> linkedhashmap = new LinkedHashMap<Integer, String>(10,(float)0.75,true);
		linkedhashmap.put(1, "one");
		linkedhashmap.put(2, "two");
		linkedhashmap.put(3, "three");
		linkedhashmap.put(4, "four");
		/*Iterator itr = linkedhashmap.keySet().iterator();
		while(itr.hasNext()){
			Object key = itr.next();
			System.out.println(linkedhashmap.get(key));
		}*/
		Iterator<Entry<Integer,String>> itr = linkedhashmap.entrySet().iterator();
		while(itr.hasNext()){
			System.out.println(itr.next().getValue());
		}
		
		linkedhashmap.get(2);
		linkedhashmap.get(3);
		linkedhashmap.get(2);
		Iterator itr1 = linkedhashmap.values().iterator();
		while(itr1.hasNext()){
			System.out.println(itr1.next());
		}
	}

}
