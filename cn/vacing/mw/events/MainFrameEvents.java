package cn.vacing.mw.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cn.vacing.mw._main.ButtonCommand;
import cn.vacing.mw._main.FinalVar;
import cn.vacing.mw._main.MainThread;
import cn.vacing.mw.gui.MainFrame;
import cn.vacing.mw.threads.UdpRelatedThreads;
import cn.vacing.mw.udp.UdpSocket;

public class MainFrameEvents implements ActionListener, WindowListener {



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();


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
		
		//命令处理
		switch(Enum.valueOf(ButtonCommand.class, e.getActionCommand())) {
		case LOG_IN_CONFIRM: 
			loginSucess();
			MainThread.mainInit(mainFrame, this);
			break;
		
		//数字干扰抵消
		case DIR_CURR_CORRECT:		//直流偏置校准
			System.out.println("digital cancellation-1");
			digitCanceEvents.dirCurrCorr();
			break;
		case DI_CANCE_RESET_START:	//开始复位
			System.out.println("digital cancellation-2");
			digitCanceEvents.diCanceResetStart();
			break;
		case DI_CANCE_RESET_STOP:	//停止复位
			System.out.println("digital cancellation-2.5");
			digitCanceEvents.diCanceResetStop();
			break;
		case DI_PARAM_UP_START:		//开始参数更新
			System.out.println("digital cancellation-3");
			digitCanceEvents.paramUpdateStart();
			break;
		case DI_PARAM_UP_STOP:		//停止参数更新
			System.out.println("digital cancellation-3.5");
			digitCanceEvents.paramUpdateStop();
			break;
		case DI_RECE_TIME_DELAY:	//接收时延配置
			System.out.println("digital cancellation-4");
			digitCanceEvents.receTimeDelayConf();
			break;
		case DI_CATCH_LENGTH:		//缓存长度配置
			System.out.println("digital cancellation-5");
			digitCanceEvents.catchLengthConf();
			break;
		case DI_FEEDBACK_TIME_DELAY://反馈延时配置
			System.out.println("digital cancellation-6");
			digitCanceEvents.feedbackTimeDelayConf();
			break;
			
		//性能展示
		case SHOW_CONSTELLATION:	//显示星座图面板
			performEvents.showConstellation();
			break;
		case SHOW_CANCE_PERFORM:	//显示频谱对比面板
			performEvents.showCancePerform();
			break;
		default:
			System.out.println(e.getActionCommand() + ": command is unused!!!!");
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
					JOptionPane.showMessageDialog(
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
		digitCanceEvents.init(mainFrame, urt);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if (udpSocket == null)
			return;
		udpSocket.closeUdp();

		System.exit(0);
	}
	private MainFrame mainFrame;
	private UdpSocket udpSocket;
	private UdpRelatedThreads urt;
	private PerformEvents performEvents = new PerformEvents();
	private LinkCtrlEvents linkCtrlEvents = new LinkCtrlEvents();
	private DigitCanceEvents digitCanceEvents = new DigitCanceEvents();
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
