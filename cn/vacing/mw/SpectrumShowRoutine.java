package cn.vacing.mw;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cn.vacing.mwEvents.DataConvert;
import cn.vacing.mwThreads.UdpDataConsumer;
import cn.vacing.perfomaceGui.Complex;
import cn.vacing.perfomaceGui.FFT;
import cn.vacing.perfomaceGui.SpectrumDisplay;

/**
 * 频谱显示事务处理。
 * @author Gavin
 *
 */
public class SpectrumShowRoutine implements UdpDataConsumer {
	private SpectrumDisplay spectrumDisplay;
	private int spectrumShowTimes = 0;
	private ArrayList<Complex> complexList;
	private Complex[] complexArr = new Complex[SPECTRUM_LEN];
	private static final int SPECTRUM_LEN = 1024;
	private static final int BAGS = 5;
	private static final int CIRCUMSTANCES = 2;
	
	private int bagsNeeded;
	public SpectrumShowRoutine(SpectrumDisplay spectrumDisplay) {
		this.bagsNeeded = BAGS * CIRCUMSTANCES;
		this.spectrumDisplay = spectrumDisplay;
	}
	
//	public SpectrumShowRoutine() {
//		this.bagsNeeded = BAGS;
//	}
	
	private int byteCount  = 0;
	@Override
	public void dataConsumer(int length, byte[] data) {
		if(spectrumShowTimes % BAGS == 0) {
			complexList = new ArrayList<Complex>(SPECTRUM_LEN * 2);
		}
		
		//output data, simulate in matlab
//		PrintWriter out;
//		try {
//			out = new PrintWriter(new BufferedWriter(new FileWriter("byte.txt", true)));
//			for(int i = 0; i < length; i++) {
//				out.printf("%x", data[i]);
//				if(i % 3 == 2) {
//					out.println();
//				}
//			}	
//			out.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		switch(spectrumShowTimes / BAGS) {
			case 0:	//抵消前
			{
				try {						
					double[] tempDouble = DataConvert.byteArr2DoubleArr(data, length, 3, 21);	//24Q21
					List<Complex> tempComplex = Arrays.<Complex>asList(DataConvert.doubleArr2ComplexArr(tempDouble, tempDouble.length));
					complexList.addAll(tempComplex);

					if(spectrumShowTimes == BAGS - 1) {
						if(complexList.size() >= SPECTRUM_LEN) {
							complexList = trim2Size(complexList, SPECTRUM_LEN);
							Complex[] fftResult = FFT.fft(complexList.toArray(complexArr));
							arrMirror(fftResult);	// 将频谱的(pi -> 2*pi)的部分折叠到(-pi -> 0)
							double[][] power = DataConvert.complexArr2PowerArr(fftResult);
							
//							//output data, simulate in matlab
//							PrintWriter outC = new PrintWriter(new BufferedWriter(new FileWriter("power_before.txt")));
//							for(double d: power[1]) {
//								outC.println(d);
//							}	
//							outC.close();
							
							spectrumDisplay.showAvgPowerBefore(getMean(power[1]));
							DataConvert.smoothLine(power[1], 5);//曲线滑动平滑
							spectrumDisplay.drawSpectrum(SpectrumDisplay.BEFORE, power);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case 1:	//抵消后
			{
				try {
					double[] tempDouble = DataConvert.byteArr2DoubleArr(data, length, 3, 21);	//24Q21
					List<Complex> tempComplex = Arrays.<Complex>asList(DataConvert.doubleArr2ComplexArr(tempDouble, tempDouble.length));
					complexList.addAll(tempComplex);
	//				System.out.println("complexList: " + complexList.size());
					if(spectrumShowTimes == 2 * BAGS - 1) {
						complexList = trim2Size(complexList, SPECTRUM_LEN);
						if(complexList.size() == SPECTRUM_LEN) {
							Complex[] fftResult = FFT.fft(complexList.toArray(complexArr));
							arrMirror(fftResult);	// 将频谱的(pi -> 2*pi)的部分折叠到(-pi -> 0)
							double[][] power = DataConvert.complexArr2PowerArr(fftResult);
							
							
//							//output data, simulate in matlab
//							PrintWriter outC = new PrintWriter(new BufferedWriter(new FileWriter("power_after.txt")));
//							for(double d: power[1]) {
//								outC.println(d);
//							}	
//							outC.close();
							
							spectrumDisplay.showAvgPowerAfter(getMean(power[1]));
							DataConvert.smoothLine(power[1], 5);//曲线滑动平滑
							spectrumDisplay.drawSpectrum(SpectrumDisplay.AFTER, power);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			default:
				System.out.println(this.getClass().getName() + " Error");
		}
		
		spectrumShowTimesAdd();
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
	 * 获取平均功率
	 */
	private double getMean(double[] dArr) {
		double mean = 0;
		for(int i = 0; i < dArr.length; i++) {
			mean += dArr[i];
		}
		
		return mean / dArr.length;
	}
	
	/**
	 * 数据长度截取为1024
	 */
	private ArrayList<Complex> trim2Size(ArrayList<Complex> lc, int size) {
		ArrayList<Complex> listTemp = new ArrayList<Complex>(size);
		
		for(Iterator<Complex> it = lc.iterator(); it.hasNext() && listTemp.size() < size; /*null*/) {
			listTemp.add(it.next());
		}
//		System.out.println("listTemp.size: " + listTemp.size());
		return listTemp;
	}
	
	/**
	 * 返回需要消费的包数
	 */
	public int getBagsNum() {
		return bagsNeeded;
	}
	
	private void spectrumShowTimesAdd() {
		if(spectrumShowTimes++ >= BAGS * CIRCUMSTANCES)
			spectrumShowTimes = 0;
	}
}	
