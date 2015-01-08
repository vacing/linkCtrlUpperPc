package cn.vacing.mw.threads;

import cn.vacing.mw.exception.DataLenUnproperException;

/**
 * 接收到的UDP数据的消费者需要继承的接口，用于线程池的统一调用。抛出数据长度不匹配异常，标识接收到的数据长度不符合要求，接收错误。
 * @author Gavin
 */
public interface UdpDataConsumer {
	void dataConsumer(int length, byte[] data) throws DataLenUnproperException;
	int getBagsNum();
}
