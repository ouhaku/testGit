package org.bob.test.indeed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JobHasher {

	/**
	 * @param args
	 */
	//private static Map<String, Integer> statistics = new HashMap<String,Integer>();
	private static Map<String, ArrayList<String[]>> statistics = new HashMap<String,ArrayList<String[]>>();
	private static Map<String, ArrayList<String>> jobid = new HashMap<String,ArrayList<String>>();
	private static Map<String, ArrayList<Integer>> detailnum = new HashMap<String,ArrayList<Integer>>();
	private static Map<String, Map<String,Integer>> detailtoidx = new HashMap<String,Map<String,Integer>>();
	private static Map<String, Set<String>> jobdetail = new HashMap<String,Set<String>>();
	private static Map<String, ArrayList<boolean[]>> jobbitmap = new HashMap<String,ArrayList<boolean[]>>();
	private static ArrayList<Map<String, ArrayList<boolean[]>>> forthreads = new ArrayList<Map<String, ArrayList<boolean[]>>>();
	private static Integer total = 0;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File src = new File("./jobs.txt");
		Integer todivide = 2;
		String key = "";
		String[] target;
		String[] details;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(src));
			String line = reader.readLine();
			//String dump = "";
			
			while(line!=null){
				total++;
				target = line.split(" ");
				//join the first 3 segment as key, the last segment is the keys
				for(int i=1;i<4;i++){
					//key += target[i].hashCode();
					key += target[i];
				}
				/*if(statistics.containsKey(key)){
					statistics.get(key).add(target[0] + "," + target[target.length-1]);
				}else{
					ArrayList<String> details = new ArrayList<String>();
					details.add(target[0] + "," + target[target.length-1]);
					statistics.put(key, details);
				}*/
				details = target[target.length-1].split(",");
				
				if(jobdetail.containsKey(key)){
					for(String temp:details){
						jobdetail.get(key).add(temp);
					}
					
					statistics.get(key).add(details);
					
					jobid.get(key).add(target[0]);
					detailnum.get(key).add(details.length);
				}else{
					Set<String> holder = new HashSet<String>();
					for(String temp:details){
						holder.add(temp);
					}
					jobdetail.put(key, holder);
					
					ArrayList<String[]> one = new ArrayList<String[]>();
					one.add(details);
					statistics.put(key, one);
					
					ArrayList<String> ids = new ArrayList<String>();
					ids.add(target[0]);
					jobid.put(key, ids);
					
					ArrayList<Integer> num = new ArrayList<Integer>();
					num.add(details.length);
					detailnum.put(key, num);
				}
				key = "";
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator itr;
        Integer idx;
		for(Map.Entry<String, Set<String>> entry:jobdetail.entrySet()){
			itr = entry.getValue().iterator();
			idx = 0;
			Map<String,Integer> indexs = new HashMap<String,Integer>();
			while(itr.hasNext()){
	            indexs.put((String)itr.next(), idx);
	            idx++;
	        }
			detailtoidx.put(entry.getKey(), indexs);
		}
		
		long startMili, endMili;
        System.out.println(Thread.currentThread().getName()+" generate bitmap begin: ");
        startMili=System.currentTimeMillis();
		
        Integer current = 0;
		for(Map.Entry<String,ArrayList<String[]>> entry: statistics.entrySet()){
			current += entry.getValue().size();
			itr = entry.getValue().iterator();
			key = entry.getKey();
			while(itr.hasNext()){
				boolean[] record = new boolean[jobdetail.get(key).size()];
				details = (String[])itr.next();
				for(String detail:details){
                    record[detailtoidx.get(key).get(detail)] = true;
                }
				if(jobbitmap.containsKey(key)){
					jobbitmap.get(key).add(record);
				}else{
					ArrayList<boolean[]> onebitmap = new ArrayList<boolean[]>();					
					onebitmap.add(record);
					jobbitmap.put(key, onebitmap);
				}
			}
			if(current > total/todivide){
				forthreads.add(jobbitmap);
				jobbitmap = new HashMap<String,ArrayList<boolean[]>>();
				current = 0;
			}			
		}
		forthreads.add(jobbitmap);
		
		/*try {
			BufferedReader reader = new BufferedReader(new FileReader(src));
			String line = reader.readLine();
			//String dump = "";
			while(line!=null){
				target = line.split(" ");
				//join the first 3 segment as key, the last segment is the keys
				for(int i=1;i<4;i++){
					//key += target[i].hashCode();
					key += target[i];
				}
				
				boolean[] record = new boolean[jobdetail.get(key).size()];
				details = target[target.length-1].split(",");
				for(String detail:details){
                    record[detailtoidx.get(key).get(detail)] = true;
                }
				if(jobbitmap.containsKey(key)){
					jobbitmap.get(key).add(record);
				}else{
					ArrayList<boolean[]> onebitmap = new ArrayList<boolean[]>();					
					onebitmap.add(record);
					jobbitmap.put(key, onebitmap);
				}

				key = "";
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		endMili=System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName()+" using time: " + (endMili-startMili));
        
        for(int i=0;i<forthreads.size();i++){
        	MyHandler worker = new MyHandler(forthreads.get(i));
        	new Thread(worker).start();
        }
				
		/*Integer num = 0;
		Integer max = Integer.MIN_VALUE;
		Integer temp;
		for(Map.Entry<String,ArrayList<String>> entry: jobid.entrySet()){
			//System.out.println(entry.getKey() + " contains: " + entry.getValue().size());
			//itr = entry.getValue().iterator();
			//while(itr.hasNext()){
			//	System.out.println(itr.next().toString());
			//}
			temp = entry.getValue().size();
			num += temp;
			max = temp>max?temp:max;
		}
		System.out.println("total jobs: " + num);
		System.out.println("Max item: " + max);*/
	}
	
	static class MyHandler implements Runnable{

		private Map<String, ArrayList<boolean[]>> jobbitmap;
		
		public MyHandler(Map<String, ArrayList<boolean[]>> jobbitmap){
			this.jobbitmap = jobbitmap;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			long startMili, endMili;
			Integer mini = Integer.MAX_VALUE;
	        Integer minj = Integer.MAX_VALUE;
	        Integer min;
	        Integer same = 0;
	        Integer idxone = 0;
	        Integer idxtwo = 0;
	        Integer gap;
	        boolean[] tocompare;
	        boolean[] compared;
	        String filename = "part_" + Thread.currentThread().getName() + ".txt";
	        String path = "./";
	        String key = "";
	        File dest = new File(path + filename);
	        
	        System.out.println(Thread.currentThread().getName()+" match begin: ");
	        startMili=System.currentTimeMillis();
	        try {
	            BufferedWriter writer  = new BufferedWriter(new FileWriter(dest));
	            //itr = result.iterator();
	            //String delim = ",";
	            String enter = "\r\n";
	            StringBuilder line = new StringBuilder();
				for(Map.Entry<String, ArrayList<boolean[]>> entry: jobbitmap.entrySet()){
					idxone = 0;
					idxtwo = 0;
					gap = entry.getValue().size();
					key = entry.getKey();
					for(int i=idxone;i<idxone+gap;i++){
		                tocompare = entry.getValue().get(i);
		                mini = detailnum.get(key).get(i);
		                //int idx = idxtwo.equals(idxone)?i+1:idxtwo;
		                for(int j=(idxtwo.equals(idxone)?i+1:idxtwo);j<idxtwo+gap;j++){
		                    minj = detailnum.get(key).get(j)<mini?detailnum.get(key).get(j):mini;
		                    compared = entry.getValue().get(j);
		                    for(int k=0;k<tocompare.length;k++){
		                        if(tocompare[k]&&compared[k]){
		                            same++;
		                        }                        
		                    }
		                    min = (minj+1)/2;
		                    if(same>=min){
		                    	//System.out.println(jobid.get(key).get(i) + ", " + jobid.get(key).get(j));
		                    	 line.append("[");
		                            line.append(jobid.get(key).get(i));
		                            line.append(", ");
		                            line.append(jobid.get(key).get(j));
		                            line.append("]");
		                            writer.write(line.toString());
		                            writer.write(enter);
		                            writer.flush();
		                            line.delete(0, line.length());
		                    }
		                    same = 0;
		                }
					}
				}
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        endMili=System.currentTimeMillis();
	        System.out.println(Thread.currentThread().getName()+" using time: " + (endMili-startMili));
		}
		 
	}

}
