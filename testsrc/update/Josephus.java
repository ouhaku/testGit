package org.bob.test.algorithm;

public class Josephus {

    private static class Node<AnyType> {
        AnyType iElement;
        Node<AnyType> iLeft;
        Node<AnyType> iRight;
        int iSize;
        public Node(AnyType element) {
            iElement = element;
            iLeft = iRight = null;
            iSize = 1;
        }
        
        public void recomputeSize() {
            int leftSize = (iLeft != null) ? iLeft.iSize : 0;
            int rightSize = (iRight != null) ? iRight.iSize : 0;
            iSize = leftSize + 1 + rightSize;
        }
    }
    
    private static class RankTree<AnyType> {
        private Node<AnyType> iRoot;
        public RankTree(AnyType[] elements) {
            iRoot = buildTree(elements, 0, elements.length - 1);
        }
        // Internal method to build tree for elements from i to j.
        private Node<AnyType> buildTree(AnyType [] elements, int i, int j){
            if (j < i) 
                return null;
            if (i == j) 
                return new Node<AnyType>(elements[i]);
            int mid = (i + j) / 2;
            Node<AnyType> t = new Node<AnyType>(elements[mid]);
            t.iLeft = buildTree(elements, i, mid - 1);
            t.iRight = buildTree(elements, mid + 1, j);
            t.recomputeSize();
            return t;
        }
        
        public Node<AnyType> find(int k) {
            return find(k, iRoot);//.iElement;
        }
        
        private Node<AnyType> find(int k, Node<AnyType> t) {
            if (t == null) 
                throw new IllegalArgumentException();
            
            int leftSize = (t.iLeft != null) ? t.iLeft.iSize : 0;
            if (k-1 < leftSize) 
                return find(k, t.iLeft);
            if (k-1 == leftSize) 
                return t;
            return find(k - leftSize - 1, t.iRight);
        }
        
        private AnyType findFirst(Node<AnyType> t) {
            AnyType smallest = null;
            while (t.iLeft != null && smallest==null){
                if(t.iLeft.iLeft != null)
                    smallest = findFirst(t.iLeft);
                	//t = t.iLeft;
                else{
                    smallest = t.iLeft.iElement;
                    if (t.iLeft.iRight != null)
                        t.iLeft = t.iLeft.iRight;
                    else
                        t.iLeft = null;
                    //t.iSize--;
                    //return smallest;
                }
            }
            t.recomputeSize();
            return smallest;
        }
        
        public void remove(int k) {
            iRoot = remove(k, iRoot);
        }
        
        private Node<AnyType> remove(int k, Node<AnyType> t) {
            if (t == null) 
                throw new IllegalArgumentException();
            int leftSize = (t.iLeft != null) ? t.iLeft.iSize : 0;
            if (k-1 < leftSize) 
                t.iLeft = remove(k, t.iLeft);
            else if (k-1 > leftSize) 
                t.iRight = remove(k - leftSize - 1, t.iRight);
            else {
                if (t.iLeft != null && t.iRight != null) { 
                    // Two children
                    AnyType result = findFirst(t.iRight);
                    if (result != null)
                        t.iElement = result;
                    else{
                        t.iElement = t.iRight.iElement;
                        t.iRight = t.iRight.iRight;
                        t.iSize--;
                    }
                        
                    //t.iElement = findFirst(t.iRight).iElement;
                    //t.iRight = removeFirst(t.iRight);
                } 
                else 
                    t = (t.iLeft != null) ? t.iLeft : t.iRight;
            }
            if (t != null) 
                t.recomputeSize();
            return t;                
        }
        
    }
    
    private Integer total,interval;
    
    public Josephus(Integer in1, Integer in2){
        this.total = in1;
        this.interval = in2;
    }
    
    
    public Integer[] solute(){
        Integer[] target = new Integer[total];
        for (int i=0;i<total;i++){
            target[i] = i+1;
        }
        RankTree sol = new RankTree<Integer>(target);
        Node<Integer> findnode;
        int tofind = 1;
        int gap;
        System.out.println("begin");
        for (int i=1;i<=total;i++){
            tofind = ((tofind + interval - 1)%(total-i+1))!=0?(tofind + interval - 1)%(total-i+1):(total-i+1);
            findnode = sol.find(tofind);
            target[i-1] = findnode.iElement;
            //gap = findnode.iSize;
            sol.remove(tofind);
            //tofind = tofind + gap;
        }
        System.out.println("end");
        return target;
    }
    
    public static void main(String[] args){
        Integer input1 = 1000;//Integer.parseInt(args[0]);
        Integer input2 = 13;//Integer.parseInt(args[1]);
        Josephus jose = new Josephus(input1,input2);
        Integer[] output = jose.solute();
        String ref = "";
        for(int i=0; i<output.length; i++)
            ref = ref + " " + output[i];
        System.out.println("josephus circle: ");
        System.out.println(ref);
    }    
}