package cn.vacing.mw.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import cn.vacing.mw._main.ButtonCommand;

public class LinkCtrlPanel extends JPanel{

	/**
	 * 获要配置到fpga0的任意数据
	 * @return
	 */
	public String anyDataFpga0Conf() {
		return anyDataFpga0.getText();
	}
	/**
	 * 获要配置到fpga1的任意数据
	 * @return
	 */
	public String anyDataFpga1Conf() {
		return anyDataFpga1.getText();
	}
	/**
	 * 返回timeSynGate的值
	 */
	public String getTimeSynGate() {
		return timeSynGate.getText();
	}
	
	/**
	 * 获取配置的发射中频
	 * @return
	 */
	public String emitCentFreqConf() {
		return emitCentFreq.getText();
	}
	/**
	 * 打开AD1
	 */
	public void openAD1()
	{
		ad1Ctrl.setText("关AD1");
		ad1Ctrl.setActionCommand(ButtonCommand.AD1_CLOSE.name());
	}
	/**
	 * 关闭AD1
	 */
	public void closeAD1()
	{
		ad1Ctrl.setText("开AD1");
		ad1Ctrl.setActionCommand(ButtonCommand.AD1_OPEN.name());
	}
	/**
	 * 打开AD2
	 */
	public void openAD2()
	{
		ad2Ctrl.setText("关AD2");
		ad2Ctrl.setActionCommand(ButtonCommand.AD2_CLOSE.name());
	}
	/**
	 * 关闭AD2
	 */
	public void closeAD2()
	{
		ad2Ctrl.setText("开AD2");
		ad2Ctrl.setActionCommand(ButtonCommand.AD2_OPEN.name());
	}
	
	/**
	 * 打开DA
	 */
	public void openDA()
	{
		daCtrl.setText("关DA");
		daCtrl.setActionCommand(ButtonCommand.DA_CLOSE.name());
	}
	/**
	 * 关闭DA
	 */
	public void closeDA()
	{
		daCtrl.setText("开DA");
		daCtrl.setActionCommand(ButtonCommand.DA_OPEN.name());
	}
	/**
	 * 频域平滑段数配置
	 */
	public String smoothSegsConf() {
		return smoothSegs.getText();
	}
	
	/**
	 * 链路复位
	 */
	public void linkResetOn() {
		linkReset.setText("复位中...");
		linkReset.setActionCommand(ButtonCommand.LINK_RESET_OFF.name());
	}
	/**
	 * 取消链路复位
	 */
	public void linkResetOff() {
		linkReset.setText("复位链路");
		linkReset.setActionCommand(ButtonCommand.LINK_RESET_ON.name());
	}
	
	/**
	 * 链路控制
	 */
	public LinkCtrlPanel(ActionListener buttonEvents) {
		this.buttonEvents = buttonEvents;
		setLayout(new GridLayout(1, 0, 0, 0));
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "链路控制");
		setBorder(titledBorder);
		
		baseCtrlPanel = baseCtrlPanelInit();
		add(baseCtrlPanel);
		
		rfCtrlPanel = rfCtrlPanelInit();
		add(rfCtrlPanel);
	}
	
	/**
	 * 链路控制之射频控制
	 */
	private JPanel rfCtrlPanelInit() {
		JPanel rfCtrlPanel = new JPanel();
		rfCtrlPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		rfCtrlPanel.setBorder(borderTemp);
		
		GridBagConstraints gbc;
		

		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;

		rfCtrlPanel.add(new JLabel(), gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 1;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		rfCtrlPanel.add(new JLabel(), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
//		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 3;
		rfCtrlPanel.add(new JLabel(), gbc);
		
		return rfCtrlPanel;
	}
	
	/**
	 * 链路控制之基带控制
	 */
	private JPanel baseCtrlPanelInit()
	{
		JPanel baseCtrlPanel = new JPanel();
		baseCtrlPanel.setLayout(new GridLayout(0, 3, 5, 5));
		Border borderTemp = BorderFactory.createEtchedBorder();
		baseCtrlPanel.setBorder(borderTemp);
	
		linkReset = new JToggleButton("复位链路");
		linkReset.addActionListener(buttonEvents);
		linkReset.setActionCommand(ButtonCommand.LINK_RESET_ON.name());
		
		rfBoardCtr = new JButton("<html><font color='red'><b>射频控制</b></body></html>");
		rfBoardCtr.setBackground(Color.RED);
		rfBoardCtr.setOpaque(true);
		rfBoardCtr.setActionCommand(ButtonCommand.RF_BOARD_CTR.name());
		rfBoardCtr.addActionListener(buttonEvents);
		
		ad1Ctrl = new JButton("开AD1");
		ad1Ctrl.setActionCommand(ButtonCommand.AD1_OPEN.name());
		ad1Ctrl.addActionListener(buttonEvents);
		ad1Ctrl.setEnabled(false);
		
		ad2Ctrl = new JButton("开AD2");
		ad2Ctrl.setActionCommand(ButtonCommand.AD2_OPEN.name());
		ad2Ctrl.addActionListener(buttonEvents);
		ad2Ctrl.setEnabled(false);
		
		daCtrl = new JButton("开DA");
		daCtrl.setActionCommand(ButtonCommand.DA_OPEN.name());
		daCtrl.addActionListener(buttonEvents);
		
		JLabel timeSynGateLabe = new JLabel("时间捕获：");
		timeSynGate = new JTextField();
		timeSynGateConf = new JButton("配置");
		timeSynGateConf.setActionCommand(ButtonCommand.TIME_SYN_CONFIG.name());
		timeSynGateConf.addActionListener(buttonEvents);
		timeSynGateConf.setEnabled(false);
		
		JLabel osciStepLabe = new JLabel("晶振步进:");
		osciStep = new JTextField();
		osciStepConf = new JButton("配置");
		osciStepConf.setActionCommand(ButtonCommand.OSCI_STEP_CONFIG.name());
		osciStepConf.addActionListener(buttonEvents);
		osciStepConf.setEnabled(false);
		
		JLabel emitCentFreqLabe = new JLabel("发射中频：");
		emitCentFreq = new JTextField();
		emitCentFreq.setToolTipText("范围：0-65535");
		emitCentFreqConf = new JButton("配置");
		emitCentFreqConf.setActionCommand(ButtonCommand.EMIT_CENT_FREQ_CONF.name());
		emitCentFreqConf.addActionListener(buttonEvents);
		
		JLabel smoothSegsLabe = new JLabel("平滑段数：");
		smoothSegs = new JTextField();
		smoothSegs.setToolTipText("范围：0-63");
		smoothSegsConf = new JButton("配置");
		smoothSegsConf.setActionCommand(ButtonCommand.SMOOTH_SEGS_CONF.name());
		smoothSegsConf.addActionListener(buttonEvents);

		JLabel anyDataFpga0Labe = new JLabel("FPGA0 ANY:");
		anyDataFpga0 = new JTextField();
		anyDataFpga0.setToolTipText("32bit内容发送到fpga0");
		anyDataFpga0Conf = new JButton("配置");
		anyDataFpga0Conf.setActionCommand(ButtonCommand.FPGA0_ANY.name());
		anyDataFpga0Conf.addActionListener(buttonEvents);
		
		JLabel anyDataFpga1Labe = new JLabel("FPGA1 ANY:");
		anyDataFpga1 = new JTextField();
		anyDataFpga1.setToolTipText("32bit内容发送到fpga1");
		anyDataFpga1Conf = new JButton("配置");
		anyDataFpga1Conf.setActionCommand(ButtonCommand.FPGA1_ANY.name());
		anyDataFpga1Conf.addActionListener(buttonEvents);
		
		
		//row 1
		baseCtrlPanel.add(linkReset);
		baseCtrlPanel.add(rfBoardCtr);
		baseCtrlPanel.add(new JLabel());
		//row 2
		baseCtrlPanel.add(ad1Ctrl);
		baseCtrlPanel.add(ad2Ctrl);
		baseCtrlPanel.add(daCtrl);

		//row 3
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 4
		baseCtrlPanel.add(emitCentFreqLabe);
		baseCtrlPanel.add(emitCentFreq);
		baseCtrlPanel.add(emitCentFreqConf);
		//row 5
		baseCtrlPanel.add(smoothSegsLabe);
		baseCtrlPanel.add(smoothSegs);
		baseCtrlPanel.add(smoothSegsConf);
		//row 6
		baseCtrlPanel.add(osciStepLabe);
		baseCtrlPanel.add(osciStep);
		baseCtrlPanel.add(osciStepConf);
		//row 7
		baseCtrlPanel.add(timeSynGateLabe);
		baseCtrlPanel.add(timeSynGate);
		baseCtrlPanel.add(timeSynGateConf);
		

		//row 8
		baseCtrlPanel.add(anyDataFpga0Labe);
		baseCtrlPanel.add(anyDataFpga0);
		baseCtrlPanel.add(anyDataFpga0Conf);
		//row 9
		baseCtrlPanel.add(anyDataFpga1Labe);
		baseCtrlPanel.add(anyDataFpga1);
		baseCtrlPanel.add(anyDataFpga1Conf);
		//row 10
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 11
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		
		return baseCtrlPanel;
	}
	
	//全部按钮事件
	private ActionListener buttonEvents;
	
	//链路控制-基带控制面板
	private JPanel baseCtrlPanel;
	private JButton ad1Ctrl;	//AD1控制
	private JButton ad2Ctrl;	//AD2控制
	private JButton daCtrl;	//DA控制
	private JToggleButton linkReset;	//链路复位

	private JButton osciStepConf;			//晶振步进控制
	private JTextField osciStep;			//晶振步进
	private JTextField timeSynGate;			//时间捕获门限
	private JButton timeSynGateConf;			//时间捕获门限控制
	private JTextField emitCentFreq;			//发射中频
	private JButton emitCentFreqConf;			//发射中频配置
	private JTextField smoothSegs;				//平滑段数
	private JButton smoothSegsConf;				//平滑段数配置
	
	private JTextField anyDataFpga0;
	private JButton anyDataFpga0Conf;
	private JTextField anyDataFpga1;
	private JButton anyDataFpga1Conf;
	
	//链路控制-射频控制面板
	private JPanel rfCtrlPanel;
	private JButton rfBoardCtr;
}
