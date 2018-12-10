

package com.example.demo.book2.mtpattern.ch7.pc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 基于Semaphore的支持流量控制的通道实现。
 * 
 * @author Viscent Huang
 *
 * @param <P> “产品”类型
 */
public class SemaphoreBasedChannel<P> implements Channel<P> {
	private final BlockingQueue<P> queue;
	private final Semaphore semaphore;

	/**
	 * 
	 * @param queue 阻塞队列，通常是一个无界阻塞队列。
	 * @param flowLimit 流量限制数
	 */
	public SemaphoreBasedChannel(BlockingQueue<P> queue, int flowLimit) {
		this.queue = queue;
		this.semaphore = new Semaphore(flowLimit);
	}

	@Override
	public P take() throws InterruptedException {
		return queue.take();
	}

	@Override
	public void put(P product) throws InterruptedException {
		semaphore.acquire();
		try {
			queue.put(product);
		} finally {
			semaphore.release();
		}
	}
}
