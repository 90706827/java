

package com.example.demo.book2.mtpattern.ch1;

/**
* @Author Mr.Jangni
* @Description 非线程安全的计数器。
* @Date 14:45 2018/12/7
* @Param
* @Return
**/
public class NonThreadSafeCounter {
	private int counter = 0;

	public void increment() {
		counter++;
	}

	public int get() {
		return counter;
	}
}
