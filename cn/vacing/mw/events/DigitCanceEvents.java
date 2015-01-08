package cn.vacing.mw.events;

import cn.vacing.mw.gui.MainFrame;
import cn.vacing.mw.udp.UdpSocket;

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
