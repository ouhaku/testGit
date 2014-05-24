package org.bob.test.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class SegmentTree {

    static class Node {
        Integer left = Integer.MAX_VALUE;
        Integer right = Integer.MIN_VALUE;
        Integer cover = 0;
        Node leftN = null;
        Node rightN = null;
    }
    
    static class Interval{
        Integer left;
        Integer right;
        public Interval(Integer a, Integer b){
            left = a;
            right = b;
        }
    }
    
    Node root;
    
    public SegmentTree(Integer l, Integer r){
        root = buildtree(l,r);        
    }
    
    public Node buildtree(Integer l, Integer r){
        Node n = new Node();
        n.left = l;
        n.right = r;
        if(l+1<=r){
            int mid = (l + r)/2;
            n.leftN = buildtree(l,mid);
            n.rightN = buildtree(mid+1,r);
        }
        return n;
    }
    
    public void addInterval(Node root, Integer l, Integer r){
        if(l>r)
            return;
        if(l<root.left)
            addInterval(root,root.left,r);
        if(r>root.right)
            addInterval(root,l,root.right);
        
        if(l.equals(root.left) && r.equals(root.right)){
            root.cover++;
        }else{
            int mid = (root.left + root.right)/2;
            if(r<=mid)
                addInterval(root.leftN,l,r);
            else if(l>mid)
                addInterval(root.rightN,l,r);
            else{
                addInterval(root.leftN,l,mid);
                addInterval(root.rightN,mid+1,r);
            }
        }
        return;
    }
    
    public void deleteInterval(Node root, Integer l, Integer r){
        
    }
    
    public Integer searchDot(Node root, Integer dot){
        if(root.leftN==null||root.rightN==null)
            return root.cover;
        if(dot<root.left || dot >root.right)
            return 0;
        /*if(dot.equals((root.left + root.right)/2)){
            return searchDot(root.leftN, dot) + searchDot(root.rightN, dot) + root.cover;
        }else */
        if(dot<=(root.left + root.right)/2){
            return searchDot(root.leftN, dot) + root.cover;
        }else{
            return searchDot(root.rightN, dot) + root.cover;
        }            
    }
    
    public Integer searchContain(Node root, Integer l, Integer r){
        if(root.leftN==null||root.rightN==null)
            return root.cover;
        if(l<root.left || r>root.right || l>=r)
            return 0;
        if(r<=(root.left + root.right)/2)
            return searchContain(root.leftN,l,r) + root.cover;
        else if(l>=(root.left + root.right)/2)
            return searchContain(root.rightN,l,r) + root.cover;
        else
            return root.cover;
        
    }
    
    public Integer searchOverlap(Node root, Integer l, Integer r){
        if(root.leftN==null||root.rightN==null)
            return root.cover;
        if(l<root.left || r>root.right || l>r)
            return 0;
        int mid = (root.left + root.right)/2;
        if(r<=mid)
            return searchOverlap(root.leftN,l,r) + root.cover;
        else if(l>mid)
            return searchOverlap(root.rightN,l,r) + root.cover;
        else
            return searchOverlap(root.leftN,l,mid) + searchOverlap(root.rightN,mid+1,r) + root.cover - searchOverlap(root.leftN,mid,mid);
    }
    
    public static void main(String[] args){
        Random rdl = new Random();
        Random rdr = new Random();
        /*Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        System.out.println("xxx");
        int dot = sc.nextInt();
        sc.close();*/
        int size = 0;
        int dot = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
        	size = Integer.parseInt(br.readLine());
        	System.out.println("xxx");
        	dot = Integer.parseInt(br.readLine());
        	br.close();
        }catch(Exception e) {
        }       
        Integer[] inl = {0,2,3,4,5,5,6,8,8,2};//new Integer[10];
        Integer[] inr = {2,5,5,6,5,6,10,10,9,7};//new Integer[10];
        SegmentTree tree = new SegmentTree(0,size);
        for(int i=0;i<inl.length;i++){
            //inl[i] = rdl.nextInt(100);
            //inr[i] = rdr.nextInt(100);
            System.out.print("["+inl[i]+", "+inr[i]+"]");
        }
        
        for(int i=0;i<inl.length;i++){
            tree.addInterval(tree.root, inl[i], inr[i]);
        }
        System.out.println();
        System.out.println(tree.searchDot(tree.root, dot));
        System.out.println(tree.searchOverlap(tree.root,8,9));
    }
}
