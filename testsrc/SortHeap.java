package org.bob.test.algorithm;

import java.util.Random;

public class SortHeap {
	private static Random rand = new Random();
	
	public static void main(String[] args){
		//Integer size = Integer.parseInt(args[0]);
		//Integer uplimit = Integer.parseInt(args[1]);
		Integer[] target = createRandom(5000000, 100000000);
		//Integer[] target = {4,17,13,2,2,19,15,13,7,3};
		
		Integer[] refer = target.clone();
		long startMili, endMili;
        System. out.println("quick sort begin: ");
        startMili=System.currentTimeMillis();
        java.util.Arrays.sort(refer);
        endMili=System.currentTimeMillis();
        System. out.println("from sort middle number is " + refer[target.length/2-1] + " using time: " + (endMili-startMili));
		
		/*String ref = "";
		for(int i=0; i<target.length; i++)
			ref = ref + " " + target[i];
		System.out.println("original: ");
		System.out.println(ref);*/
		
        System. out.println("random quick sort begin: ");
        startMili=System.currentTimeMillis();
		randomQuickSort(target,0,target.length-1);
		endMili=System.currentTimeMillis();
        System. out.println("from sort middle number is " + refer[target.length/2-1] + " using time: " + (endMili-startMili));
		/*ref = "";
		for(int i=0; i<target.length; i++)
			ref = ref + " " + target[i];
		System.out.println("quick sort: ");
		System.out.println(ref);*/
		
		
		//int heapsize = target.length - 1;
		//makeHeap(target, heapsize);
		
		/*System. out.println("heap sort begin: ");
        startMili=System.currentTimeMillis();
		heapSort(target, heapsize);
		endMili=System.currentTimeMillis();
        System. out.println("from sort middle number is " + refer[target.length/2-1] + " using time: " + (endMili-startMili));*/
	}
	
	public static Integer[] createRandom(int size, int uplimit) {
		Random rd = new Random();
		Integer[] generated = new Integer[size];
		for (int i=0;i<size;i++){
			generated[i] = rd.nextInt(uplimit) + 1;
		}
		return generated;
	}
	
	public static void makeHeap(Integer[] target, int heapsize){
		for(int i = (heapsize-1)/2;i>=0;i--){
			maxHeapify(target,heapsize,i);
		}
	}
	
	public static void maxHeapify(Integer[] target, int heapsize, int index){
		if (index*2 + 1> heapsize)
			return;
		int largest = index;
		if (target[index] < target[index*2 + 1])
			largest = index*2 + 1;
		if ((index*2 + 2 <= heapsize)&& target[largest] < target[index*2 + 2])
			largest = index*2 + 2;
		if (largest == index)
			return;

		Integer temp = target[index];
		target[index] = target[largest];
		target[largest] = temp;
		maxHeapify(target,heapsize,largest);
		return;
	}
	
	public static void heapSort(Integer[] target, int heapsize){
		makeHeap(target,heapsize);
		for(int i = heapsize; i>0; i--){
			Integer temp = target[0];
			target[0] = target[heapsize];
			target[heapsize] = temp;
			heapsize--;
			maxHeapify(target,heapsize,0);
		}
	}
	
	public static int randomPartition(Integer[] target, int lowlimit, int uplimit){
		int index = rand.nextInt(uplimit-lowlimit+1) + lowlimit;
		Integer temp = target[index];
		target[index] = target[uplimit];
		target[uplimit] = temp;
		return partition(target,lowlimit,uplimit);
	}
	
	public static int partition(Integer[] target, int lowlimit, int uplimit){
		Integer pivot = target[uplimit];
		int lowrange = lowlimit - 1;
		for(int i=lowlimit;i<uplimit;i++){
			if (target[i] <= pivot){
				lowrange++;
			    if (lowrange==i)
			    	continue;
				Integer temp = target[i];
				target[i] = target[lowrange];
				target[lowrange] = temp;
			}
		}
		Integer temp = target[uplimit];
		target[uplimit] = target[lowrange+1];
		target[lowrange+1] = temp;
		return lowrange+1;
	}
	
	public static void randomQuickSort(Integer[] target, int lowlimit, int uplimit) {
		if (lowlimit < uplimit){
			int pivot = randomPartition(target, lowlimit, uplimit);
			randomQuickSort(target, lowlimit, pivot-1);
			randomQuickSort(target, pivot+1, uplimit);
		}
	}
}
