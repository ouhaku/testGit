package org.bob.test.algorithm;

import java.util.Random;

public class BagFinite {
	
	private Integer[] nums;

	/**
	 * @param args
	 */
	public BagFinite(Integer[] val) {
		nums = new Integer[val.length];
		nums = val;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int total = Integer.parseInt(args[0]);
		int uplimit = Integer.parseInt(args[1]);
		//bagFinite(total, uplimit);
		Integer coin[] = {5,9,11,8};
		int value = 913;
		bagInfinite(coin, value);

		//BagFinite bag = new BagFinite(createRandom(total, uplimit));
		
		
	}
	
	public static void bagFinite(Integer total, Integer uplimit){
		Integer[] bag = createRandom(total,uplimit);
		int sum = 0;
		String con = "";
		for (int i=0; i<bag.length;i++){
			sum = sum + bag[i];
			con = con + " " + bag[i];
			if ((i+1)%30==0) {
				System.out.println(con );
				con = "";
			}
		}
		System.out.println(con );
		System.out.println("sum is " + sum );
		sum = (Integer)sum/2;
		
		Integer a[][] = new Integer[2][sum+1];
		boolean trace[][] = new boolean[bag.length][sum+1];
		
		for (int i=0; i<=sum; i++) {
            a[0][i] = 0;
        }
		
		con = "";
		int flip = 1;
		
		
		for (int p=0;p<bag.length;p++) {
			for (int q=0;q<=sum;q++) {
				if (bag[p]<=q)
					a[flip][q] = a[1-flip][q]> a[1-flip][q-bag[p]]+bag[p]?a[1-flip][q]:a[1-flip][q-bag[p]]+bag[p];
				else 
					a[flip][q] = a[1-flip][q];
				
				trace[p][q]= a[flip][q] == a[1-flip][q]?false:true;
				//con = con + " " + trace[p][q];
			}
			flip = 1 - flip;
			//System.out.println(con);
			//con = "";
		}
		
		for (int p=bag.length-1, start = sum;p>=0;p--) {
			if (trace[p][start] && start >=1){
				con = bag[p] + " " + con;
				start = start - bag[p];
			}
		}
		
		System.out.println("consititution is: " + con);
		System.out.println("result is " + a[1-flip][sum] );
	}
	
	public static void bagInfinite (Integer[] coin, Integer value){
		int type = coin.length;
		Integer boundary = 200000000;
		Integer a[][] = new Integer[2][value+1];
		int num[][] = new int[type][value+1];
		boolean trace[][] = new boolean[type][value+1];
		
		for (int i=0; i<=value; i++) {
            a[0][i] = boundary;
        }
		String con = "";
		int flip = 1;
		
		for (int p=0;p<type;p++) {
			a[1-flip][0] = 0;
			for (int q=value;q>=0;q--) {
				int suji = 0;
				while (coin[p] * suji <= q) {
					a[flip][q] = a[1-flip][q]>a[1-flip][q - coin[p]*suji] + suji?a[1-flip][q - coin[p]*suji] + suji:a[1-flip][q];
					suji++;
				}
				trace[p][q]= a[flip][q] == a[1-flip][q]?false:true;
				num[p][q] = a[flip][q] == a[1-flip][q]?0:(suji-1);
			}
			flip = 1 - flip;
		}
		
		if ( boundary.compareTo(a[1-flip][value]) == 0){
			System.out.println("can not match value!");
			return;
		}
		
		
		for (int p=type-1, start = value;p>=0;p--) {
			if (trace[p][start] && start >=1){
				con = coin[p] + " * " + num[p][start] + " + " + con;
				start = start - num[p][start]*coin[p];
			}
		}
		
		System.out.println("need coin: " + a[1-flip][value]);
		System.out.println("consititution is: " + con + " = " + value);

	}
	
	public static Integer[] createRandom (int total, int uplimit){
		Random rd = new Random();
		Integer[] result = new Integer[total];
		for (int i = 0; i<total; i++) {
			result[i] = rd.nextInt(uplimit) + 1;
		}
		return result;
	}

}
