package cn.vacing.mw.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import cn.vacing.mw._main.ButtonCommand;
import cn.vacing.mw._main.ConstellationShowRoutine;
import cn.vacing.mw._main.FinalVar;
import cn.vacing.mw._main.MainThread;
import cn.vacing.mw._main.SpectrumShowRoutine;
import cn.vacing.mw.gui.MainFrame;
import cn.vacing.mw.perfomace_gui.ConstellationDisplay;
import cn.vacing.mw.perfomace_gui.SpectrumDisplay;
import cn.vacing.mw.threads.UdpRelatedThreads;
import cn.vacing.mw.udp.UdpSocket;

public class PerformEvents implements ActionListener, WindowListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(Enum.valueOf(ButtonCommand.class, e.getActionCommand())) {
		case CANCE_PERFORM_START:
			cancePerformStart();
			break;
		case CANCE_PERFORM_STOP:
			cancePerformStop();
			break;
		case CONSTELLATION_START:
			constellationStart();
			break;
		case CONSTELLATION_STOP:
			constellationStop();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 开始显示干扰抵消性能频谱
	 */
	public void cancePerformStart() {
		synchronized (showSpectrum) {
			System.out.println("perform start");
			showSpectrum = true;
		}
	}
	
	/**
	 * 停止显示抵消性能频谱
	 */
	public void cancePerformStop() {
		synchronized (showSpectrum) {
			System.out.println("perform stop");
			showSpectrum = false;
		}
	}
	
	/**
	 * 开始显示星座图
	 */
	public void constellationStart() {
		synchronized (showConstellation) {
			System.out.println("constellation Start");
			showConstellation = true;
		}
	}
	
	/**
	 * 停止显示星座图
	 */
	public void constellationStop() {
		synchronized (showConstellation) {
			System.out.println("constellation Stop");
			showConstellation = false;
		}
	}
	
	/**
	 * 显示数字干扰抵消窗口
	 */
	public void showCancePerform() {
		spectrumDisplay.setVisible(true);
	}

	/**
	 * 显示链路星座图窗口
	 */
	public void showConstellation() {	
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
		performTimer = new Timer(false);
		spectrumDisplay = SpectrumDisplay.getInstance(this, this);
		constellationDisplay = ConstellationDisplay.getInstance(this, this);
		consShowRoutine = new ConstellationShowRoutine(constellationDisplay);
		spectrumTimerTask = new TimerTask() {
			@Override
			public void run() {
				synchronized (showSpectrum) {
					if(showSpectrum) {	//频谱显示
						if(future == null || future.isDone()) {	//首次进入，或UDP未阻塞
	//						showSpectrum = false;
							System.out.println("Timer works" + 
												"\tIP:" + mainFrame.getFpga1Ip()
												+"\tPort:" + MainThread.PORT_2);
							Callable<Integer> gud = urt.getGetSpectrumDataThread(mainFrame.getFpga1Ip(), 
									MainThread.PORT_2,
									ButtonCommand.CANCE_PERFORM_START.s2fpga, 
									new SpectrumShowRoutine(spectrumDisplay));
							future = urt.submitThread(gud);	
						} else {
							System.out.println("\nThread is Undone!!");
						}
					}
				}
				synchronized (showConstellation) {
					if(showConstellation) {	//星座图显示
						if(future == null || future.isDone()) {		//首次进入，或UDP未阻塞
	//						showConstellation = false;
							System.out.println("Timer works" + 
									"\tIP:" + mainFrame.getFpga1Ip()
									+"\tPort:" + MainThread.PORT_2);
							Callable<Integer> gud = urt.getGetConstellationDataThread(mainFrame.getFpga1Ip(), 
									MainThread.PORT_2,
									ButtonCommand.CONSTELLATION_START.s2fpga, 
									consShowRoutine);
							future = urt.submitThread(gud);	
						} else {
							System.out.println("\nThread is Undone!!");
						}
					}
				}
			}
		};
		performTimer.schedule(spectrumTimerTask, 1000, 100);
	}
	private MainFrame mainFrame;
	private UdpRelatedThreads urt;
	private SpectrumDisplay spectrumDisplay;
	private ConstellationDisplay constellationDisplay;
	private ConstellationShowRoutine consShowRoutine;	//星座图要滑动显示，因此不能每次新建
	
	private Timer performTimer;
	private TimerTask spectrumTimerTask;
	private volatile Boolean showSpectrum = false;
	private volatile Boolean showConstellation = false;
	private Future<Integer> future;
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		cancePerformStop();
		constellationStop();
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
