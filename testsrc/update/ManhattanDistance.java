package org.bob.test.algorithm;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class ManhattanDistance extends TestFlow{

	static class Pair<Type>{
		Type x;
		Type y;
		static Integer dim = 2;
	}
	
	//private Map.Entry<Integer,Integer>[] dots;
	//private Pair<Integer>[] dots;
	private Integer[][] dots;
	private Integer dim = 3;
	
	public ManhattanDistance(Integer total, Integer uplimit){
		super(total,uplimit);
	}	
	
	public void data(){
		//with a inner class like 
		//class MyPair { private final Map.Entry<Integer,Integer> node}
		//then dots = new MyPair[num]
		//http://stackoverflow.com/questions/217065/cannot-create-an-array-of-linkedlists-in-java
		//dots = (Entry<Integer, Integer>[])new Map.Entry<?,?>[num];
		//dots = (Pair<Integer>[])new Pair<?>[total];
		dots = new Integer[total][dim];
		Random[] rd = new Random[dim];
		for(int i=0;i<total;i++){
			for(int j=0;j<dim;j++){
				rd[j] = new Random();
				dots[i][j] = rd[j].nextInt(uplimit) + 1;
			}
			//dots[i] = new Pair<Integer>();
		}
	}
	
	public void algorithm(){
		Integer dimision = 1<<dim;
		Integer[][] distance = new Integer[total][dimision];
		
		//求得X00000~X11111，下标是二进制
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < dimision; j++) {
                distance[i][j] = getSumByBits(dots[i], j);
            }
        }
		
        Integer result = Integer.MIN_VALUE;
        Integer MAXi = Integer.MIN_VALUE;
        Integer MINi = Integer.MAX_VALUE;
        int dot1i = -1; 
        int dot2i = -1;
        int dotMax = -1;
        int dotMin = -1;
        for (int i = 0; i < dimision; i++) {
            for (int j = 0; j < total; j++) {
                if (MAXi < distance[j][i]) {
                    MAXi = distance[j][i];
                    dot1i = j;
                }
                if (MINi > distance[j][i]) {
                    MINi = distance[j][i];
                    dot2i = j;
                }
            }
            //result = max(result, (MAXi - MINi));
            if(result<(MAXi-MINi)){
            	result = MAXi-MINi;
            	dotMax = dot1i;
            	dotMin = dot2i;
            }
            //result = result>=(MAXi-MINi)?result:MAXi-MINi;
            MAXi = Integer.MIN_VALUE;
            MINi = Integer.MAX_VALUE;
            dot1i = 0; 
            dot2i = 0;
        }
        System.out.println("the max distance is: " + result);
        for(int j=0;j<dim;j++){
			System.out.print(dots[dotMax][j] + ", ");
		}
        System.out.println();
        for(int j=0;j<dim;j++){
			System.out.print(dots[dotMin][j] + ", ");
		}
        System.out.println();
	}
	
	public void printResult(){
		for(int i=0;i<total;i++){
			System.out.print("the dot is: (");
			for(int j=0;j<dim;j++){
				System.out.print(dots[i][j] + ", ");
			}
			System.out.println();
		}
	}
	
	//0表示正数，1表示负数
    private Integer getSumByBits(Integer[] a, Integer s) {
        //long result = 0L;
    	Integer result = 0;
        for (int i = 0; i < dim; i++) {
            if (exist(s, i)) {
                result -= a[i];
            } else {
                result += a[i];
            }
        }
        return result;
    }
	
	//value的二进制表示，在第offset位是否为1
    private boolean exist(int value, int offset) {
        //return (value & (1 << offset)) == 1;
        return (value & (1 << offset)) != 0;
    }
    
    private Integer max(Integer x, Integer...yy) {
    	Integer result = x;
        for(Integer y : yy) {
            if (result < y) {
                result = y;
            }
        }
        return result;
    }
}
