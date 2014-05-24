package org.bob.test.indeed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class JobGenerator {

	/**
	 * @param args
	 */
	private static String[] company = new String[10];
	private static String[] position = new String[10];
	private static String[] location = new String[10];
	
	private static String[] keywords = {"A","B","C","D","E","F","G","H",
        "I","J","K","L","M","N","O","P","Q",
        "R","S","T","U","V","W","X","Y","Z",
        "a","b","c","d","e","f","g","h","i",
        "j","k","l","m","n","o","p","q","r",
        "s","t","u","v","w","x","y","z"};
	
	static {
		for(int i=0;i<company.length;i++){
			company[i] = "company" + i;
			//System.out.println(company[i] + company[i].hashCode());
		}
		for(int i=0;i<position.length;i++){
			position[i] = "position" + i;
		}
		for(int i=0;i<location.length;i++){
			location[i] = "location" + i;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer num=10000;
		Integer size,index;
		File jobs = new File("./jobs.txt");
		Random rdc = new Random();
		Random rdp = new Random();
		Random rdl = new Random();
		Random rds = new Random();
        Random rdi = new Random();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(jobs));
			String delim = " ";
			String keydelim = ",";
			String enter = "\r\n";
			StringBuilder job = new StringBuilder();
			HashSet<Integer> indexs = new HashSet<Integer>();
			Iterator itr = indexs.iterator();
			for(int i=0;i<num;i++){
				job.append(i+delim);
				job.append(company[rdc.nextInt(company.length)]);
				job.append(delim);
				job.append(position[rdp.nextInt(position.length)]);
				job.append(delim);
				job.append(location[rdl.nextInt(location.length)]);
				
				job.append(delim);
				//to use Set avoids generating same random number as index
				size = rds.nextInt(26) + 1;
				while(indexs.size()<size){
                    index = rdi.nextInt(keywords.length);
                    indexs.add(index);
                }
                itr = indexs.iterator();
                while(itr.hasNext()){
                    job.append(keywords[(Integer)itr.next()]);
                    job.append(keydelim);
                }
				
				job.append(enter);
				writer.write(job.toString());
				writer.flush();
				job.delete(0,job.length());
				indexs.clear();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//writer.close();
		}
	}

}
