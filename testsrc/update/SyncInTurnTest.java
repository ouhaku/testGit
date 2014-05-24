package org.bob.test.syntax;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SyncInTurnTest {

	private static Map<ObjectOutputStream,String> current = new HashMap<ObjectOutputStream, String>();
	private static Map<String,String> match = new HashMap<String,String>();
	{
		String[] temp = {"A","B","C","D"};
		for (int i=0;i<temp.length;i++){
			try {
				match.put(temp[i], temp[(i+1)%temp.length]);
				OutputStream tofile = new FileOutputStream("./"+temp[i]+".txt");
				ObjectOutputStream oo = new ObjectOutputStream(tofile);
				current.put(oo, temp[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static class MyThread extends Thread {
		private String toprint;
		private String next;
		private Integer count = 10;
		public MyThread(String target) {
			toprint = target;
			setName(toprint);
			setNext(toprint);
		}
		
		public void setNext(String next){
			this.next = match.get(next);
		}
		//overwritten
		public void run(){
			System.out.print("Thrend"+getName()+": ");
			while(count>0){
				//synchronized(this) {
					for (Entry<ObjectOutputStream, String> one: current.entrySet()){
						if(getName()!= null && getName().equals(one.getValue())){
							try {
								one.getKey().writeObject(getName());
								System.out.print(getName());
								one.setValue(match.get(getName()));
								count--;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				//}
			}
			System.out.print("");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Thread A = new Thread(){
			public void run(){
				
			}
		};*/
		SyncInTurnTest todo = new SyncInTurnTest();
		String[] temp = {"A","B","C","D"};
		for (int i=0;i<temp.length;i++){
			new MyThread(temp[i]).start();
		}
		
		
	}

}
