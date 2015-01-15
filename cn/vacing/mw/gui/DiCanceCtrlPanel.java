package cn.vacing.mw.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import cn.vacing.mw._main.ButtonCommand;
import cn.vacing.mw.exception.NumInputOutOfBounds;

public class DiCanceCtrlPanel extends JPanel {
	/**
	 * 数字干扰抵消复位按钮
	 */
	public void diCanceResetStart() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				diCanceReset.setText("复位中...");
				diCanceReset.setActionCommand(ButtonCommand.DI_CANCE_RESET_STOP
						.name());
			}
		});
	}
	public void diCanceResetStop() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				diCanceReset.setText("复位");
				diCanceReset
						.setActionCommand(ButtonCommand.DI_CANCE_RESET_START
								.name());
			}
		});
	}

	/**
	 * 数字干扰抵消系数更新按钮
	 */
	public void paramUpdateStart() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				paramUpdate.setText("系数更新中...");
				paramUpdate.setActionCommand(ButtonCommand.DI_PARAM_UP_STOP
						.name());
			}
		});
	}
	public void paramUpdateStop() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				paramUpdate.setText("系数更新");
				paramUpdate.setActionCommand(ButtonCommand.DI_PARAM_UP_START
						.name());
			}
		});
	}

	/**
	 * 直流偏置校准 
	 */
	public void dirCurrCorr() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				/* do nothing now */
			}
		});
	}

	/**
	 * 接收时延配置
	 * @throws NumInputOutOfBounds
	 */
	public String receTimeDelayConf() {
		return receTimeDelay.getText();
	}

	/**
	 * 反馈时延配置
	 */
	public String feedbackTimeDelayConf() {
		return feedbackTimeDelay.getText();
	}
	
	/**
	 * RLS缓存长度配置
	 */
	public String catchLengthConf() {
		return catchLength.getText();
	}
	
	/**
	 * 构造方法
	 * @param buttonEvents
	 */
	public DiCanceCtrlPanel(ActionListener buttonEvents) {
		this.buttonEvents = buttonEvents;
		diCanceConfPanelInit();
	}

	private void diCanceConfPanelInit() {
		setLayout(new GridBagLayout());
		Border borderTemp = BorderFactory.createEtchedBorder();
		Border titledBorder = BorderFactory.createTitledBorder(borderTemp,
				"数字干扰抵消控制");
		setBorder(titledBorder);

		GridBagConstraints gbc;

		diCanceReset = new JToggleButton("复位中...");
		diCanceReset.doClick();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		diCanceReset
				.setActionCommand(ButtonCommand.DI_CANCE_RESET_STOP.name());
		diCanceReset.addActionListener(buttonEvents);
		add(diCanceReset, gbc);

		paramUpdate = new JToggleButton("系数更新");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		paramUpdate.setActionCommand(ButtonCommand.DI_PARAM_UP_START.name());
		paramUpdate.addActionListener(buttonEvents);
		add(paramUpdate, gbc);

		dirCurrCorr = new JButton("直偏校准");
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		dirCurrCorr.setActionCommand(ButtonCommand.DIR_CURR_CORRECT.name());
		dirCurrCorr.addActionListener(buttonEvents);
		add(dirCurrCorr, gbc);

		JLabel receTimeDelayLabel = new JLabel("接收时延：");
		receTimeDelayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(10, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(receTimeDelayLabel, gbc);
		receTimeDelay = new JTextField(10);
		receTimeDelay.setToolTipText("范围：0-63");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(10, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(receTimeDelay, gbc);
		receTimeDelayConf = new JButton("配置");
		receTimeDelayConf.addActionListener(buttonEvents);
		receTimeDelayConf.setActionCommand(ButtonCommand.DI_RECE_TIME_DELAY
				.name());
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(10, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(receTimeDelayConf, gbc);

		JLabel feedbackTimeDelayLabel = new JLabel("反馈延时：");
		feedbackTimeDelayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(feedbackTimeDelayLabel, gbc);
		feedbackTimeDelay = new JTextField(10);
		feedbackTimeDelay.setToolTipText("范围：0-63");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(feedbackTimeDelay, gbc);
		feedbackTimeDelayConf = new JButton("配置");
		feedbackTimeDelayConf.addActionListener(buttonEvents);
		feedbackTimeDelayConf
				.setActionCommand(ButtonCommand.DI_FEEDBACK_TIME_DELAY.name());
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(feedbackTimeDelayConf, gbc);

		JLabel catchLengthLabel = new JLabel("缓存长度：");
		catchLengthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(catchLengthLabel, gbc);
		catchLength = new JTextField(10);
		catchLength.setToolTipText("范围：24-32767");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(catchLength, gbc);
		catchLengthConf = new JButton("配置");
		catchLengthConf.addActionListener(buttonEvents);
		catchLengthConf.setActionCommand(ButtonCommand.DI_CATCH_LENGTH.name());
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(catchLengthConf, gbc);

		/**
		 * 填充空间。增加空间以后需要修改下面的内容。
		 */
		// 下侧
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 6;
		JLabel j1 = new JLabel();
		// j1.setBackground(Color.RED);
		j1.setOpaque(true);
		add(j1, gbc);

		// 右侧
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 0;
		// gbc.ipadx = 5;
		// gbc.ipady = 5;
		// gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0.5;
		// gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 4;
		gbc.gridwidth = 3;
		JLabel j2 = new JLabel();
		// j2.setBackground(Color.GREEN);
		j2.setOpaque(true);
		add(j2, gbc);
	}

	// 全部按钮事件
	private ActionListener buttonEvents;

	// 数字干扰抵消面板
	private JToggleButton diCanceReset; // 数字干扰抵消复位
	private JButton dirCurrCorr; // 直流偏置校准
	private JToggleButton paramUpdate; // 系数更新
	private JTextField receTimeDelay; // 接收时延
	private JButton receTimeDelayConf;
	private JTextField feedbackTimeDelay; // 反馈时延
	private JButton feedbackTimeDelayConf;
	private JTextField catchLength; // RLS缓存长度
	private JButton catchLengthConf;
	
	private static final long serialVersionUID = 1L;
}
