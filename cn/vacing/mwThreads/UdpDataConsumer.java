package cn.vacing.mwThreads;

/**
 * 接收到的UDP数据的消费者需要继承的接口，用于线程池的统一调用
 * @author Gavin
 */
public interface UdpDataConsumer {
	void dataConsumer(int length, byte[] data);
	int getBagsNum();
}
