﻿package cn.vacing.mw.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import cn.vacing.mw._main.ButtonCommand;


/**
 * 链路状态控制界面，显示在主界面中心
 * @author Gavin
 *
 */
public class GlobalControlPanel extends JPanel{
	public GlobalControlPanel(ActionListener buttonEvents)
	{
		this.buttonEvents = buttonEvents;
		setLayout(new GridLayout(0, 2, 10, 10));
		linkCtrlPanel = new LinkCtrlPanel(buttonEvents);
		performanceShowPanel = performanceShowPanelInit();
		diCanceCtrPanel = new DiCanceCtrlPanel(buttonEvents);
		rfCanceCtrPanel = rfCanceCtrPanelInit();
		add(linkCtrlPanel);
		add(performanceShowPanel);
		add(diCanceCtrPanel);
		add(rfCanceCtrPanel);
	}
	
	private JPanel rfCanceCtrPanelInit()
	{
		JPanel rfCanceCtrPanel = new JPanel();
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
		
		return rfCanceCtrPanel;
	}
	
	private JPanel performanceShowPanelInit()
	{
		JPanel performanceShowPanel = new JPanel();
		performanceShowPanel.setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp, "性能分析");
		performanceShowPanel.setBorder(titledBorder);
		
		GridBagConstraints gbc;
		
		showCancePerformace = new JButton("数字干扰抵消性能");
		showCancePerformace.setActionCommand(ButtonCommand.SHOW_CANCE_PERFORM.name());
		showCancePerformace.addActionListener(buttonEvents);
		gbc = new GridBagConstraints();
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		performanceShowPanel.add(showCancePerformace, gbc);
		
		showConstellation = new JButton("链路星座图");
		showConstellation.setActionCommand(ButtonCommand.SHOW_CONSTELLATION.name());
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
		
		return performanceShowPanel;
	}

	//4 parts of this panel
	public final LinkCtrlPanel linkCtrlPanel;
	public final JPanel performanceShowPanel;	
	public final DiCanceCtrlPanel diCanceCtrPanel;
	public final JPanel rfCanceCtrPanel;
	
	//全部按钮事件
	private ActionListener buttonEvents;
	
	//性能展示面板
	private JButton showCancePerformace;
	private JButton showConstellation;
	private JTextField blockErrorRate;
	
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
	
	private static final long serialVersionUID = -8448425938245945473L;	
}
