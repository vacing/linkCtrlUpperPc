package cn.vacing.mw._main;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import cn.vacing.mw.events.MainFrameEvents;
import cn.vacing.mw.gui.*;
import cn.vacing.mw.threads.UdpRelatedThreads;
import cn.vacing.mw.udp.*;

public class MainThread {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrameEvents mfBe = new MainFrameEvents();
		MainFrame mf = new MainFrame(mfBe, mfBe);
		mfBe.setMf(mf);
	}
	
	/**
	 * 在登陆成功后再初始化的部分
	 * @param mf
	 * @param mfBe
	 */
	public static void mainInit(MainFrame mf, MainFrameEvents mfBe) {
		try {
			UdpSocket us = new UdpSocket(mf.getIpLocal(), mf.getPortLocal());
			System.out.println(mf.getIpLocal());
			ExecutorService exec = Executors.newSingleThreadExecutor();
			UdpRelatedThreads urt = new UdpRelatedThreads(us, exec);
			mfBe.init(mf, us, urt);	//finish the init of be
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mf, "Udp 打开失败，请检查本地IP及端口设置！！");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
