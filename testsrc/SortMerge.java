package org.bob.test.algorithm;

import java.util.Random;

public class SortMerge {
	
	public static void main(String[] args){
		int total = 1000000;
        int uplimit = 10000000;
        long startMili, endMili;
        
		Integer[] target = createRandom(total,uplimit);
		Integer[] refer = target.clone();
		
		System. out.println("quick sort begin: ");
        startMili=System.currentTimeMillis();
        java.util.Arrays.sort(refer);
        endMili=System.currentTimeMillis();
        System. out.println("using time: " + (endMili-startMili));
        
        System. out.println("merge sort begin: ");
        startMili=System.currentTimeMillis();
		MergeSort(target,0,target.length);
		endMili=System.currentTimeMillis();
        System. out.println("using time: " + (endMili-startMili));
		/*String con = "";
        String ref = "";
        for (int i=0; i<target.length;i++){
            con = con + " " + target[i];
            ref = ref + " " + refer[i];
            if ((i+1)%30==0) {
                System. out.println("original is " +ref );
                System. out.println("sorted   is " +con );
                con = "";
                ref = "";
            }
        }
        System. out.println("original is " +ref );
        System. out.println("sorted   is " +con );*/
		
	}
	
	/*public Integer[] Merge(Integer[] small, Integer[] big){
		return 
	}*/
	
	public static void MergeSort(Integer[] target, int start, int end){
		if(start + 1 < end){
			int mid = (start + end)/2;
			MergeSort(target,start,mid);
			MergeSort(target,mid,end);
			MergeInPlace(target,start,end,mid);
		}
		if(start + 1 == end && end!=target.length) {
			if(target[start] > target[end]){
				Integer temp = target[start];
				target[start] = target[end];
				target[end] = temp;
			}
		}
		return;
		//return target;
	}
	
	public static Integer[] MergeInPlace(Integer[] target, int start, int end, int boundary){
		if (start >= boundary)
			return target;
		if (boundary >= end)
			return target;
		int i = start;
		int j = boundary;
		while(i<j && j<end){
			while (target[i] <= target[j] && i < boundary){
				i++;			
			}
			if (i == boundary)
				break;
			while (target[j] <= target[i] && j < end) {
				j++;
			}
			exchange(target, i, j, boundary);
			i = i + j - boundary;
			boundary = j;
		}
		
		/*if (i+j-boundary < j && j < end) {
			MergeInPlace(target, i+j-boundary, end, j);
		}*/
		return  target;
	}
	
	public static Integer[] exchange(Integer[] target, int start, int end, int boundary){
		reverse(target, start, boundary-start);
		reverse(target, boundary, end-boundary);
		reverse(target, start, end-start);
		return target;
	}
	
	public static Integer[] reverse(Integer[] target, int start, int length) {
		int i = start;
		int j = start + length - 1;
		Integer temp;
		while (i<j) {
			temp = target[i];
			target[i] = target[j];
			target[j] = temp;
			i++;
			j--;
		}
		return target;
	}

	public static Integer[] createRandom (int total, int uplimit){
        Random rd = new Random();
        Integer[] result = new Integer[total];
        for (int i = 0; i<total; i++) {
            result[i] = rd.nextInt(uplimit) + 1;
        }
        //Integer[] temp = {400,417,288,292,987};
        //Integer[] temp = {795,803,607,23,182};
        //Integer[] temp = {789,531,655,283,544};
        //Integer[] temp = {3,9,10,6,9,7,3,6,5,5};
        //Integer[] temp = {19,87,39,4,27,29,48,29,83,37};
        //Integer[] temp = {27,11,17,23,31 ,63, 86, 99, 18, 55, 41};
        //return temp;
        return result;
    }
}
