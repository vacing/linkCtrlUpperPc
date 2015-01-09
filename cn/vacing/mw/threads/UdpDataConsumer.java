package cn.vacing.mw.threads;

import cn.vacing.mw.exception.DataLenUnproperException;

/**
 * 接收到的UDP数据的消费者需要继承的抽象类，用于线程池的统一调用。抛出数据长度不匹配异常，标识接收到的数据长度不符合要求，接收错误。
 * @author Gavin
 */
public abstract class UdpDataConsumer {
	
	/**
	 * 消费者需要继承的抽象类，每个消费者需要消费的数据量不同
	 * @param bags 本消费者需要消费的数据包数
	 */
	protected UdpDataConsumer(int bags) {
		bagsNeeded = bags;
	}
	
	/**
	 * 调用该函数一次，消费一包数据
	 * @param length 数据长度
	 * @param data 数据
	 * @throws DataLenUnproperException 接收到的数据长度错误， 可能发生了数据丢失
	 */
	public abstract void dataConsumer(int length, byte[] data) throws DataLenUnproperException;
	
	/**
	 * 返回需要消费的包数
	 * @return
	 */
	public int getBagsNum() {
		return bagsNeeded;
	}
	
	/**
	 * 本消费者需要消费的包数
	 */
	protected final int bagsNeeded; 
}
