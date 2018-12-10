

package com.example.demo.book2.mtpattern.ch12.ms;

/**
 * 表示子任务处理失败的异常。
 * 
 * @author Viscent Huang
 */
@SuppressWarnings("serial")
public class SubTaskFailureException extends Exception {

	/**
	 * 对处理失败的子任务进行重试所需的信息
	 */
	@SuppressWarnings("rawtypes")
	public final RetryInfo retryInfo;

	@SuppressWarnings("rawtypes")
	public SubTaskFailureException(RetryInfo retryInfo, Exception cause) {
		super(cause);
		this.retryInfo = retryInfo;
	}

}