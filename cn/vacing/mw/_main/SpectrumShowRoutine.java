package cn.vacing.mw._main;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import cn.vacing.mw.exception.DataLenUnproperException;
import cn.vacing.mw.exception.NumInputOutOfBounds;
import cn.vacing.mw.perfomace_gui.SpectrumDisplay;
import cn.vacing.mw.threads.UdpDataConsumer;
import cn.vacing.mw.tools.Complex;
import cn.vacing.mw.tools.DataConvert;
import cn.vacing.mw.tools.FFT;

/**
 * 频谱显示事务处理。
 * @author Gavin
 *
 */
public class SpectrumShowRoutine extends UdpDataConsumer {
	
	public SpectrumShowRoutine(SpectrumDisplay spectrumDisplay) {
		super(BAGS * CIRCUMSTANCES);
		this.spectrumDisplay = spectrumDisplay;
	}
	
	@Override
	public synchronized void dataConsumer(int length, byte[] data) throws DataLenUnproperException{
		/**
		 * 在接收第0包（第一种情况的第1包）和BAGS包（第二种情况的第1包）前重置临时存储
		 */
		if(currentBagNo % BAGS == 0) {
			complexList = new ArrayList<Complex>(SPECTRUM_LEN * 2);	//留出1倍余量，防止数据过多
		}
		
//		//output data, simulate in matlab
//		PrintWriter out;
//		try {
//			out = new PrintWriter(new BufferedWriter(new FileWriter("spectrum_byte.txt", true)));
//			for(int i = 0; i < length; i++) {
//				out.printf("%x", data[i]);
//				if(i % BYTE_CNT == BYTE_CNT-1) {
//					out.println();
//				}
//			}	
//			out.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		switch(currentBagNo / BAGS) {
			case 0:	{//抵消前
				double[] tempDouble = DataConvert.byteArr2DoubleArr(data, length, BYTE_CNT, FRACTION_NUM);
				List<Complex> tempComplex = Arrays.<Complex>asList(DataConvert.double2Complex(tempDouble));
				complexList.addAll(tempComplex);

				if(currentBagNo == BAGS - 1) {//收集了足够的包数的数据
					if(complexList.size() >= SPECTRUM_LEN) {
						complexList = DataConvert.trim2Size(complexList, SPECTRUM_LEN);
						double[][] power = DataConvert.complexArr2PowerArr(complexList.toArray(complexArr));
						double powerDBm = DataConvert.getDBValue(DataConvert.getMean(power[1]));
						spectrumDisplay.showAvgPowerBefore(powerDBm);
						Complex[] fftResult = FFT.fft(complexList.toArray(complexArr));
						arrMirror(fftResult);	// 将频谱的(pi -> 2*pi)的部分折叠到(-pi -> 0)
						double[][] powerDBmLine;
						try {
							powerDBmLine = DataConvert.complexArr2PowerDBArr(fftResult);
							DataConvert.smoothLine(powerDBmLine[1], 5);//曲线滑动平滑
							spectrumDisplay.drawSpectrum(SpectrumDisplay.BEFORE, powerDBmLine);
						} catch (NumInputOutOfBounds e) {
							// TODO Auto-generated catch block
							JOptionPane.showConfirmDialog(null, e.getMessage());
						}
					}
				}
				break;
			}
			case 1:	{//抵消后
				double[] tempDouble = DataConvert.byteArr2DoubleArr(data, length, BYTE_CNT, FRACTION_NUM);	
				List<Complex> tempComplex = Arrays.<Complex>asList(DataConvert.double2Complex(tempDouble));
				complexList.addAll(tempComplex);
//				System.out.println("complexList: " + complexList.size());
				if(currentBagNo == 2 * BAGS - 1) {//收集了足够的包数的数据
					if(complexList.size() >= SPECTRUM_LEN) {	
						complexList = DataConvert.trim2Size(complexList, SPECTRUM_LEN);
						double[][] power = DataConvert.complexArr2PowerArr(complexList.toArray(complexArr));
						double powerDBm = DataConvert.getDBValue(DataConvert.getMean(power[1]));
						spectrumDisplay.showAvgPowerAfter(powerDBm);
						Complex[] fftResult = FFT.fft(complexList.toArray(complexArr));
						arrMirror(fftResult);	// 将频谱的(pi -> 2*pi)的部分折叠到(-pi -> 0)
						double[][] powerDBmLine;
						try {
							powerDBmLine = DataConvert.complexArr2PowerDBArr(fftResult);
							DataConvert.smoothLine(powerDBmLine[1], 5);//曲线滑动平滑
							spectrumDisplay.drawSpectrum(SpectrumDisplay.AFTER, powerDBmLine);
						} catch (NumInputOutOfBounds e) {
							// TODO Auto-generated catch block
							JOptionPane.showConfirmDialog(null, e.getMessage());
						}
					}
				}
				break;
			}
			default:
				System.out.println(this.getClass().getName() + " Error");
		}
		
		/**
		 * 根据上面的case语句，包的编号要在0-9之间，而不能是1-10（->0）之间
		 */
		bagWaitedNext();
	}
	
	/**
	 * 将频谱的(pi -> 2*pi)的部分折叠到(-pi -> 0)
	 * @param oArr
	 */
	private void arrMirror(Object[] oArr) {
		Object[] oTemp = oArr.clone();
		int center = oArr.length / 2;
		
		for(int i = 0; i < oArr.length; i++) {
			if(i < center) {
				oArr[i] = oTemp[center + i];
				continue;
			}
			oArr[i] = oTemp[i - center];
		}
	}
	
	/**
	 * 等待的数据包的编号，如等待10个包，则变化过程为：0->1, 1->2, ..., 9->0，共10个包
	 */
	private synchronized void bagWaitedNext() {
		if(++currentBagNo > bagsNeeded - 1)	
			currentBagNo = 0;	//收完需要的最后一个包之后，等待编号重新变为0
//		System.out.println(currentBagNo);
	}
	
	private static final int SPECTRUM_LEN = 1024;
	private static final int BAGS = 2;	//每种曲线或点的包数
	private static final int CIRCUMSTANCES = 2;	//曲线条数或点的种数
	//16Q12
	private static final int BYTE_CNT = 2;	//字节数， *8等于bit数
	private static final int FRACTION_NUM = 12;	//小数位数
	
	private SpectrumDisplay spectrumDisplay;
	private volatile int currentBagNo = 0;	//收到的数据包的编号
	private ArrayList<Complex> complexList;	//临时保存接收到的数据
	private Complex[] complexArr = new Complex[SPECTRUM_LEN];	//数据接收完毕后，存储收到的数据转换而成复数
}	
