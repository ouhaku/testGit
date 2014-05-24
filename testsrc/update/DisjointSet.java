package org.bob.test.algorithm;

public class DisjointSet {
	
	static class Element{
		Integer parentIndex;
		Integer numChildren;
		Integer rank;
		
        public void setParent(int i) {    
            parentIndex=i;            
        }
	}
	
	private Element[] all;
	private Integer curSetNum;
	
	public DisjointSet(Integer ini){
		curSetNum = ini;
		all = new Element[ini];
		for(int i=0;i<ini;i++){
			all[i] = new Element();
			all[i].parentIndex = i;
			all[i].numChildren = 1;
			all[i].rank = 0;
		}
	}

	public Integer find(Integer tocheck){
		if(tocheck>=all.length || tocheck < 0)
			return all.length;
		if(!all[tocheck].parentIndex.equals(tocheck)){
			all[tocheck].setParent(find(all[tocheck].parentIndex));		
		}
		return all[tocheck].parentIndex;
	}
	
	public boolean union(Integer a, Integer b){
		Integer setA = find(a);
		Integer setB = find(b);
		if(setA.equals(setB))
			return false;
		//can use other para to represent rank, other than the children num
		if(all[setA].numChildren > all[setB].numChildren){
		//if(all[setA].rank > all[setB].rank)
			all[setB].parentIndex = setA;
			all[setA].numChildren += all[setB].numChildren;
			all[setB].numChildren = 0;
		}else{
			all[setA].parentIndex = setB;
			all[setB].numChildren += all[setA].numChildren;
			all[setA].numChildren = 0;
			//if(all[setA].rank.equals(all[setB].rank))
				//all[setB].rank++;
		}
		curSetNum--;
		return true;
	}
}
