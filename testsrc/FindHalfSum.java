package org.bob.test.algorithm;

import java.util.Random;

public class FindHalfSum {
	public static void main(String[] args){
		//Integer[] target = createRandom(10,100);
		Integer[] target = {3, 4, 5, -3, 100, 1, 89, 54, 23, 20};
		findHalfSum(target);
	}
	
	public static Integer[] createRandom(int size, int uplimit){
		Random rd = new Random();
		Integer[] target = new Integer[size];
		for (int i = 0; i<uplimit; i++) {
			target[i] = rd.nextInt(uplimit) + 1;
		}
		return target;
	}
	
	public static void findHalfSum(Integer[] target){
		java.util.Arrays.sort(target);
		Integer sum=0,size=0;
		for (int i=0;i<target.length;i++)
			sum+=target[i];
		sum = sum/2;
		size = target.length/2;
		Integer current=0;
		for (int i=0;i<size;i++)
			current+=target[i];
		int idxlow=0,idxhigh=size-1;
		int idxback=0;
		boolean compact=true;
		while(current<sum){
			if(compact){
				idxback = idxhigh;
				idxhigh++;
				current = current - target[idxhigh-1] + target[idxhigh];				
				compact = false;
			}else{
				if(target[idxlow] + target[idxhigh+1]<=target[idxback+1] + target[idxback]){
					idxhigh++;
					current = current - target[idxhigh-1] + target[idxhigh];
				}else{
					current = current - target[idxlow] - target[idxhigh] + target[idxback+1] + target[idxback];
					idxlow++;
					idxback++;
					idxhigh = idxback;
					compact=true;
				}					
			}
		}
		
		System.out.println("nearest sum is: " + current);
		String ref = "";
		for(int i=idxlow; i<idxback; i++)
			ref = ref + " " + target[i];
		if(!compact){
			ref = ref + target[idxhigh];
		}
		System.out.println("set is: ");
		System.out.println(ref);
		return;
	}
}
