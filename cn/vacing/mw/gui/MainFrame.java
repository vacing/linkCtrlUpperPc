package cn.vacing.mw.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.NebulaBrickWallSkin;
import org.jvnet.substance.theme.SubstanceUltramarineTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import cn.vacing.mw._main.ButtonCommand;
import cn.vacing.mw.exception.NumInputOutOfBounds;

/**
 * 完整界面
 * @author Gavin
 * 
 */
public class MainFrame extends JFrame {
	
//--------------------------------数字干扰抵消-----------------------------------
	public void diCanceResetStart() {
		gcp.diCanceCtrPanel.diCanceResetStart();
	}
	public void diCanceResetStop() {
		gcp.diCanceCtrPanel.diCanceResetStop();
	}
	public void diParamUpdateStart() {
		gcp.diCanceCtrPanel.paramUpdateStart();
	}
	public void diParamUpdateStop() {
		gcp.diCanceCtrPanel.paramUpdateStop();
	}
	public void diDirCurrCorr() {
		gcp.diCanceCtrPanel.dirCurrCorr();
	}
	public String diReceTimeDelayConf(){
		return gcp.diCanceCtrPanel.receTimeDelayConf();
	}
	public String diFeedbackTimeDelayConf(){
		return gcp.diCanceCtrPanel.feedbackTimeDelayConf();
	}
	public String diCatchLengthConf(){
		return gcp.diCanceCtrPanel.catchLengthConf();
	}
	//--------------------------------数字干扰抵消-----------------------------------	
	
	/**
	 * 频域平滑段数配置
	 */
	public void freqAverSegsConf()	{
		
	}
	
	/**
	 * 时间捕获门限控制
	 */
	public void timeSynGateConfig() {
		String timeSynGate = gcp.getTimeSynGate();
		lsp.stateChange(LinkStatParam.TIME_SYN_GATE, 1, timeSynGate);
	}
	/**
	 * 打开AD1
	 */
	public void openAD1() {
		gcp.openAD1();
		lsp.stateChange(LinkStatParam.AD1, 1, "打开");
	}
	public void closeAD1() {
		gcp.closeAD1();
		lsp.stateChange(LinkStatParam.AD1, 1, "关闭");
	}

	/**
	 * 打开AD2
	 */
	public void openAD2() {
		gcp.openAD2();
		lsp.stateChange(LinkStatParam.AD2, 1, "打开");
	}

	public void closeAD2() {
		gcp.closeAD2();
		lsp.stateChange(LinkStatParam.AD2, 1, "关闭");
	}

	/**
	 * 打开DA1
	 */
	public void openDA1() {
		gcp.openDA1();
		lsp.stateChange(LinkStatParam.DA1, 1, "打开");
	}
	public void closeDA1() {
		gcp.closeDA1();
		lsp.stateChange(LinkStatParam.DA1, 1, "关闭");
	}

	/**
	 * 打开DA2
	 */
	public void openDA2() {
		gcp.openDA2();
		lsp.stateChange(LinkStatParam.DA2, 1, "打开");
	}
	public void closeDA2() {
		gcp.closeDA2();
		lsp.stateChange(LinkStatParam.DA2, 1, "关闭");
	}

	/**
	 * 获取设定fpga1和2的IP地址
	 * @return
	 */
	public String getFpga1Ip() {
		return fpga1Ip;
	}

	public String getFpga2Ip() {
		return fpga2Ip;
	}
	public String getIpLocal() {
		return ipLocal;
	}
	public int getPortLocal() {
		return portLocal;
	}
	
	public MainFrame(ActionListener buttonAction, WindowListener windowAction) {
		super("微波全双工控制软件");
		this.buttonAction = buttonAction;
		this.windowAction = windowAction;
		initFrame();
	}

	/**
	 * 主界面初始化
	 */
	public void initFrame() {
		setLayout(new BorderLayout(3, 3));
		gcp = new GlobalControlPanel(buttonAction);
		getContentPane().add(gcp, BorderLayout.CENTER);

		lsp = new LinkStatPanel();
		lsp.initTable(LinkStatParam.getStatInstance());
		getContentPane().add(lsp, BorderLayout.WEST);

		linp = new LoginPanel(buttonAction);
		setGlassPane(linp);
		linp.setVisible(true);

		try {
			javax.swing.UIManager
					.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SubstanceLookAndFeel.setCurrentTheme(new SubstanceUltramarineTheme());
		SubstanceLookAndFeel.setSkin(new NebulaBrickWallSkin());
		SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
		SubstanceLookAndFeel
				.setCurrentWatermark(new SubstanceBubblesWatermark());
		SubstanceLookAndFeel
				.setCurrentBorderPainter(new StandardBorderPainter());
		SubstanceLookAndFeel
				.setCurrentGradientPainter(new StandardGradientPainter());

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 1200) / 2, (screenSize.height - 700) / 2,
				1200, 700);
		getContentPane().setBackground(Color.BLACK);
		addWindowListener(windowAction);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"./resource/huawei_logo.png"));
		setVisible(true);
	}

	/**
	 * 登陆成功的界面处理
	 */
	public void loginSuccess() {
		fpga1Ip = linp.getFpga1Ip();
		fpga2Ip = linp.getFpga2Ip();
		ipLocal = linp.getIPLocal();
		portLocal = linp.getPortLocal();
		linp.setVisible(false);
	}

	private String fpga1Ip;
	private String fpga2Ip;
	private String ipLocal;
	private int portLocal;

	private GlobalControlPanel gcp;
	private LinkStatPanel lsp;
	private LoginPanel linp;
	private ActionListener buttonAction;
	private WindowListener windowAction;

	private static final long serialVersionUID = 1L;
}
