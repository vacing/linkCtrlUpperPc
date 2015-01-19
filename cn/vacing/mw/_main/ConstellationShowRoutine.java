package cn.vacing.mw._main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.vacing.mw.exception.DataLenUnproperException;
import cn.vacing.mw.perfomace_gui.ConstellationDisplay;
import cn.vacing.mw.threads.UdpDataConsumer;
import cn.vacing.mw.tools.Complex;
import cn.vacing.mw.tools.DataConvert;

public class ConstellationShowRoutine extends UdpDataConsumer {
	
	/**
	 * 星座图消费者类
	 * @param constellationDisplay 星座图显示的GUI组件
	 */
	public ConstellationShowRoutine(ConstellationDisplay constellationDisplay) {
		super(BAGS * CIRCUMSTANCES);	//初始化需要消费的包数
		this.constellationDisplay = constellationDisplay;
	}
	
	@Override
	public void dataConsumer(int length, byte[] data)
			throws DataLenUnproperException {
		/**
		 * 在接收第0号包（第一种情况的第1包）和BAGS号包（第二种情况的第1包）前重置临时存储
		 */
		if(currentBagNo % BAGS == 0) {
			complexList = new ArrayList<Complex>(CONSTELLATION_LEN * 2);	//留出1倍余量，防止数据过多
		}
		
//		//output data, simulate in matlab
//		PrintWriter out;
//		try {
//			out = new PrintWriter(new BufferedWriter(new FileWriter("constallation_byte.txt", true)));
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
		
		double[] doubleArr = DataConvert.byteArr2DoubleArr(data, length, BYTE_CNT, FRACTION_NUM);
		List<Complex> compTemp = Arrays.<Complex>asList(DataConvert.doubleArr2ComplexArr(doubleArr, doubleArr.length));
		complexList.addAll(compTemp);

		if(currentBagNo == bagsNeeded - 1) {
			if(complexList.size() >= CONSTELLATION_LEN) {
				complexList = DataConvert.trim2Size(complexList, CONSTELLATION_LEN);
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
				
				constellationDisplay.drawConstellation(ConstellationDisplay.BEFORE, dataShow);
			}
		}
		
		bagWaitedNext();
	}
	
	/**
	 * 等待的数据包的编号，如等待10个包，则变化过程为：0->1, 1->2, ..., 9->0，共10个包
	 */
	private synchronized void bagWaitedNext() {
		if(++currentBagNo > bagsNeeded - 1)
			currentBagNo = 0;	//收完需要的最后一个包之后，等待编号重新变为0
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

	private static final int BAGS = 5;		//每种曲线或点的包数
	private static final int CIRCUMSTANCES = 1;	//曲线条数或点的种数
	private static final int CONSTELLATION_LEN = 1024;		//每次更新星座图的数据长度
	//24Q17
	private static final int BYTE_CNT = 3;	//字节数， *8等于bit数
	private static final int FRACTION_NUM = 17;	//小数位数
	
	private ConstellationDisplay constellationDisplay;
	private volatile int currentBagNo;		//数据包编号, 0 - (bagsNeeded - 1)
	private ArrayList<Complex> complexList;		//数据临时存放
	private Complex[] complexArr = new Complex[CONSTELLATION_LEN];		//数据收集完成后，存放最终处理的源数据形式
	private static double[][] dataShow = new double[2][4*CONSTELLATION_LEN];	//滑动存放星座图显示数据。

}
