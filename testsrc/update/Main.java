package org.bob.test.algorithm;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;
import java.util.Date;

public class Main {

	//static InputStream is;	
	//static String INPUT = "";
	/*static String INPUT = "6\r\n" + 
	"3 4 2 2 4 3\r\n" + 
	"2 7 5 4 6 5\r\n" + 
	"5 5 4 3 4 3\r\n" + 
	"8 1 9 8 3 2\r\n" + 
	"1 4 2 2 5 5\r\n" + 
	"20 20 9 8 8 7";*/
	
	static String INPUT = 
		"1\r\n"+
		"4\r\n"+
		"1234\r\n"+
		"1234\r\n"+
		"1234\r\n"+
		"1234J\r\n";

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		//is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes())
		long start = System.currentTimeMillis();;
		Scanner sc = INPUT.isEmpty() ? new Scanner(System.in): new Scanner(INPUT);
		solve(sc);	
		sc.close();
		long end = System.currentTimeMillis();
	}
	
	/*public static void solve(Scanner sc){
		int N = sc.nextInt();
		for(int t=0;t<N;t++){
			int R = sc.nextInt(), B = sc.nextInt(), rn = sc.nextInt(), 
			rk = sc.nextInt(), bn = sc.nextInt(), bk = sc.nextInt();
			
			int mod = 1000000009;			
			int con = 1<<Math.max(rn, bn);
			
			long[][][] dp = new long[R+1][B+1][con];
			long total = 0;
			dp[0][0][0] = 1;
			
			for(int i = 0;i <= R;i++){
				for(int j = 0;j <= B;j++){
					for(int k = 0;k < con;k++){
						if(dp[i][j][k] == 0)
							continue;
						if(i+1 <= R){
							int red = Integer.bitCount(k&(1<<Math.min(rn-1, i+j))-1);
							if(red+1 < rk){
								dp[i+1][j][(k<<1|1)&(con-1)] = (dp[i+1][j][(k<<1|1)&(con-1)] + dp[i][j][k])%mod;
							}
						}
						if(j+1 <= B){
							int blue = Integer.bitCount(~k&(1<<Math.min(bn-1, i+j))-1);
							if(blue+1 < bk){
								dp[i][j+1][(k<<1|0)&(con-1)] = (dp[i][j+1][(k<<1|0)&(con-1)] + dp[i][j][k])%mod;
							}
						}
					}
				}
			}
			for(int k = 0;k < con;k++){
				total = (total + dp[R][B][k])%mod;
			}
			
			System.out.println(total%mod);
		}
	}*/

	static void solve(Scanner sc){
        int T = sc.nextInt();
        for(int t=0;t<T;t++){
            int player = sc.nextInt();
            //sc.nextLine();
            int num = 0;
            ArrayList<LinkedList<Character>> game = new ArrayList<LinkedList<Character>>();
            for(int i=0;i<player;i++){                
                char[] line = sc.next().toCharArray();
                LinkedList<Character> one = new LinkedList<Character>();
                for(int j=0;j<line.length;j++){
                    one.add(line[j]);
                    num++;
                }
                game.add(one);
            }
            
            int cur = 0;
            int next = (cur + 1)%game.size();
            int round = 0;
            int cycle = 0;
            
            while(game.size()>1){
                round++;
                LinkedList<Character> src = game.get(next);
                Character card = src.pollFirst();
                if(src.size()==0){
                    game.remove(next);
                    if(next==0)
                        cur = cur - 1;
                    if(next>=game.size())
                        next = 0;
                }
                LinkedList<Character> dst = game.get(cur);
                if(dst.removeFirstOccurrence(card)){
                    cycle = 0;
                    if(dst.size()==0){
                        game.remove(cur);
                        if(next>=game.size())
                            next = next -1;
                    }
                }else{
                    dst.addLast(card);
                    cycle++;
                }
                if(cycle>num){
                    round = -1;
                    break;
                }
                cur = next;
                next = (next+1)%game.size();
                System.out.println(game.toString());
            }
            System.out.println(round);
        }        
    }
}
