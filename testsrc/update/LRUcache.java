package org.bob.test.syntax;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LRUcache<Type> {

	Deque queue = new ArrayDeque<Type>();
	Integer size = 0;
	Lock lock = new ReentrantLock();
	Condition notfull = lock.newCondition();
	Condition notempty = lock.newCondition();
	
	public LRUcache(Integer size){
		this.size = size;
	}
	
	public void add(Type one){
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName()+ " got the add lock");
			if(queue.size()>=size){
				notfull.await();
			}
			queue.offerFirst(one);
			notempty.signal();
				
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}		
	}
	
	public Type get(){
		Type result = null;
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName()+ " got the get lock");
			if(queue.size()==0){
				notempty.await();
			}
			result = (Type) queue.pollLast();
			notfull.signal();				
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}
		return result;
	}
	
	static class Worker implements Runnable{
		private LRUcache<Integer> myqueue;
		private Integer toadd;
		
		public Worker(LRUcache<Integer> queue, Integer toadd){
			myqueue = queue;
			this.toadd = toadd;
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(Thread.currentThread().getName()+ " started");
			while(true){
				
			myqueue.add(toadd);
			System.out.println(Thread.currentThread().getName()+ " added: " + toadd);
			}
		}
		
	}
	
	static class Consumer implements Runnable{
		private LRUcache<Integer> myqueue;
		//private Integer toadd;
		
		public Consumer(LRUcache<Integer> queue){
			myqueue = queue;
			//this.toadd = toadd;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(Thread.currentThread().getName()+ " started");
			while(true){
			
			System.out.println(Thread.currentThread().getName()+ " got " + myqueue.get());
			}
		}		
	}
	
	public static void main(String[] args){
		LRUcache<Integer> test = new LRUcache<Integer>(5);
		for(int i = 0; i < 5 ;i++){
			new Thread(new Worker(test,i)).start();
		}
		for(int i = 0; i < 5 ;i++){
			new Thread(new Consumer(test)).start();
		}
	}
	
}
