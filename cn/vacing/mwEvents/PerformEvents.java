package cn.vacing.mwEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

import cn.vacing.mw.FinalVar;
import cn.vacing.mw.SpectrumShowRoutine;
import cn.vacing.mwGui.MainFrame;
import cn.vacing.mwThreads.UdpRelatedThreads;
import cn.vacing.mwUdp.UdpSocket;
import cn.vacing.perfomaceGui.ConstellationDisplay;
import cn.vacing.perfomaceGui.SpectrumDisplay;

public class PerformEvents implements ActionListener, WindowListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();

		if (command.equals(FinalVar.CANCE_PERFORM_START)) {
			cancePerformStart();
			return;
		}
		if (command.equals(FinalVar.CANCE_PERFORM_STOP)) {
			cancePerformStop();
			return;
		}
		if (command.equals(FinalVar.CONSTELLATION_START)) {
			constellationStart();
			return;
		}
		if (command.equals(FinalVar.CONSTELLATION_STOP)) {
			constellationStop();
			return;
		}
	}
	
	/**
	 * 开始显示干扰抵消性能频谱
	 */
	public void cancePerformStart() {
		System.out.println("perform start");
		showSpectrum = true;
	}
	
	/**
	 * 停止显示抵消性能频谱
	 */
	public void cancePerformStop() {
		System.out.println("perform stop");
		showSpectrum = false;
	}
	
	/**
	 * 开始显示星座图
	 */
	public void constellationStart() {
		System.out.println("constellationStart");
		urt.submitThread(urt.new GetUdpDataThread(mainFrame.getFpga1Ip(), 
				FinalVar.PORT_2,
				FinalVar.CONSTELLATION_START_COMMAND, 
				new SpectrumShowRoutine(spectrumDisplay)));
	}
	
	/**
	 * 停止显示星座图
	 */
	public void constellationStop() {
		System.out.println("constellationStop");

	}
	
	/**
	 * 显示数字干扰抵消窗口
	 */
	public void showCancePerform() {
		spectrumDisplay = SpectrumDisplay.getInstance(this, this);
		spectrumDisplay.setVisible(true);
	}

	/**
	 * 显示链路星座图窗口
	 */
	public void showConstellation() {
		constellationDisplay = ConstellationDisplay.getInstance(this, this);
		constellationDisplay.setVisible(true);
	}
	
	/**
	 * 设置主窗口
	 * @param mainFrame
	 */
	public void init(MainFrame mainFrame, UdpRelatedThreads urt) {
		this.mainFrame = mainFrame;
		this.urt = urt;
	}
	
	/**
	 * 添加定时器，满足条件的情况下定时获取数据
	 */
	public PerformEvents () {
		spectrumTimer = new Timer(false);
		spectrumTimerTask = new TimerTask() {
			@Override
			public void run() {
				if(showSpectrum) {
//					showSpectrum = false;
					System.out.println("Timer works" + 
										"\tIP:" + mainFrame.getFpga1Ip()
										+"\tPort:" + FinalVar.PORT_2);
					UdpRelatedThreads.GetUdpDataThread gud = urt.new GetUdpDataThread(mainFrame.getFpga1Ip(), 
							FinalVar.PORT_2,
							FinalVar.CANCE_PERFORM_START_COMMAND, 
							new SpectrumShowRoutine(spectrumDisplay));
					urt.submitThread(gud);	
				}
			}
		};
		spectrumTimer.schedule(spectrumTimerTask, 1000, 200);
	}
	private MainFrame mainFrame;
	private UdpRelatedThreads urt;
	private SpectrumDisplay spectrumDisplay;
	private ConstellationDisplay constellationDisplay;
	
	private Timer spectrumTimer;
	private TimerTask spectrumTimerTask;
	private Boolean showSpectrum = false;
	private Future<?> spectrumFuture;
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		cancePerformStop();
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
