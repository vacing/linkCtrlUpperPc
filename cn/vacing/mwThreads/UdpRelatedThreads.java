package cn.vacing.mwThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import cn.vacing.mwEvents.DataConvert;
import cn.vacing.mwUdp.UdpSocket;
import cn.vacing.test.TestSpectrumDataGet;

public class UdpRelatedThreads {
	private UdpSocket udpSocket;	
	private ExecutorService exec;

	public UdpRelatedThreads(UdpSocket udpSocket, ExecutorService exec) {
		this.udpSocket = udpSocket;
		this.exec = exec;
	}
	
	public Future<?> submitThread(Runnable task) {
		Future<?> f = exec.submit(task);
		return f;
	}
	/**
	 * 只发送命令
	 * @author usdr
	 *
	 */
	public class SendCommandThread implements Runnable {
		private String destIP;
		private int destPort;
		private int command;

		public SendCommandThread(String destIP, int destPort, int command) {
			this.destIP = destIP;
			this.destPort = destPort;
			this.command = command;
		}

		@Override
		public void run() {
			synchronized (udpSocket) {
//				System.out.printf("%x\n", command);
				udpSocket.sendUdpMesg(destIP, 					// 目标IP
						destPort, 								// 目标端口
						DataConvert.intToBytesArray(command)); 	// 控制命令
			}
			try {
				Thread.sleep(2);							//防止命令发送过快
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * 发送命令，并接收数据
	 * @author usdr
	 *
	 */
	public class GetUdpDataThread implements Runnable {
		private String destIP;			//目标IP
		private int destPort;			//目标端口
		private int command;			//命令
		private int bagsNeeded;			//需要的包数
		private UdpDataConsumer consumer;				//数据消费者

		public GetUdpDataThread(String destIP, int destPort, int command, UdpDataConsumer consumer) {
			this.destIP = destIP;
			this.destPort = destPort;
			this.command = command;
			this.consumer = consumer;
			this.bagsNeeded = consumer.getBagsNum();
		}
		
		@Override
		public void run() {
			//抵消前数据上传
			synchronized (udpSocket) {
				udpSocket.sendUdpMesg(destIP, 					// 目标IP
						destPort, 								// 目标端口
						new byte[]{(byte) 0xc7}); 	// 控制命令
			}
			byte[] packetBuffer = new byte[1470];
			for(int bagsCount = 0; bagsCount < bagsNeeded / 2; bagsCount++) {
				int temp = 0;
				synchronized (udpSocket) {
						temp = udpSocket.receUdpMesg(packetBuffer);
				}
//				temp = TestSpectrumDataGet.spectrumDataGet(packetBuffer);
				consumer.dataConsumer(temp, packetBuffer);
//				System.out.println("Received:  " + temp + "\tbagsCount: " + bagsCount);
				if (temp <= 0) {	//udp未打开（-1）或接收超时（0）
					return;
				}
			}
			
			//抵消后数据上传
			synchronized (udpSocket) {
				udpSocket.sendUdpMesg(destIP, 					// 目标IP
						destPort, 								// 目标端口
						new byte[]{(byte) 0xc8}); 	// 控制命令
			}		
			for(int bagsCount = 0; bagsCount < bagsNeeded / 2; bagsCount++) {
				int temp = 0;
				synchronized (udpSocket) {
						temp = udpSocket.receUdpMesg(packetBuffer);
				}
//				int temp = TestSpectrumDataGet.spectrumDataGet(packetBuffer);
				consumer.dataConsumer(temp, packetBuffer);
//				System.out.println("Received:  " + temp  + "\tbagsCount: " + bagsCount);
				if (temp <= 0) {//udp未打开（-1）或接收超时（0）
					return;
				}
			}

		}
	}
}
