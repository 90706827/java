

package com.example.demo.book2.mtpattern.ch1;

/**
 * 线程安全的计数器。
 * 
 * @author Viscent Huang
 *
 */
public class ThreadSafeCounter {
	private int counter = 0;

	public void increment() {
		synchronized (this) {
			counter++;
		}
	}

	public int get() {
		synchronized (this) {
			return counter;
		}

	}
}
