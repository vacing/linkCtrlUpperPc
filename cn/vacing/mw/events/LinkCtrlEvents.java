package cn.vacing.mw.events;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cn.vacing.mw._main.ButtonCommand;
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
		mf.timeSynGateConfig();
	}
	
	/**
	 * 打开AD1
	 */
	public void openAD1() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				ButtonCommand.AD1_OPEN.s2fpga));
		mf.openAD1();
	}
	/**
	 * 关闭AD1
	 */
	public void closeAD1() {
		urt.submitThread(urt.getSendCommandThread(mf.getFpga0Ip(), 
				MainThread.PORT_2,
				ButtonCommand.AD1_CLOSE.s2fpga));
		mf.closeAD1();
	}
	/**
	 * 打开AD2
	 */
	public void openAD2() {
		urt.submitThread(urt.getSendCommandThread(mf.getFpga0Ip(), 
				MainThread.PORT_2,
				ButtonCommand.AD2_OPEN.s2fpga));
		mf.openAD2();
	}
	/**
	 * 关闭AD2
	 */
	public void closeAD2() {
		urt.submitThread(urt.getSendCommandThread(mf.getFpga0Ip(), 
				MainThread.PORT_2,
				ButtonCommand.AD2_CLOSE.s2fpga));
		mf.closeAD2();
	}
	
	/**
	 * 打开DA
	 */
	public void openDA() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				ButtonCommand.DA_OPEN.s2fpga));
		mf.openDA();
	}
	/**
	 * 关闭DA
	 */
	public void closeDA() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				ButtonCommand.DA_CLOSE.s2fpga));
		mf.closeDA();
	}

	/**
	 * 复位链路，fpga0
	 */
	public void linkResetOn() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				ButtonCommand.LINK_RESET_ON.s2fpga));
		mf.linkResetOn();
	}
	/**
	 * 复位取消，fpga0
	 */
	public void linkResetOff() {
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				ButtonCommand.LINK_RESET_OFF.s2fpga));
		mf.linkResetOff();
	}
	
	/**
	 * 配置平滑段数，fpga1
	 */
	public void smoothSegsConf() {
		String numStr = mf.smoothSegsConf();
		if(! numStr.matches("[\\d]+")) {	//使用正则表达式，防止不输入数据或输入的不是数字
			JOptionPane.showMessageDialog(mf, "请输入正确内容。");
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
				(ButtonCommand.SMOOTH_SEGS_CONF.s2fpga | num)));
	}

	/**
	 * 配置发射中频
	 * @return
	 */
	public void emitCentFreqConf() {
		String numStr = mf.emitCentFreqConf();
		if(! numStr.matches("[\\d]+")) {	//使用正则表达式，防止不输入数据或输入的不是数字
			JOptionPane.showMessageDialog(mf, "请输入正确内容。");
			return;
		}
		int num = Integer.valueOf(numStr, 10);
		// 0 <= num <= 65535
		if(num < 0 || num > 65535) {
			JOptionPane.showMessageDialog(mf, "请输入0-65535的十进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				(ButtonCommand.EMIT_CENT_FREQ_CONF.s2fpga | num)));
	}
	/**
	 * 获要配置到fpga0的任意数据
	 * @return
	 */
	public void anyDataFpga0Conf() {
		String numStr = mf.anyDataFpga0Conf();
		if(! numStr.matches("([\\dabcdef]){1,8}")) {	//使用正则表达式，防止不输入数据或输入的不是16进制数字
			JOptionPane.showMessageDialog(mf, "请输入正确内容。");
			return;
		}
		long num = Long.valueOf(numStr, 16);
		// 0 <= num <= 65535
		if(num < 0 || num > Integer.MAX_VALUE) {
			JOptionPane.showMessageDialog(mf, "请输入31bit表示范围内的正十六进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga0Ip(), MainThread.PORT_2,
				(ButtonCommand.FPGA0_ANY.s2fpga | (int)num)));
	}
	/**
	 * 获要配置到fpga1的任意数据
	 * @return
	 */
	public void anyDataFpga1Conf() {
		String numStr = mf.anyDataFpga1Conf();
		if(! numStr.matches("([\\dabcdef]){1,8}")) {	//使用正则表达式，防止不输入数据或输入的不是16进制数字
			JOptionPane.showMessageDialog(mf, "请输入正确内容。");
			return;
		}
		long num = Long.valueOf(numStr, 16);
		// 0 <= num <= 65535
		if(num < 0 || num > Integer.MAX_VALUE) {
			JOptionPane.showMessageDialog(mf, "请输入31bit表示范围内的正十六进制数字。");
			return;
		}
		urt.submitThread(urt.getSendCommandThread(
				mf.getFpga1Ip(), MainThread.PORT_2,
				(ButtonCommand.FPGA1_ANY.s2fpga | (int)num)));
	}
	/**
	 * 射频干扰抵消控制程序调用
	 */
	public void rfBoardCtrExe() {
		File file = new File(System.getProperty("user.dir"));		
		// get parent dir		
		String parentPath = file.getParent();
		final String EXE_PATH = parentPath + "/RF_GUI_WB/MicroWaveConfig_v1.1.exe";
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String iperfCommand = EXE_PATH;

				try {
					Runtime.getRuntime().exec(iperfCommand);
				} catch (Exception ioe) {
					JOptionPane
							.showMessageDialog(
									null,
									"<html>"
											+ "Impossible to start the rfCance.exe executable located here : <br>"
											+ new File(iperfCommand)
													.getAbsolutePath()
											+ "</html>", "Error",
									JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}
	
	//init
	public void init(MainFrame mainFrame, UdpSocket udpSocket, UdpRelatedThreads urt) {
		this.mf = mainFrame;
		this.udpSocket = udpSocket;
		this.urt = urt;
	}
	
	private MainFrame mf;
	private UdpSocket udpSocket;
	private UdpRelatedThreads urt;
}
