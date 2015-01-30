package cn.vacing.mw.gui;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.vacing.mw._main.ButtonCommand;

/**
 * 登陆界面，使用半透明蒙版
 * @author Gavin
 *
 */
public class LoginPanel extends javax.swing.JPanel {
	/**
	 * 构造方法
	 */
	public LoginPanel(ActionListener buttonEvents) {
		this.buttonEvents = buttonEvents;
		initComponents(); // 调用初始化界面的方法
	}

	/**
	 * 初始化登录界面的方法
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		fpga0IpLab = new javax.swing.JLabel();
		fpga0Ip = new javax.swing.JTextField();
		fpga1IpLab = new javax.swing.JLabel();
		fpga1Ip = new javax.swing.JTextField();
		confirmButton = new javax.swing.JButton();
		closeButton = new javax.swing.JButton();

		setForeground(java.awt.Color.gray);
		setOpaque(false);			//设置不绘制所有像素点（透明）
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		setLayout(new java.awt.GridBagLayout());
		
		charFont = new Font("隶书", Font.BOLD, 20);
		fpga0IpLab.setFont(charFont);
		fpga0IpLab.setForeground(new java.awt.Color(255, 255, 255));
		fpga0IpLab.setText("FPGA0 IP：");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		add(fpga0IpLab, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipady = -5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		fpga0Ip.setFont(new Font("隶书", Font.BOLD, 20));
		fpga0Ip.setText(IP0);
		add(fpga0Ip, gridBagConstraints);

		fpga1IpLab.setFont(new Font("隶书", Font.BOLD, 20));
		fpga1IpLab.setForeground(java.awt.Color.white);
		fpga1IpLab.setText("FPGA1 IP：");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(fpga1IpLab, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.ipady = -5;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		fpga1Ip.setFont(new Font("隶书", Font.BOLD, 20));
		fpga1Ip.setText(IP1);
		add(fpga1Ip, gridBagConstraints);
		
		JLabel ipLocalLab = new JLabel("  本地IP：");
		ipLocalLab.setFont(new Font("隶书", Font.BOLD, 20));
		ipLocalLab.setForeground(java.awt.Color.white);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		add(ipLocalLab, gridBagConstraints);
		
		ipLocal = new JTextField();
		String localIp = IP_LOCAL;
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ipLocal.setText(localIp);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.ipady = -5;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		ipLocal.setFont(new Font("隶书", Font.BOLD, 20));
		add(ipLocal, gridBagConstraints);
		
		JLabel portLocalLab = new JLabel("本地端口：");
		portLocalLab.setFont(new Font("隶书", Font.BOLD, 20));
		portLocalLab.setForeground(java.awt.Color.white);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		add(portLocalLab, gridBagConstraints);
		portLocal = new JTextField("6001");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.ipady = -5;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		portLocal.setFont(new Font("隶书", Font.BOLD, 20));
		add(portLocal, gridBagConstraints);

		confirmButton.setText("确认");
		confirmButton.setActionCommand(ButtonCommand.LOG_IN_CONFIRM.name());
		confirmButton.addActionListener(buttonEvents);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 40);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		add(confirmButton, gridBagConstraints);

		closeButton.setText("关闭");
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 55);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		add(closeButton, gridBagConstraints);
	}

	private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private void formMouseClicked(java.awt.event.MouseEvent evt) {
		JOptionPane.showMessageDialog(this, "请先确认FPGA端IP地址！！");
	}

	/**
	 * 获取登录时设定的fpga0的ip地址
	 * @return
	 */
	public String getFpga0Ip() {
		return fpga0Ip.getText();
	}
	
	/**
	 * 获取登录时设定的fpga1的ip地址
	 * @return
	 */
	public String getFpga1Ip() {
		return fpga1Ip.getText();
	}
	
	/**
	 * 获取登录时的本地ip
	 */
	public String getIPLocal() {
		return ipLocal.getText();
	}
	/**
	 * 获取登录时的本地端口
	 */
	public int getPortLocal() {
		return Integer.parseInt(portLocal.getText());
	}

	/**
	 * 绘制组件界面的方法
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // 获取2D绘图上下文
		Composite composite = g2.getComposite(); // 备份合成模式
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f)); // 设置绘图使用透明合成规则
		g2.fillRect(0, 0, getWidth(), getHeight()); // 使用当前颜色填充矩形空间
		g2.setComposite(composite); // 恢复原有合成模式
		super.paintComponent(g2); // 执行超类的组件绘制方法
	}
	
	private static final String IP0 = "192.168.1.29";
	private static final String IP1 = "192.168.1.31";
	private static final String IP_LOCAL = "192.168.1.112";
	
	private ActionListener buttonEvents;
	
	private Font charFont;
	private javax.swing.JButton closeButton;
	private javax.swing.JTextField fpga0Ip;
	private javax.swing.JTextField fpga1Ip;
	private javax.swing.JLabel fpga0IpLab;
	private javax.swing.JLabel fpga1IpLab;
	private javax.swing.JButton confirmButton;
	private JTextField ipLocal;
	private JTextField portLocal;
	
	private static final long serialVersionUID = 1L;

}