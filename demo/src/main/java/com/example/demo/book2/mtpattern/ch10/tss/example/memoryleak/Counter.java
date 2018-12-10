

package com.example.demo.book2.mtpattern.ch10.tss.example.memoryleak;

public class Counter {
	private int i=0;
	
	public int getAndIncrement(){
		return (i++);
	}
}
