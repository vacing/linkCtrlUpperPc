package cn.vacing.mwEvents;

import cn.vacing.mwGui.MainFrame;
import cn.vacing.mwUdp.UdpSocket;

public class DigitCanceEvents {

	/**
	 * 频域平滑段数配置
	 */
	public void freqAverSegsConf()	{
		
	}

	//init
	public void nit(MainFrame mainFrame, UdpSocket udpSocket) {
		this.mainFrame = mainFrame;
		this.udpSocket = udpSocket;
	}
	
	private MainFrame mainFrame;
	private UdpSocket udpSocket;
}
