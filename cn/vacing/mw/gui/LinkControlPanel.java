package cn.vacing.mw.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import cn.vacing.mw._main.FinalVar;


/**
 * 链路状态控制界面，显示在主界面中心
 * @author Gavin
 *
 */
public class LinkControlPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8448425938245945473L;
	public LinkControlPanel(ActionListener buttonEvents)
	{
		this.buttonEvents = buttonEvents;
		initPanel();
	}
	
	
	/**
	 * 返回timeSynGate的值
	 */
	public String getTimeSynGate() {
		return timeSynGate.getText();
	}
	/**
	 * 打开AD1
	 */
	public void openAD1()
	{
		ad1Ctrl.setText("关AD1");
		ad1Ctrl.setActionCommand(FinalVar.AD1_CLOSE);
	}
	/**
	 * 关闭AD1
	 */
	public void closeAD1()
	{
		ad1Ctrl.setText("开AD1");
		ad1Ctrl.setActionCommand(FinalVar.AD1_OPEN);
	}
	/**
	 * 打开AD2
	 */
	public void openAD2()
	{
		ad2Ctrl.setText("关AD2");
		ad2Ctrl.setActionCommand(FinalVar.AD2_CLOSE);
	}
	/**
	 * 关闭AD2
	 */
	public void closeAD2()
	{
		ad2Ctrl.setText("开AD2");
		ad2Ctrl.setActionCommand(FinalVar.AD2_OPEN);
	}
	
	/**
	 * 打开DA1
	 */
	public void openDA1()
	{
		da1Ctrl.setText("关DA1");
		da1Ctrl.setActionCommand(FinalVar.DA1_CLOSE);
	}
	/**
	 * 关闭DA1
	 */
	public void closeDA1()
	{
		da1Ctrl.setText("开DA1");
		da1Ctrl.setActionCommand(FinalVar.DA1_OPEN);
	}
	/**
	 * 打开DA2
	 */
	public void openDA2()
	{
		da2Ctrl.setText("关DA2");
		da2Ctrl.setActionCommand(FinalVar.DA2_CLOSE);
	}
	/**
	 * 关闭DA2
	 */
	public void closeDA2()
	{
		da2Ctrl.setText("开DA2");
		da2Ctrl.setActionCommand(FinalVar.DA2_OPEN);
	}
	
	/**
	 * 初始化控制面板
	 */
	private void initPanel()
	{
		setLayout(new GridLayout(0, 2, 10, 10));
		linkCtrlPanelInit();
		performanceShowPanelInit();
		baseCanceCtrPanelInit();
		rfCanceCtrPanelInit();
		add(linkCtrlPanel);
		add(performanceShowPanel);
		add(baseCanceCtrPanel);
		add(rfCanceCtrPanel);
	}
	
	/**
	 * 链路控制
	 */
	private void linkCtrlPanelInit() {
		linkCtrlPanel = new JPanel();
		linkCtrlPanel.setLayout(new GridLayout(1, 0, 0, 0));
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "链路控制");
		linkCtrlPanel.setBorder(titledBorder);
		
		baseCtrlPanelInit();
		linkCtrlPanel.add(baseCtrlPanel);
		
		rfCtrlPanelInit();
		linkCtrlPanel.add(rfCtrlPanel);
	}
	
	/**
	 * 链路控制之射频控制
	 */
	private void rfCtrlPanelInit() {
		rfCtrlPanel = new JPanel();
		rfCtrlPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		rfCtrlPanel.setBorder(borderTemp);
		
		GridBagConstraints gbc;
		
		rfBoardCtr = new JButton("射频板控制");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		rfBoardCtr.setActionCommand(FinalVar.RF_BOARD_CTR);
		rfBoardCtr.addActionListener(buttonEvents);
		rfCtrlPanel.add(rfBoardCtr, gbc);
		
		
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
	}
	
	/**
	 * 链路控制之基带控制
	 */
	private void baseCtrlPanelInit()
	{
		baseCtrlPanel = new JPanel();
		baseCtrlPanel.setLayout(new GridLayout(0, 3, 5, 5));
		Border borderTemp = BorderFactory.createEtchedBorder();
		baseCtrlPanel.setBorder(borderTemp);
	
		ad1Ctrl = new JButton("开AD1");
		ad1Ctrl.setActionCommand(FinalVar.AD1_OPEN);
		ad1Ctrl.addActionListener(buttonEvents);
		
		ad2Ctrl = new JButton("开AD2");
		ad2Ctrl.setActionCommand(FinalVar.AD2_OPEN);
		ad2Ctrl.addActionListener(buttonEvents);
		
		da1Ctrl = new JButton("开DA1");
		da1Ctrl.setActionCommand(FinalVar.DA1_OPEN);
		da1Ctrl.addActionListener(buttonEvents);
		
		da2Ctrl = new JButton("开DA2");
		da2Ctrl.setActionCommand(FinalVar.DA2_OPEN);
		da2Ctrl.addActionListener(buttonEvents);
		
		JLabel timeSynGateLabe = new JLabel("时间捕获：");
		timeSynGate = new JTextField();
		timeSynGateCtr = new JButton("配置");
		timeSynGateCtr.setActionCommand(FinalVar.TIME_SYN_CONFIG);
		timeSynGateCtr.addActionListener(buttonEvents);
		
		JLabel osciStepLabe = new JLabel("晶振步进:");
		osciStep = new JTextField();
		osciStepCtr = new JButton("配置");

		//row 1
		baseCtrlPanel.add(ad1Ctrl);
		baseCtrlPanel.add(ad2Ctrl);
		baseCtrlPanel.add(new JLabel());
		//row 2
		baseCtrlPanel.add(da1Ctrl);
		baseCtrlPanel.add(da2Ctrl);
		baseCtrlPanel.add(new JLabel());
		//row 3
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 4
		baseCtrlPanel.add(osciStepLabe);
		baseCtrlPanel.add(osciStep);
		baseCtrlPanel.add(osciStepCtr);
		//row 5
		baseCtrlPanel.add(timeSynGateLabe);
		baseCtrlPanel.add(timeSynGate);
		baseCtrlPanel.add(timeSynGateCtr);
		//row 6
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 7
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 8
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 9
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		//row 10
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
		baseCtrlPanel.add(new JLabel());
	}
	
	private void rfCanceCtrPanelInit()
	{
		rfCanceCtrPanel = new JPanel();
		rfCanceCtrPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "射频干扰抵消控制");
		rfCanceCtrPanel.setBorder(titledBorder);
		
		GridBagConstraints gbc;
		JLabel temp;
		
		JPanel initParamPane = new JPanel();
		Border borderTemp2 = BorderFactory.createEtchedBorder();
		initParamPane.setBorder(borderTemp2);
		initParamPane.setLayout(new GridLayout(0, 3, 2, 2));
		JLabel channelLabe = new JLabel("通道");
		channelLabe.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel amptitudeLabe = new JLabel("幅度");
		amptitudeLabe.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel phaseLabe = new JLabel("相位");
		phaseLabe.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel channel1Name = new JLabel("通道1：");
		channel1Name.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel channel2Name = new JLabel("通道2：");
		channel2Name.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel channel3Name = new JLabel("通道3：");
		channel3Name.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel channel4Name = new JLabel("通道4：");
		channel4Name.setHorizontalAlignment(SwingConstants.RIGHT);
		chan1Ampt = new JTextField(7);
		chan2Ampt = new JTextField(7);
		chan3Ampt = new JTextField(7);
		chan4Ampt = new JTextField(7);
		chan1Phase = new JTextField(7);
		chan2Phase = new JTextField(7);
		chan3Phase = new JTextField(7);
		chan4Phase = new JTextField(7);
		initParamCtr = new JButton("参数配置");
//		rfCanceExe.setActionCommand(FinalVar.RF_CANCE_EXE);
		initParamCtr.addActionListener(buttonEvents);
		initParamPane.add(channelLabe);
		initParamPane.add(amptitudeLabe);
		initParamPane.add(phaseLabe);
		initParamPane.add(channel1Name);
		initParamPane.add(chan1Ampt);
		initParamPane.add(chan1Phase);
		initParamPane.add(channel2Name);
		initParamPane.add(chan2Ampt);
		initParamPane.add(chan2Phase);
		initParamPane.add(channel3Name);
		initParamPane.add(chan3Ampt);
		initParamPane.add(chan3Phase);
		initParamPane.add(channel4Name);
		initParamPane.add(chan4Ampt);
		initParamPane.add(chan4Phase);
		initParamPane.add(new JLabel());
		initParamPane.add(new JLabel());
		initParamPane.add(initParamCtr);
//		initParamPane.setPreferredSize(new Dimension(300, 180));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 5;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		rfCanceCtrPanel.add(initParamPane, gbc);
		
		searchStart = new JButton("搜索开始");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 7;
		gbc.insets = new Insets(10, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//		rfCanceExe.setActionCommand(FinalVar.RF_CANCE_EXE);
		searchStart.addActionListener(buttonEvents);
		rfCanceCtrPanel.add(searchStart, gbc);
		
		searchStop = new JButton("搜索停止");
		gbc = new GridBagConstraints();
		gbc.gridx = 1; 
		gbc.gridy = 7;
		gbc.insets = new Insets(10, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridwidth = 1;
//		rfCanceExe.setActionCommand(FinalVar.RF_CANCE_EXE);
		searchStop.addActionListener(buttonEvents);
		rfCanceCtrPanel.add(searchStop, gbc);
		
		searchReset = new JButton("搜索复位");
		gbc = new GridBagConstraints();
		gbc.gridx = 2; 
		gbc.gridy = 7;
		gbc.insets = new Insets(10, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//		rfCanceExe.setActionCommand(FinalVar.RF_CANCE_EXE);
		searchReset.addActionListener(buttonEvents);
		rfCanceCtrPanel.add(searchReset, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 8;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridheight = 9;
		temp = new JLabel();
//		temp.setBackground(Color.BLUE);
		temp.setOpaque(true);
		rfCanceCtrPanel.add(temp, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 3; 
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 5;
		temp = new JLabel();
//		temp.setBackground(Color.GREEN);
		temp.setOpaque(true);
		rfCanceCtrPanel.add(temp, gbc);
	}
	
	private void baseCanceCtrPanelInit()
	{
		baseCanceCtrPanel = new JPanel();
		baseCanceCtrPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "数字干扰抵消控制");
		baseCanceCtrPanel.setBorder(titledBorder);
		
		GridBagConstraints gbc;
		
//		private JButton baseCanceCtr;
//		private JTextField freqAverSegs;
//		private JButton freqAverSegsCtr;
//		private JTextField forgetFactor;
//		private JButton forgetFactorCtr;
		
		baseCanceCtr = new JButton("打开");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//		baseCanceCtr.setActionCommand(FinalVar.RF_BOARD_CTR);
		baseCanceCtr.addActionListener(buttonEvents);
		baseCanceCtrPanel.add(baseCanceCtr, gbc);
		
		JLabel freqAverSegsLabe = new JLabel("频域平滑段数：");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 1;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(10, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(freqAverSegsLabe, gbc);
		freqAverSegs = new JTextField(10);
		gbc = new GridBagConstraints();
		gbc.gridx = 1; 
		gbc.gridy = 1;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(10, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(freqAverSegs, gbc);
		freqAverSegsCtr = new JButton("配置");
		freqAverSegsCtr.addActionListener(buttonEvents);
		freqAverSegsCtr.setActionCommand(FinalVar.FREQ_AVER_SEGS_CONFIG);
		gbc = new GridBagConstraints();
		gbc.gridx = 2; 
		gbc.gridy = 1;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(10, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(freqAverSegsCtr, gbc);
		
		JLabel forgetFactorLabe = new JLabel("遗忘因子：");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 2;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(forgetFactorLabe, gbc);
		forgetFactor = new JTextField(10);
		gbc = new GridBagConstraints();
		gbc.gridx = 1; 
		gbc.gridy = 2;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(forgetFactor, gbc);
		forgetFactorCtr = new JButton("配置");
		gbc = new GridBagConstraints();
		gbc.gridx = 2; 
		gbc.gridy = 2;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		baseCanceCtrPanel.add(forgetFactorCtr, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 3;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		baseCanceCtrPanel.add(new JLabel(), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 3; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
//		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 4;
		baseCanceCtrPanel.add(new JLabel(), gbc);
	}

	private void performanceShowPanelInit()
	{
		performanceShowPanel = new JPanel();
		performanceShowPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "性能分析");
		performanceShowPanel.setBorder(titledBorder);
		
		GridBagConstraints gbc;
		
		showCancePerformace = new JButton("数字干扰抵消性能");
		showCancePerformace.setActionCommand(FinalVar.SHOW_CANCE_PERFORM);
		showCancePerformace.addActionListener(buttonEvents);
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		performanceShowPanel.add(showCancePerformace, gbc);
		
		showConstellation = new JButton("链路星座图");
		showConstellation.setActionCommand(FinalVar.SHOW_CONSTELLATION);
		showConstellation.addActionListener(buttonEvents);
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		performanceShowPanel.add(showConstellation, gbc);
		
		JPanel blockErrorRatePanel = new JPanel();
		blockErrorRatePanel.setLayout(new GridLayout(1, 0, 10, 0));
		JLabel blockErrorRateLabel = new JLabel("误块率: ");
		blockErrorRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		blockErrorRatePanel.add(blockErrorRateLabel);
		blockErrorRate = new JTextField();
//		blockErrorRate.setEditable(false);
		blockErrorRate.setBackground(Color.WHITE);
		blockErrorRatePanel.add(blockErrorRate);
//		blockErrorRatePanel.add(new JLabel("%"));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; 
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		performanceShowPanel.add(blockErrorRatePanel, gbc);
		
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 2;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		performanceShowPanel.add(new JLabel(), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2; 
		gbc.gridy = 0;
//		gbc.ipadx = 5;
//		gbc.ipady = 5;
//		gbc.insets = new Insets(10, 20, 30, 40);
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 3;
		performanceShowPanel.add(new JLabel(), gbc);
	}

	//4 parts of this panel
	private JPanel linkCtrlPanel;
	private JPanel performanceShowPanel;	
	private JPanel baseCanceCtrPanel;
	private JPanel rfCanceCtrPanel;

	//全部按钮事件
	private ActionListener buttonEvents;
	
	//链路控制-基带控制面板
	private JPanel baseCtrlPanel;
	private JButton ad1Ctrl;	//AD1控制
	private JButton ad2Ctrl;	//AD2控制
	private JButton da1Ctrl;	//DA1控制
	private JButton da2Ctrl;	//DA2控制
//	private JButton channelEstimationParam;	//信道估计参数
	private JButton osciStepCtr;			//晶振步进控制
	private JTextField osciStep;			//晶振步进
	private JTextField timeSynGate;			//时间捕获门限
	private JButton timeSynGateCtr;			//时间捕获门限控制
	
	//链路控制-射频控制面板
	private JPanel rfCtrlPanel;
	private JButton rfBoardCtr;
	
	
	//性能展示面板
	private JButton showCancePerformace;
	private JButton showConstellation;
	private JTextField blockErrorRate;
	
	//数字干扰抵消面板
	private JButton baseCanceCtr;
	private JTextField freqAverSegs;
	private JButton freqAverSegsCtr;
	private JTextField forgetFactor;
	private JButton forgetFactorCtr;
	
	
	//射频干扰抵消面板
	//射频抵消-初始参数
	private JTextField chan1Ampt;
	private JTextField chan2Ampt;
	private JTextField chan3Ampt;
	private JTextField chan4Ampt;
	private JTextField chan1Phase;
	private JTextField chan2Phase;
	private JTextField chan3Phase;
	private JTextField chan4Phase;
	private JButton initParamCtr;
	private JButton searchStart;
	private JButton searchStop;
	private JButton searchReset;
	
	
}
