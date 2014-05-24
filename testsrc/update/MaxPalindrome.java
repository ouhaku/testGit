package org.bob.test.algorithm;

public class MaxPalindrome {
	
	public static void main(String[] args){
		String raw = "babcbabcbaccba";
		
		String modified = preprocess(raw);
		char[] target = modified.toCharArray();
		int[] palind = new int[target.length];
		int center = 0;
		int Redge = 0;
		palind[0] = 1;
		for(int i=1;i<target.length;i++){
			palind[i] = Redge > i ? min(Redge-i, palind[center-(i-center)]) : 1;
			while((i-palind[i]>=0 && i+palind[i]<=target.length-1) && target[i+palind[i]]==target[i-palind[i]]){
				palind[i]++;
				/*if(i-palind[i]<0 || i+palind[i]>target.length-1)
					break;*/
			}
			if(i+palind[i]>Redge){
				Redge = i + palind[i];
				center = i;
			}
		}
		
		int max=Integer.MIN_VALUE;
		int idx = 0;
		for(int i=0;i<palind.length;i++)
			if(palind[i]>max){
				max = palind[i];
				idx = i;
			}
		if(target[idx]=='#')
			System.out.println("center: " + target[idx-1]+ target[idx+1]+ " length: " + palind[idx]/2);
		else
			System.out.println("center: " + target[idx]+ " length: " + (palind[idx]-1)/2);
	}
	
	public static int min(int a, int b){
		return a>b?b:a;
	}
	
	public static String preprocess(String raw) {
		char[] bits = raw.toCharArray();
		StringBuilder edit = new StringBuilder();
		String dump = "";
		String delim = "#";
		for(int i=0;i<bits.length;i++){
			edit.append(delim);
			edit.append(bits[i]+dump);			
		}
		edit.append(delim);
		return edit.toString();
	}

}
