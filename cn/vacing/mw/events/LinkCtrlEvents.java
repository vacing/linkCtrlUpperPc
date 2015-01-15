package cn.vacing.mw.events;

import cn.vacing.mw._main.FinalVar;
import cn.vacing.mw._main.MainThread;
import cn.vacing.mw.gui.MainFrame;
import cn.vacing.mw.threads.UdpRelatedThreads;
import cn.vacing.mw.tools.DataConvert;
import cn.vacing.mw.udp.UdpSocket;

public class LinkCtrlEvents {
	/**
	 * 时间捕获门限控制
	 */
	public void timeSynGateConfig() {
		mainFrame.timeSynGateConfig();
	}
	
	/**
	 * 打开AD1
	 */
	public void openAD1() {
		urt.submitThread(urt.getSendCommandThread(
				mainFrame.getFpga1Ip(), MainThread.PORT_1,
				FinalVar.AD1_OPEN_COMMAND));
		mainFrame.openAD1();
	}
	/**
	 * 关闭AD1
	 */
	public void closeAD1() {
		urt.submitThread(urt.getSendCommandThread(mainFrame.getFpga1Ip(), 
				MainThread.PORT_1,
				FinalVar.AD1_CLOSE_COMMAND));
		mainFrame.closeAD1();
	}
	/**
	 * 打开AD2
	 */
	public void openAD2() {
		urt.submitThread(urt.getSendCommandThread(mainFrame.getFpga1Ip(), 
				MainThread.PORT_1,
				FinalVar.AD2_OPEN_COMMAND));
		mainFrame.openAD2();
	}
	/**
	 * 关闭AD2
	 */
	public void closeAD2() {
		urt.submitThread(urt.getSendCommandThread(mainFrame.getFpga1Ip(), 
				MainThread.PORT_1,
				FinalVar.AD2_CLOSE_COMMAND));
		mainFrame.closeAD2();
	}
	
	/**
	 * 打开DA1
	 */
	public void openDA1() {
		udpSocket.sendUdpMesg(mainFrame.getFpga1Ip(), MainThread.PORT_1,
				DataConvert.intToBytesArray(FinalVar.DA1_OPEN_COMMAND));
		mainFrame.openDA1();
	}
	/**
	 * 打开DA2
	 */
	public void openDA2() {
		udpSocket.sendUdpMesg(mainFrame.getFpga1Ip(), MainThread.PORT_1,
				DataConvert.intToBytesArray(FinalVar.DA2_OPEN_COMMAND));
		mainFrame.openDA2();
	}
	/**
	 * 关闭DA1
	 */
	public void closeDA1() {
		udpSocket.sendUdpMesg(mainFrame.getFpga1Ip(), MainThread.PORT_1,
				DataConvert.intToBytesArray(FinalVar.DA1_CLOSE_COMMAND));
		mainFrame.closeDA1();
	}
	/**
	 * 关闭DA2
	 */
	public void closeDA2() {
		udpSocket.sendUdpMesg(mainFrame.getFpga1Ip(), MainThread.PORT_1,
				DataConvert.intToBytesArray(FinalVar.DA2_CLOSE_COMMAND));
		mainFrame.closeDA2();
	}


	//init
	public void init(MainFrame mainFrame, UdpSocket udpSocket, UdpRelatedThreads urt) {
		this.mainFrame = mainFrame;
		this.udpSocket = udpSocket;
		this.urt = urt;
	}
	
	private MainFrame mainFrame;
	private UdpSocket udpSocket;
	private UdpRelatedThreads urt;
}
