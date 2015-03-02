package cn.vacing.mw.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.vacing.mw.exception.DataLenUnproperException;
import cn.vacing.mw.exception.NumInputOutOfBounds;

public class DataConvert {
	/**
	 * Convert a 32-bit int to byte array, big endian.
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] intToBytesArray(int num) {
		byte[] byteArr = new byte[4];
		// big endian, low to high
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[byteArr.length - 1 - i] = (byte) (num & 0xff);
			num >>= 8;
		}
		return byteArr;
	}

	/**
	 * Convert byte arr to int array (4 byte to 1 int, big endian). eg: 0x: 11,
	 * 22, 33, 44, 55, 66, 77, 88 -> 11223344, 55667788
	 * 
	 * @param b
	 *            : byte array
	 * @param length
	 *            : valid length of b
	 * @param bytesPerInt
	 *            : number of bytes per Int
	 * @return
	 * @throws DataLenUnproperException
	 */
	public static int[] byteArr2IntArr(byte[] b, int length, int bytesPerInt)
			throws DataLenUnproperException {
		if (length % bytesPerInt != 0) {
			throw new DataLenUnproperException();
		}

		int[] intArr = new int[length / bytesPerInt];
		int value = 0;
		for (int i = 0, j = 0; i < length; i++) {
			value <<= 8; // 低地址byte在高位，大端格式
			value |= (b[i] & 0xff); // 运算时会对b[i]进行自动转型，导致b[i]的符号位扩展

			if (i % bytesPerInt == bytesPerInt - 1) {
				// System.out.println("bin:" +
				// Integer.toBinaryString(Integer.MIN_VALUE >>
				// (4-bytesPerInt)*8));
				// System.out.println("bin:" +
				// Integer.toBinaryString(Integer.MIN_VALUE)); //符号位为1，其余为0
				// System.out.println("0x80 & (1<<7): " + (0x80 & (1<<7)));
				// //结果为0x80(128)，不为1
				if ((b[i + 1 - bytesPerInt] & (1 << 7)) != 0) { // 是一个负数，当不足32位时进行符号扩展(不能利用等于1判断，只有1不移位时与的结果才为1)
					if ((bytesPerInt * 8) < 32) // 移位的次数为指定次数对32取模，因此若为32时，实际上不移位，不符合需要
						value |= Integer.MIN_VALUE >> (4 - bytesPerInt) * 8; // 将int剩余位进行符号扩展，
				}
				intArr[j++] = value;
				value = 0;
			}
		}
		return intArr;

	}

	/**
	 * convert byte array to float array
	 * 
	 * @param b
	 *            : byte array
	 * @param numPerDouble
	 *            : number of bytes per Double
	 * @param fractionNum
	 *            : the bit's number of fraction
	 * @param length
	 *            : valid length of b
	 * @return
	 * @throws DataLenUnproperException
	 */
	public static double[] byteArr2DoubleArr(byte[] b, int length, int bytesPerDouble, double fractionNum)
			throws DataLenUnproperException {
		
		int[] intArr = byteArr2IntArr(b, length, bytesPerDouble);
		if (null == intArr) {
			return null;
		}
		double[] floatArr = new double[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			floatArr[i] = intArr[i] / Math.pow(2, fractionNum);
		}

		return floatArr;
	}

	/**
	 * covert double array to Complex Array
	 * 
	 * @param d
	 *            : double array
	 * @param length
	 *            : valid length of double array
	 * @return
	 * @throws DataLenUnproperException 
	 */
	public static Complex[] doubleArr2ComplexArr(double[] d, int length) throws DataLenUnproperException {
		if (length % 2 != 0) {
			throw new DataLenUnproperException();
		}

		Complex[] complexArr = new Complex[length / 2];
		Complex com;
		for (int i = 0; i + 1 < length; i += 2) {
			com = new Complex(d[i], d[i + 1]);
			complexArr[i / 2] = com;
		}

		return complexArr;
	}
	
	/**
	 * covert double array to Complex array, 
	 * given data as real part, and imagine part is 0.
	 * 
	 * @param d: double array
	 * @return	parsed complex data
	 * @throws DataLenUnproperException 
	 */
	public static Complex[] double2Complex(double[] d) {
		Complex[] complexArr = new Complex[d.length];
		Complex com;
		for (int i = 0; i < d.length; i++) {
			com = new Complex(d[i], 0);
			complexArr[i] = com;
		}
		return complexArr;
	}
	
	/**
	 * covert a double data  to Complex data, 
	 * given data is real part, and imagine part is 0.
	 * 
	 * @param d: double array
	 * @return	parsed complex data
	 * @throws DataLenUnproperException 
	 */
	public static Complex double2Complex(double d) {
		return new Complex(d, 0);
	}

	/**
	 * convert complex array to power array, 以db为单位, 第一个一维数组表示横坐标，第二个一维数组表示纵坐标
	 * 
	 * @param c
	 *            : complex array
	 * @return
	 * @throws NumInputOutOfBounds 
	 */
	public static double[][] complexArr2PowerDBArr(Complex[] c) throws NumInputOutOfBounds {
		double[][] doubleArr = new double[2][c.length];
		
		Complex nonZero = new Complex(0, 0);
		for(int i = 0; i < c.length; i++) {
			if(c[i].abs() > Double.MIN_VALUE) {
				nonZero = c[i];
				break;
			}
		}
		if(nonZero.abs() < Double.MIN_VALUE) {	//输入全0，无法求dbm
			throw new NumInputOutOfBounds("Input data are all zero, can't get their db value!!");
		}

		double dbDefault = 20 * Math.log10(nonZero.abs());	
		for (int i = 0; i < c.length; i++) {
			doubleArr[0][i] = i;
			doubleArr[1][i] = 20 * Math.log10(c[i].abs());	
			if(Double.isInfinite(doubleArr[1][i])) {	//可能c[i].abs()=0，造成功率的对数为负无穷
				if(i > 0)
					doubleArr[1][i] = doubleArr[1][i-1];
				else
					doubleArr[1][0] = dbDefault;
			}
		}
		return doubleArr;
	}

	/**
	 * convert complex array to power array，直接以功率为单位，不做db的转换， 第一个一维数组表示横坐标，第二个一维数组表示纵坐标
	 * 
	 * @param c
	 *            : complex array
	 * @return
	 */
	public static double[][] complexArr2PowerArr(Complex[] c) {
		double[][] doubleArr = new double[2][c.length];
		Complex mean = getMean(c);
//		System.out.println(mean);
		for (int i = 0; i < c.length; i++) {
			doubleArr[0][i] = i;
//			System.out.println("c[i]" + c[i]);
//			System.out.println("c[i].minus(mean)" + c[i].minus(mean));
			doubleArr[1][i] = Math.pow((c[i].minus(mean)).abs(), 2);	//minus the direct bias	
		}
		return doubleArr;
	}
	
	
	/**
	 * 使用滑动窗进行平滑,长度为10
	 */
	public static void smoothLine(double[] dArr, int window) {
		double[] dTemp = new double[dArr.length + window - 1];

		for (int i = 0; i < dTemp.length; i++) {
			if (i < window) {
				dTemp[i] = dArr[0];
				continue;
			}
			dTemp[i] = dArr[i - window + 1];
		}

		double temp = dArr[0] * window;
		for (int i = 0; i < dArr.length; i++) {
			temp = temp - dTemp[i] + dTemp[i + window - 1]; // 滑动窗
			dArr[i] = temp / window;
		}
	}
	
	/**
	 * 数据长度截取为指定长度
	 */
	public static ArrayList<Complex> trim2Size(ArrayList<Complex> lc, int size) {
		ArrayList<Complex> listTemp = new ArrayList<Complex>(size);
		
		for(Iterator<Complex> it = lc.iterator(); it.hasNext() && listTemp.size() < size; /*null*/) {
			listTemp.add(it.next());
		}
//		System.out.println("listTemp.size: " + listTemp.size());
		return listTemp;
	}

	public static void main(String[] args) {

		byte[] ba = intToBytesArray(0x44992211);
		for (byte b : ba) {
			System.out.format("%x \n", b);// 0x:44, 33, 22, 11
		}
		ba = new byte[] { -0x22, 0x11, 0x44, 0x33, 0x22, 0x11 };
		int[] ia;
		try {
			ia = byteArr2IntArr(ba, ba.length, 3);

			for (int i : ia)
				System.out.format("%x \n", i); // 0x:665544, 332211
	
			// double[] da = byteArr2DoubleArr(ba, ba.length, 4, 2);
			// for (double i : da)
			// System.out.format("%f \n", i); //0x:665544, 332211
		
		} catch (DataLenUnproperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取数组的平均值
	 * @param dArr
	 * @return
	 */
	public static double getMean(double[] dArr) {
		double mean = 0;
		for(int i = 0; i < dArr.length; i++) {
			mean += dArr[i];
		}
		return mean / dArr.length;
	}
	public static Complex getMean(Complex[] dArr) {
		Complex mean = new Complex(0, 0);
		for(int i = 0; i < dArr.length; i++) {
			mean = mean.plus(dArr[i]);
		}
//		System.out.println("mean1: " + mean);
		return mean.divC(dArr.length);
	}
	
	/**
	 * 获取给定值指定的dbm数
	 * @param value
	 * @return
	 */
	public static double getDBValue(double value) {
		return 10 * Math.log10(value);
	}
}
