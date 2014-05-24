package org.bob.test.indeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ShuffledOrder {

	static class Song implements Comparable<Song> {
		
		private Integer albumid;
		private Integer songid;
		private Random rd = new Random();

		public Song(Integer a, Integer b){
			albumid = a;
			songid = b;
		}
		@Override
		public int compareTo(Song o) {
			// TODO Auto-generated method stub
			if(this.albumid.equals(o.albumid)){
				if(this.songid.equals(o.songid))
					return 0;
				return this.songid>o.songid?1:-1;
			}
			int a = rd.nextInt(100);
			return a>50?1:-1;
		}
		
	}

	static class ShuffleComparator implements Comparator<Song>{

		//private Random rd = new Random();
		private Map<Integer,Integer> albumseq;
		
		public ShuffleComparator(Map<Integer,Integer> seq){
			albumseq = seq;
		}
		@Override
		public int compare(Song o1, Song o2) {
			// TODO Auto-generated method stub
			Song one = (Song)o1;
			Song two = (Song)o2;
			if(one.albumid.equals(two.albumid)){
				if(one.songid.equals(two.songid))
					return 0;
				else
					return one.songid>two.songid?1:-1;
			}
			
			return albumseq.get(o1.albumid)>albumseq.get(o2.albumid)?1:-1;
		}
		
	}
	/**
	 * @param args
	 */
	private static Map<Integer,ArrayList<Integer>> all = new HashMap<Integer,ArrayList<Integer>>();
	private static Set<Integer> albumid = new HashSet<Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer num = 20;
		Integer album, song;
		Song[] raw = new Song[num];
		Random rda = new Random();
		Random rds = new Random();
		for(int i=0;i<raw.length;i++){
			album = rda.nextInt(10)+1;
			song = rds.nextInt(5)+1;
			Song one = new Song(album,song);
			/*if(all.containsKey(album)){
				all.get(album).add(song);
			}else{
				ArrayList<Integer> single = new ArrayList<Integer>();
				single.add(song);
				all.put(album, single);
			}*/
			albumid.add(album);
			raw[i] = one;
			System.out.println("album: " + album + " song: " + song);
		}
		System.out.println("--------------------------------------------------");
		
		Set<Integer> shuffle = new HashSet<Integer>();
		while(shuffle.size()<albumid.size()){
			album = rda.nextInt(num)+1;
			shuffle.add(album);
		}
		Iterator itr1 = albumid.iterator();
		Iterator itr2 = shuffle.iterator();
		Map<Integer,Integer> albumseq = new HashMap<Integer,Integer>();
		while(itr1.hasNext()&&itr2.hasNext()){
			albumseq.put((Integer)itr1.next(), (Integer)itr2.next());
		}
		/*for(ArrayList<Integer> single:all.values()){
			Arrays.sort(single.toArray());
		}*/
		Arrays.sort(raw, new ShuffleComparator(albumseq));
		for(int i=0;i<raw.length;i++){
			Song one = raw[i];
			System.out.println("album: " + one.albumid + " song: " + one.songid);
		}
		/*TreeSet<Song> shuffled = new TreeSet<Song>();
		for(int i=0;i<raw.length;i++){
			shuffled.add(raw[i]);
		}
		System.out.println("tree set size: " + shuffled.size());
		Iterator itr = shuffled.iterator();
		while(itr.hasNext()){
			Song one = (Song)itr.next();
			System.out.println("album: " + one.albumid + " song: " + one.songid);
		}*/

	}

}
