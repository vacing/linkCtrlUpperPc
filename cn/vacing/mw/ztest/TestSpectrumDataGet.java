package cn.vacing.mw.ztest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;


/**
 * 测试类，模拟底层发送的数据，并将数据按照底层格式进行返回。
 * 底层数据格式为：串行byte流，得到的是数组。数组中相邻3个byte（24位）按照大端格式组成一个int，相邻两个int组成一个负数，其中实部在前。
 * 			每次返回一个数组长度的数据，其中数组由调用者指定，剩余的数据不足时全部返回。函数返回值为本次在给定数组中填充的数据。
 * @author Gavin
 *
 */
public class TestSpectrumDataGet {
	private static int count = 0;
	private static int noise = 0;
	private static int times = 0;
	
	public static void main(String[] args) throws Exception {
		byte[] byteTemp = read("./d_jhh_1_com_N.txt");
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("./out.txt"));
		PrintStream old = System.out;
		System.setOut(new PrintStream(bos));
		int i = 0;
		for(byte b: byteTemp) {
			System.out.printf("%X", b);
			
			if((i++)%3 == 2) {
				System.out.printf("\n");
			}
		}
		
		System.setOut(old);
		bos.close();
		
	}

	/**
	 * 按照底层格式返回数据
	 * @param packetBuffer：填充数据的数组
	 * @return：本次在数组中填充数据的数量
	 */
	public static int spectrumDataGet(byte[] packetBuffer) {
		byte[] byteTemp;
		try {	
			byteTemp = read("./d_jhh_1_com_N.txt");
			int i = 0;
			for(; count < byteTemp.length && i < packetBuffer.length; count ++) {
				packetBuffer[i++] = (byte) (byteTemp[count] + noise + new Random().nextInt(5));
			}
			if(count == byteTemp.length) {
				count = 0;
				if(times++  % 2 == 0)
					noise = 90;
				else
					noise = 0;
			}
			return i;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static byte[] read(String fileName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		byte[] byteArr = new byte[2048*3];
		byte[] baTemp;
		for(int i = 0; (s = in.readLine()) != null; /*null*/) {
			baTemp = str2byteArr(s);
			byteArr[i++] = baTemp[0];
			byteArr[i++] = baTemp[1];
			byteArr[i++] = baTemp[2];
		}
		
		in.close();
		return byteArr;
	}
	
	public static byte[] str2byteArr(String s) {
		if(s.length() > 6) {
			System.out.println("The length of the string is error!");
		}
		//将不定长度的字符串转换为定长为6的字符串
		char[] charZero= new char[6];
		for(int i = 0; i < 6; i++) {
			charZero[i] = '0';
		}
		char[] sCharArr = s.toCharArray();
		for(int i = 6 - s.length(), j = 0; i < 6; i++, j++) {
			charZero[i] = s.charAt(j);
		}
		
		//将定长字符串转换为byte数组
		s = String.valueOf(charZero);
//		System.out.println(s);
		byte[] byteArr = new byte[3];
		for(int i = 0; i < 6; i++, i++) {
			byteArr[i/2] = (byte)Integer.parseInt(s.substring(i, i+2), 16);
//			System.out.println(Byte.toString(byteArr[i/2]));
		}
		
		return byteArr;
	}
}
