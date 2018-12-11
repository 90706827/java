

package com.example.demo.book2.mtpattern.ch12.ms;

/**
 * 对原始任务分解算法策略的抽象。
 * 
 * @author Viscent Huang
 *
 * @param <T>
 *          子任务类型
 */
public interface TaskDivideStrategy<T> {
	/**
	 * 返回下一个子任务。 若返回值为null，则表示无后续子任务。
	 * 
	 * @return 下一个子任务
	 */
	T nextChunk();
}