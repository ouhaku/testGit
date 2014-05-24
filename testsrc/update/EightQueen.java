package org.bob.test.algorithm;

public class EightQueen {
    private Integer size;
    
    public EightQueen(Integer t){
        size = t;
    }
    
    public Integer getSize(){
        return size;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Integer size = 13;//Integer.parseInt(args[0]);
        EightQueen sol = new EightQueen(size);
        Integer[] result = sol.getSolution(size);
        /*for(int i=0;i<result.length;i++){
            String tmp = "";
        for(int j=0;j<result.length;j++){
        if(result[i]==j)
        tmp += "*";
        else
        tmp += "-";
        }
        System.out.println(tmp);
        }*/
    }
    public Integer[] getSolution(Integer size){
        Boolean[] column = new Boolean[size];
        Boolean[] left = new Boolean[2*size - 1];
        Boolean[] right = new Boolean[2*size - 1];
        Integer[] index = new Integer[size];
        Integer cur = 0;
        Integer start = 0;
        Integer count = 0;
        Boolean find = false;
        this.initial(column, left, right, index);

        for(int firstcol=0;firstcol<size;){
           //find all the solutions for one fixed queen in first row
	       while(true){//while(cur<size)
	    	   //place one queen
	           for(int i=start;i<size;i++){
	               if(column[i]&&left[size-1-cur+i]&&right[size-1-cur+size-1-i]){
	                   index[cur] = i;
	                   column[i] = false;
	                   left[size-1-cur+i] = false;
	                   right[size-1-cur+size-1-i] = false;
	                   start = 0;
	                   cur++;
	                   find = true;
	                   break;
	               }
	           }
	           //found one solution
	           if(cur==size){
	        	   count++;
	        	   find = false;
	           }
	           //backtrace
	           if (!find){     	
	               do{
	                   cur--;
	                   column[index[cur]] = true;
	                   left[size-1-cur+index[cur]] = true;
	                   right[size-1-cur+size-1-index[cur]] = true;
	                   start = index[cur] + 1;
	                   index[cur] = -1;
	               }while(index[cur]==size-1&&cur>0);             
	           }
	           //reset the label for next round
	           find = false;
	           //backtrace to first row, jump out
	           if(cur==0)
	        	   break;
	       }
	       
	       //initialize for next place in first row
	       firstcol++;
	       start = firstcol;
	       this.initial(column, left, right, index);     	
        }
        System.out.println("total solutions: "+count );
        return index;
    }
    
    public void initial(Boolean[] column, Boolean[] left, Boolean[] right, Integer[] index){
    	int size = column.length;
    	for(int i=0;i<size;i++){
            column[i]=true;
            left[i]=true;
            left[2*size-2-i]=true;
            right[i]=true;
            right[2*size-2-i]=true;
            index[i]=-1;
        }
    }
}
