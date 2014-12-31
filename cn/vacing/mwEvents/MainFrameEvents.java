package cn.vacing.mwEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cn.vacing.mw.FinalVar;
import cn.vacing.mw.MainThread;
import cn.vacing.mwGui.MainFrame;
import cn.vacing.mwThreads.UdpRelatedThreads;
import cn.vacing.mwUdp.UdpSocket;

public class MainFrameEvents implements ActionListener, WindowListener {

	private MainFrame mainFrame;
	private UdpSocket udpSocket;
	private UdpRelatedThreads urt;
	private PerformEvents performEvents = new PerformEvents();
	private LinkCtrlEvents linkCtrlEvents = new LinkCtrlEvents();
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();

		//login
		if (command.equals(FinalVar.LOG_IN_CONFIRM)) {
			loginSucess();
			MainThread.mainInit(mainFrame, this);
			return;
		}
		
		//link control
		if (command.equals(FinalVar.AD1_OPEN)) {
			linkCtrlEvents.openAD1();
			return;
		}
		if (command.equals(FinalVar.AD1_CLOSE)) {
			linkCtrlEvents.closeAD1();
			return;
		}
		if (command.equals(FinalVar.AD2_OPEN)) {
			linkCtrlEvents.openAD2();
			return;
		}
		if (command.equals(FinalVar.AD2_CLOSE)) {
			linkCtrlEvents.closeAD2();
			return;
		}
		if (command.equals(FinalVar.DA1_OPEN)) {
			linkCtrlEvents.openDA1();
			return;
		}
		if (command.equals(FinalVar.DA1_CLOSE)) {
			linkCtrlEvents.closeDA1();
			return;
		}
		if (command.equals(FinalVar.DA2_OPEN)) {
			linkCtrlEvents.openDA2();
			return;
		}
		if (command.equals(FinalVar.DA2_CLOSE)) {
			linkCtrlEvents.closeDA2();
			return;
		}
		if (command.equals(FinalVar.RF_BOARD_CTR)) {
			rfBoardCtrExe();
			return;
		}
		if	(command.equals(FinalVar.TIME_SYN_CONFIG))	{
			linkCtrlEvents.timeSynGateConfig();
			return;
		}
		
		//rf cancellation control
//		if (command.equals(FinalVar.RF_CANCE_EXE)) {
//			rfCanceExe();
//			return;
//		}
		
		
		//performance watching
		if (command.equals(FinalVar.SHOW_CANCE_PERFORM)) {
			performEvents.showCancePerform();
			return;
		}
		if (command.equals(FinalVar.SHOW_CONSTELLATION)) {
			performEvents.showConstellation();
			return;
		}

	}

	/**
	 * 射频干扰抵消控制程序调用
	 */
	private void rfBoardCtrExe() {
		final String EXE_PATH = "./exteriorExe/RF_GUI_WB/MicroWaveConfig_v1.1.exe";

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

	/**
	 * 登录成功
	 */
	private void loginSucess() {
		mainFrame.loginSuccess();
	}

	/**
	 * 初始设定主窗体，用于完成登录事件
	 * @param mf
	 */
	public void setMf(MainFrame mf) {
		this.mainFrame = mf;
	}
	/**
	 * 初始化窗体各部件事件类
	 */
	public void init(MainFrame mainFrame, UdpSocket udpSocket, UdpRelatedThreads urt) {
		this.mainFrame = mainFrame;
		this.udpSocket = udpSocket;
		this.urt = urt;
		linkCtrlEvents.init(mainFrame, udpSocket, urt);
		performEvents.init(mainFrame, urt);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if (udpSocket == null)
			return;
		udpSocket.closeUdp();

		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
