

package com.example.demo.book2.mtpattern.ch8.activeobject.example;

import java.util.concurrent.Future;

public interface SampleActiveObject {
	public Future<String> process(String arg, int i);
}