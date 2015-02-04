package cn.vacing.mw.events;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cn.vacing.mw._main.ButtonCommand;
import cn.vacing.mw._main.FinalVar;
import cn.vacing.mw._main.MainThread;
import cn.vacing.mw.exception.NumInputOutOfBounds;
import cn.vacing.mw.gui.MainFrame;
import cn.vacing.mw.threads.UdpRelatedThreads;
import cn.vacing.mw.udp.UdpSocket;

public class DigitCanceEvents {
	/**
	 * 数字干扰抵消复位按钮
	 */
	public void diCanceResetStart() {
		mf.diCanceResetStart();
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				ButtonCommand.DI_CANCE_RESET_START.s2fpga));
	}
	public void diCanceResetStop() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				ButtonCommand.DI_CANCE_RESET_STOP.s2fpga));
		mf.diCanceResetStop();	
	}

	/**
	 * 数字干扰抵消系数更新按钮
	 */
	public void paramUpdateStart() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				ButtonCommand.DI_PARAM_UP_START.s2fpga));
		mf.diParamUpdateStart();
	}
	public void paramUpdateStop() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				ButtonCommand.DI_PARAM_UP_STOP.s2fpga));
		mf.diParamUpdateStop();
	}

	/**
	 * 直流偏置校准 
	 */
	private static int dirCurrState = ButtonCommand.DIR_CURR_CORRECT.s2fpga;
	public void dirCurrCorr() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				dirCurrState));
		mf.diDirCurrCorr();
		/**
		 * 如果最低位为1，则对最低位清零。否则最低位置1。
		 */
		if((dirCurrState & 1) == 1) {
			dirCurrState &= ~1;
		} else {
			dirCurrState |= 1;
		}
		
//		System.out.println(Integer.toBinaryString(dirCurrState));
	}

	/**
	 * 接收时延配置
	 * @throws NumInputOutOfBounds
	 */
	public void receTimeDelayConf(){
		String numStr = mf.diReceTimeDelayConf();
		if(! numStr.matches("[\\d]+")) {	//使用正则表达式，防止不输入数据或输入的不是数字
			JOptionPane.showMessageDialog(mf, "请输入内容。");
			return;
		}
		int num = Integer.valueOf(numStr, 10);
		// 0 <= num <= 63
		if(num < 0 || num > 63) {
			JOptionPane.showMessageDialog(mf, "请输入0-63的十进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				(ButtonCommand.DI_RECE_TIME_DELAY.s2fpga | num)));
	}

	/**
	 * 反馈时延配置
	 */
	public void feedbackTimeDelayConf(){
		String numStr = mf.diFeedbackTimeDelayConf();
		if(! numStr.matches("[\\d]+")) {	//使用正则表达式，防止不输入数据或输入的不是数字
			JOptionPane.showMessageDialog(mf, "请输入内容。");
			return;
		}
		int num = Integer.valueOf(numStr, 10);
		// 0 <= num <= 63
		if(num < 0 || num > 63) {
			JOptionPane.showMessageDialog(mf, "请输入0-63的十进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				(ButtonCommand.DI_FEEDBACK_TIME_DELAY.s2fpga | num)));
	}
	
	/**
	 * RLS缓存长度配置
	 */
	public void catchLengthConf(){
		String numStr = mf.diCatchLengthConf();
		if(! numStr.matches("[\\d]+")) {	//使用正则表达式，防止不输入数据或输入的不是数字
			JOptionPane.showMessageDialog(mf, "请输入内容。");
			return;
		}
		int num = Integer.valueOf(numStr, 10);
		// 24 <= num <= 32767
		if(num < 24 || num > 32767) {
			JOptionPane.showMessageDialog(mf, "请输入24-32767的十进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				(ButtonCommand.DI_CATCH_LENGTH.s2fpga | num)));
	}
	
	
	/**
	 * @param mainFrame
	 * @param urt
	 */
	public void init(MainFrame mainFrame, UdpRelatedThreads urt) {
		this.mf = mainFrame;
		this.urt = urt;
	}
	
	private MainFrame mf;
	private UdpRelatedThreads urt;
}
