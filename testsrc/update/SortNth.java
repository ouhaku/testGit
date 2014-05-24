package org.bob.test.algorithm;

import java.util.Random;

public class SortNth {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int total = Integer.parseInt(args[0]);
        int uplimit = Integer.parseInt(args[1]);
        int Nth = Integer.parseInt(args[2]);
        /*int reminder = uplimit%5;
        uplimit = uplimit + reminder;*/
        Integer[] bag = createRandom(total,uplimit);
        Integer[] refer = bag.clone();
        java.util.Arrays.sort(refer);
        /*while(reminder!=0){
            bag[bag.length-reminder] = Integer.MAX_VALUE;
            reminder--;
        }    */        
        
        
        String con = "";
        String ref = "";
        for (int i=0; i<bag.length;i++){
            con = con + " " + bag[i];
            ref = ref + " " + refer[i];
            if ((i+1)%30==0) {
                System.out.println("original is "+con );
                System.out.println("sorted   is "+ref );
                con = "";
                ref = "";
            }
        }
        System.out.println("original is "+con );
        System.out.println("sorted   is "+ref );
        //Integer Nth = bag.length/2 + bag.length%2;
        Integer result = findNthNumber(bag,Nth);
        System.out.println("middle number is " + result);

    }
    
    public static Integer findNthNumber (Integer[] original, Integer Nth) {
        if ( original.length < 5) {
        	java.util.Arrays.sort(original);
        	if (Nth <= original.length)
        		return original[Nth-1];
        	else
        		return original[(original.length+1)/2-1];
            /*switch (original.length){
                case 1:
                    return original[0];
                case 2:
                    return original[0]>original[1]?original[1]:original[0];
                case 3:
                {
                    int e = Integer.MAX_VALUE;
                    int a = original[0]>original[1]?original[1]:original[0];
                    int b = original[2]>e?e:original[2];
                    int c = a>b?a:b;
                    a = original[0]<original[1]?original[1]:original[0];
                    b = original[2]<e?e:original[2];
                    int d = a<b?a:b;
                    return c<d?c:d;
                
                }
                case 4:
                {
                    int a = original[0]>original[1]?original[1]:original[0];
                    int b = original[2]>original[3]?original[3]:original[2];
                    int c = a>b?a:b;
                    a = original[0]<original[1]?original[1]:original[0];
                    b = original[2]<original[3]?original[3]:original[2];
                    int d = a<b?a:b;
                    return c<d?c:d;
                }
                default:
                    return 0;
            }*/
                
        }
             
        int mod5 = original.length % 5;
        
        Integer middle[];
        Integer reminder[];
        if (mod5!=0) {
            reminder = new Integer[mod5];
            middle = new Integer[(original.length - mod5)/5 + 1];
            for (int j= 0; j < mod5; j++ )
                reminder[j] = original[original.length-mod5+j];
            middle[middle.length-1] = findNthNumber(reminder,(mod5+1)/2);
        } else {
            middle = new Integer[(original.length - mod5)/5];
        }
            
        //for (int j= 0; j < mod5; j++ )
            //middle[middle.length-1-j] = original[original.length-1-j];
        int big = 0;
        int midup = 0;
        int mid = 0;
        int middown = 0;
        int small = 0;
        // sort 5 numbers within at most 7 comparisons
        for (int i = 0; i < original.length - mod5;) {
            //first
            if (original[i]> original[i+1]){
                big = original[i];
                small = original[i+1];
            } else {
                big = original[i+1];
                small = original[i];
            }
            //second
            if (original[i+2]> original[i+3]){
                midup = original[i+2];
                middown = original[i+3];
            } else {
                midup = original[i+3];
                middown = original[i+2];
            }
            //third
            if (big > midup){
                //fourth
                if (original[i + 4] > midup) {
                    //fifth
                    if (original[i + 4] > big) {
                        mid = midup;
                        midup = big;
                        big = original[i+4];
                        //sixth
                        if (small > mid) {
                            int temp = mid;
                            mid = small;
                            small = middown;
                            middown = temp;
                        } else {
                            //seventh
                            if (small > middown) {
                                int temp = middown;
                                middown = small;
                                small = temp;
                            }
                        }
                    } else {
                        mid = midup;
                        midup = original[i+4];
                        //sixth
                        if (small > mid) {
                            //seventh
                            if (small > midup) {
                                int temp = midup;
                                midup = small;
                                small = middown;
                                middown = mid;
                                mid = temp;
                            } else {
                                int temp = mid;
                                mid = small;
                                small = middown;
                                middown = temp;
                            }
                        } else {
                            //seventh
                            if (small > middown) {
                                int temp = middown;
                                middown = small;
                                small = temp;
                            }
                        }
                    }
                } else {
                    //fifth
                    if (original[i + 4] > middown) {
                        mid = original[i+4];
                    } else {                    
                        mid = original[i+4];
                        mid = middown;
                        middown = original[i+4];
                    }
                    //sixth
                    if (small > mid) {
                        //seventh
                        if (small > midup) {
                            int temp = midup;
                            midup = small;
                            small = middown;
                            middown = mid;
                            mid = temp;
                        } else {
                            int temp = mid;
                            mid = small;
                            small = middown;
                            middown = temp;
                        }
                    } else {
                        //seventh
                        if (small > middown) {
                            int temp = middown;
                            middown = small;
                            small = temp;
                        }
                    }                    
                }
            } else {
                //fourth
                if (original[i + 4] > big) {
                    //fifth 
                    if (original[i + 4] > midup) {
                        mid = big;
                        big = original[i + 4];
                        //sixth
                        if (middown > mid) {
                            int temp = mid;
                            mid = middown;
                            middown = temp;
                        } else {
                            //seventh
                            if (small > middown) {
                                int temp = middown;
                                middown = small;
                                small = temp;
                            }
                        }
                    } else {
                        mid = big;
                        big = midup;
                        midup = original[i + 4];
                        //sixth
                        if (middown > mid) {
                            //seventh
                            if (middown > midup) {
                                int temp = midup;
                                midup = middown;
                                middown = mid;
                                mid = temp;
                            } else {
                                int temp = mid;
                                mid = middown;
                                middown = temp;
                            }
                        } else {
                            //seventh
                            if (small > middown) {
                                int temp = middown;
                                middown = small;
                                small = temp;
                            }
                        }                        
                    }
                } else {
                    //fifth
                    mid = midup;
                    midup = big;
                    big = mid;
                    if (original[i + 4] > small) {                    
                        mid = original[i+4];
                    } else {
                        mid = small;
                        small = original[i+4];
                    }
                    //sixth
                    if (middown > mid) {
                        //seventh
                        if (middown > midup) {
                            int temp = midup;
                            midup = middown;
                            middown = mid;
                            mid = temp;
                        } else {
                            int temp = mid;
                            mid = middown;
                            middown = temp;
                        }
                    } else {
                        //seventh
                        if (small > middown) {
                            int temp = middown;
                            middown = small;
                            small = temp;
                        }
                    }
                }                    
            }
            //System.out.println(original[i] + " " + original[i+1] + " " + 
                    //original[i+2]+ " " + original[i+3]+ " " + original[i+4] +  "  mid: " + mid);
            middle[i/5] = mid;
            big = 0;
            midup = 0;
            mid = 0;
            middown = 0;
            small = 0;
            i = i + 5;
        }
        
        mid = findNthNumber (middle, Nth);
        Integer[] pass = new Integer[original.length];
        int labelsmall = 0;
        int labelbig = 0;
        for (int i = 0;i<original.length;i++) {
        	if ( original[i] > mid)
        		if(original[i] == Integer.MAX_VALUE)
        			continue;
        		else {
        			pass[pass.length-1-labelbig] = original[i];
        			labelbig++;
        		}
        	else {
        		pass[labelsmall] = original[i];
        		labelsmall++;
        	}
        }
        /*if (labelsmall == Nth)
        	return mid;*/
        if (labelsmall > Nth) {
        	Integer[] temp = new Integer[labelsmall];
        	for (int i = 0;i<temp.length;i++)
        		temp[i] = pass[i];
        	mid = findNthNumber(temp, Nth);
        }
        if (labelsmall < Nth) {
        	if (pass.length - labelbig >= Nth)
        		return mid;
        	Integer[] temp = new Integer[labelbig];
        	for (int i = 0;i<temp.length;i++)
        		temp[i] = pass[pass.length-1-i];
        	mid = findNthNumber(temp, Nth-labelsmall);       	
        }
        return mid;
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
        //439 789 531 655 283
        //914 439 789 531 655 
        //return temp;
        return result;
    }

}
