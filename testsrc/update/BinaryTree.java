package org.bob.test.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class BinaryTree<Type> {
	
	private TreeNode<Type> root;
	
	static class TreeNode<Type> {
		Type value;
		Integer rank=0;
		TreeNode<Type> leftchild;
		TreeNode<Type> rightchild;
		
		public TreeNode(Type value) {
			this.value = value;
			leftchild = null;
			rightchild = null;
		}
	}
	
	public BinaryTree(Type[] target){
		root = buildtree(target,0,target.length-1);
	}
	
	public TreeNode<Type> buildtree(Type[] target, int i, int j){
		if(j<i)
			return null;
		if(j==i)
			return new TreeNode<Type>(target[i]);
		int m = (i+j)/2;
		   //int mid=(first+last)/2;
		   //to avoid overflow,you should do it like this:
		   //int mid=(first&last)+((first^last)>>1);
		TreeNode<Type> t = new TreeNode<Type>(target[m]);
		t.leftchild = buildtree(target,i,m-1);
		t.rightchild = buildtree(target,m+1,j);
		return t;
	}
	
	public TreeNode<Type> findancestor(TreeNode<Type> start, Type one, Type two){
		if(start == null)
			return null;
		if(start.value.equals(one)||start.value.equals(two))
			return start;
		TreeNode<Type> left = findancestor(start.leftchild,one,two);
		TreeNode<Type> right = findancestor(start.rightchild,one,two);
		if(left!=null && right != null)
			return start;
		else
			return left!=null?left:right;		
	}
	
	public void preorderR(TreeNode<Type> root){
		if(root==null)
			return;
		System.out.print(root.value);
		System.out.print(",");
		preorderR(root.leftchild);
		preorderR(root.rightchild);
	}
	
	public void preorderI(TreeNode<Type> root){
		if(root==null)
			return;
		Stack<TreeNode<Type>> stack = new Stack<TreeNode<Type>>();
		stack.push(root);
		TreeNode<Type> node;
		while(!stack.isEmpty()){
			node = stack.pop();
			System.out.print(node.value);
			System.out.print(",");
			if(node.rightchild!=null)
				stack.push(node.rightchild);
			if(node.leftchild!=null)
				stack.push(node.leftchild);
		}
	}
	
	public void inorderR(TreeNode<Type> root){
		if(root==null)
			return;
		inorderR(root.leftchild);
		System.out.print(root.value);
		System.out.print(",");		
		inorderR(root.rightchild);
	}
	
	public void inorderI(TreeNode<Type> root){
		if(root==null)
			return;
		Stack<TreeNode<Type>> stack = new Stack<TreeNode<Type>>();
		TreeNode<Type> current = root;
		while(!stack.isEmpty()|| current!=null){
			if(current!=null){
				stack.push(current);
				current = current.leftchild;
			}else{
				current = stack.pop();
				System.out.print(current.value);
				System.out.print(",");
				current = current.rightchild;
			}
		}
	}
	
	public void postorderR(TreeNode<Type> root){
		if(root==null)
			return;
		postorderR(root.leftchild);
		postorderR(root.rightchild);
		System.out.print(root.value);
		System.out.print(",");				
	}
	
	public void postorderI1(TreeNode<Type> root){
		if(root==null)
			return;
		Stack<TreeNode<Type>> stack = new Stack<TreeNode<Type>>();
		TreeNode<Type> cur = root;
		TreeNode<Type> pre = null;
		stack.push(cur);
		while(!stack.isEmpty()){
			//be careful of == and equal(), down tree
			cur = stack.peek();
			if(pre==null || pre.leftchild == cur || pre.rightchild == cur){
				if(cur.leftchild!=null)
					stack.push(cur.leftchild);
				else if(cur.rightchild!=null)
					stack.push(cur.rightchild);
			}else if(pre == cur.leftchild){
				if(cur.rightchild!=null)
					stack.push(cur.rightchild);
			}else {
				cur = stack.pop();
				System.out.print(cur.value);
				System.out.print(",");
			}
			pre = cur;
		}
	}
	
	public void postorderI2(TreeNode<Type> root){
		if(root==null)
			return;
		Stack<TreeNode<Type>> stack = new Stack<TreeNode<Type>>();
		Stack<TreeNode<Type>> output = new Stack<TreeNode<Type>>();
		TreeNode<Type> node = null;
		stack.push(root);
		while(!stack.isEmpty()){
			node = stack.pop();
			output.push(node);
			if(node.leftchild!=null)
				stack.push(node.leftchild);
			if(node.rightchild!=null)
				stack.push(node.rightchild);		
		}
		
		while(!output.isEmpty()){
			node = output.pop();
			System.out.print(node.value);
			System.out.print(",");
		}
	}
	
	public void postbasedoninpre(Integer[] in, Integer[] pre){
		HashMap<Integer, Integer> index = new HashMap<Integer,Integer>();
		for(int i=0;i<in.length;i++){
			index.put(in[i], i);
		}
		_postbasedoninpre(in, pre, 0, in.length-1, 0, pre.length-1, index);
	}
	
	public void _postbasedoninpre(Integer[] in, Integer[] pre, int inx, int iny, int prex, int prey, Map<Integer,Integer> index){
		if(inx<0 || iny>in.length-1 || prex<0|| prey>pre.length-1 || inx>iny || prex>prey)
			return;
		Integer offset = index.get(pre[prex]);
		_postbasedoninpre(in,pre,inx,offset-1,prex+1,prex+offset-inx,index);
		_postbasedoninpre(in,pre,offset+1,iny,prex+offset-inx+1,prey,index);
		System.out.print(pre[prex]);
		System.out.print(",");	
	}
	
	public static void main(String[] args){
		Integer[] target = new Integer[100];
		for(int i=0;i<target.length;i++){
			target[i] = i+1;
		}
		BinaryTree<Integer> tree = new BinaryTree<Integer>(target);
		
		/*Integer one = 1;
		Integer two = 499;
		TreeNode<Integer> result = tree.findancestor(tree.root,one,two);
		System.out.println("ancestor is:" + result.value);*/
		
		/*tree.preorderR(tree.root);
		System.out.println();
		tree.preorderI(tree.root);
		System.out.println();
		tree.inorderR(tree.root);
		System.out.println();
		tree.inorderI(tree.root);
		System.out.println();
		tree.postorderR(tree.root);
		System.out.println();
		tree.postorderI1(tree.root);
		System.out.println();
		tree.postorderI2(tree.root);*/
		
		Integer[] pre =  {50,25,12,6,3,1,2,4,5,9,7,8,10,11,18,15,13,14,16,17,21,19,20,23,22,24,37,31,28,26,27,29,30,34,32,33,35,36,43,40,38,39,41,42,46,44,45,48,47,49,75,62,56,53,51,52,54,55,59,57,58,60,61,68,65,63,64,66,67,71,69,70,73,72,74,88,81,78,76,77,79,80,84,82,83,86,85,87,94,91,89,90,92,93,97,95,96,99,98,100};
		Integer[] in = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
		tree.postbasedoninpre(in, pre);
	}

}
