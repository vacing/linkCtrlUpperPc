package cn.vacing.mw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.vacing.mw.exception.DataLenUnproperException;
import cn.vacing.mw.perfomace_gui.ConstellationDisplay;
import cn.vacing.mw.perfomace_gui.SpectrumDisplay;
import cn.vacing.mw.threads.UdpDataConsumer;
import cn.vacing.mw.tools.Complex;
import cn.vacing.mw.tools.DataConvert;
import cn.vacing.mw.tools.FFT;

public class ConstellationShowRoutine  implements UdpDataConsumer {
	private ConstellationDisplay constellationDisplay;
	private volatile int receivedBagsCount;
	private ArrayList<Complex> complexList;
	private Complex[] complexArr = new Complex[COMSTELLATION_LEN];
	private static final int COMSTELLATION_LEN = 1024;		//每次更新星座图的数据长度
	private static double[][] dataShow = new double[2][4*COMSTELLATION_LEN];	//滑动存放星座图显示数据。
	private static final int BAGS = 5;
	private static final int CIRCUMSTANCES = 1;
	private final int bagsNeeded = BAGS * CIRCUMSTANCES;	//本消费者需要的包数
	
	public ConstellationShowRoutine(ConstellationDisplay constellationDisplay) {
		this.constellationDisplay = constellationDisplay;
	}
	
	@Override
	public void dataConsumer(int length, byte[] data)
			throws DataLenUnproperException {
		double[] doubleArr = DataConvert.byteArr2DoubleArr(data, length, 3, 17);	//24Q17
		List<Complex> compTemp = Arrays.<Complex>asList(DataConvert.doubleArr2ComplexArr(doubleArr, doubleArr.length));
		complexList.addAll(compTemp);

		if(receivedBagsCount == 4) {
			if(complexList.size() >= COMSTELLATION_LEN) {
				complexList = DataConvert.trim2Size(complexList, COMSTELLATION_LEN);
				complexList.toArray(complexArr);
				double[][] showTemp = complexConvert(complexArr);
				append2DataShow(showTemp);
				
//					//output data, simulate in matlab
//					PrintWriter outC;
//					outC = new PrintWriter(new BufferedWriter(new FileWriter("power_before.txt")));
//					for(double d: power[1]) {
//						outC.println(d);
//					}	
//					outC.close();
				
				constellationDisplay.drawConstellation(constellationDisplay.BEFORE, dataShow);
			}
		}

		
	}
	@Override
	public int getBagsNum() {
		return bagsNeeded;
	}
	
	/**
	 * 计数已经收到的数据的包数
	 */
	private synchronized void receivedBagsAdd() {
		if(receivedBagsCount++ >= bagsNeeded)
			receivedBagsCount = 0;
	}
	
	/**
	 * 复数转换为浮点数数组
	 * @param compArr
	 * @return
	 */
	private synchronized double[][] complexConvert(Complex[] compArr) {
		double[][] doubleTemp = new double[2][compArr.length];
		
		for(int i = 0; i < compArr.length; i++) {
			doubleTemp[0][i] = compArr[i].re();
			doubleTemp[1][i] = compArr[i].im();
		}
		
		return doubleTemp;
		
	}
	
	/**
	 * 滑动窗显示星座图数据，避免星座图变动过大
	 * @param data
	 */
	private synchronized void append2DataShow(double[][] data) {
		int oldEnd = dataShow[0].length - data[0].length;	//有效旧数据的结尾
		
		//旧数据后移，从后向前搬移，避免覆盖有效数据
		for(int i = 0; i < oldEnd; i++) {
			dataShow[0][dataShow[0].length - i - 1] = dataShow[0][oldEnd - i - 1];
			dataShow[1][dataShow[0].length - i - 1] = dataShow[1][oldEnd - i - 1];
		}
		
		//新添加数据放在前面
		for(int i = 0; i < data[0].length; i++) {
			dataShow[0][i] = data[0][i];
			dataShow[1][i] = data[1][i];
		}
	}
	/**
	 * 测试滑动窗口函数
	 */
//	public static void main(String[] args){
//		int times = 0;
//		int data = 0;
//		while (times < 8) {
//			times ++;
//			double[][] temp = new double[2][COMSTELLATION_LEN];
//			for(int i = 0; i < temp[0].length; i++, data++) {
//				temp[0][i] = data;
//				temp[1][i] = data;
//			}
//			append2DataShow(temp);
//			
//			System.out.println();
//			for(int i = 0; i < dataShow[0].length; i++) {
//				System.out.print(dataShow[0][i] + " ");
//			}
//			System.out.println();
//			for(int i = 0; i < dataShow[0].length; i++) {
//				System.out.print(dataShow[1][i] + " ");
//			}
//			System.out.println("\n--------------------------------------------");
//		}
//		
//	}
}
