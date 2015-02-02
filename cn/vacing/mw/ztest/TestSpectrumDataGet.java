package cn.vacing.mw.ztest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
/**
 * 测试类，模拟底层发送的数据，并将数据按照底层格式进行返回。
 * 底层数据格式为：串行byte流，得到的是数组。数组中相邻2个byte（16位）按照大端格式组成一个int。
 * 			每次返回一个数组长度的数据，其中数组由调用者指定，剩余的数据不足时全部返回。函数返回值为本次在给定数组中填充的数据。
 * @author Gavin
 *
 */
public class TestSpectrumDataGet {
	private static final int INT_LEN = 2;
		private static final int FRAM_LEN = 1024;
		private static int count_before = 0;
		private static int count_after = 0;
		private static int i = 0;
		
		public static void main(String[] args) throws Exception {
			byte[] byteTemp = new byte[1470];
			
			for(;;) {
				int length = spectrumDataGet(byteTemp);
				if(length == 0) break;
				System.out.printf("%d: ", 0);
				for(int j = 0; j < length; j++) {
					System.out.printf("%X", byteTemp[j]);
					
					if((++i)%INT_LEN == 0) {
						System.out.printf("\n");
						System.out.printf("%d: ", i/INT_LEN);
					}
				}	
				if(length < 1470) 
					System.out.println("\n----------------------------------");
			}		
		}

		/**
		 * 按照底层格式返回数据
		 * @param packetBuffer：填充数据的数组
		 * @return：本次在数组中填充数据的数量
		 */
		public static int spectrumDataGet(byte[] packetBuffer) {
			byte[] byteTemp;
			try {	
				byteTemp = read("./testData/cancle_before_real.txt");
				if(count_before < byteTemp.length - 1) {	//上传抵消前的数据
					int i = 0;
					for(; count_before < byteTemp.length && i < packetBuffer.length; count_before ++) {
						packetBuffer[i++] = (byte) (byteTemp[count_before]);
					}
					return i;
				} else if(count_after < byteTemp.length) {	//上传抵消后的数据
					byteTemp = read("./testData/cancle_after_real.txt");
					int i = 0;
					for(; count_after < byteTemp.length && i < packetBuffer.length; count_after ++) {
						packetBuffer[i++] = (byte) (byteTemp[count_after]);
					}
					return i;
				} else {
					return 0;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return 0;
		}
		
		/**
		 * 读取文件中的数据，组陈字节数组
		 * @param fileName
		 * @return
		 * @throws Exception
		 */
		public static byte[] read(String fileName) throws Exception {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String s;
			byte[] byteArr = new byte[FRAM_LEN*INT_LEN];
			byte[] baTemp;
			for(int i = 0; (s = in.readLine()) != null; /*null*/) {
//				System.out.println(s);
				baTemp = str2byteArr(s);
				for(int j = 0; j < INT_LEN; j++)
					byteArr[i++] = baTemp[j];
			}
			
			in.close();
			return byteArr;
		}
		
		/**
		 * 字符串转换为16字节流，2个字符一个组成一个16进制字节
		 * @param s
		 * @return
		 */
		public static byte[] str2byteArr(String s) {
			if(s.length() > INT_LEN*2) {
				System.out.println("The length of the string is error!");
			}
			//将定长字符串转换为byte数组
			byte[] byteArr = new byte[INT_LEN];
			//两个字符变为一个16进制数
			for(int i = 0; i < INT_LEN*2; i+=2) {
				byteArr[i/2] = (byte)Integer.parseInt(s.substring(i, i+2), 16);
//				System.out.println(Byte.toString(byteArr[i/2]));
			}
			
			return byteArr;
		}
}
