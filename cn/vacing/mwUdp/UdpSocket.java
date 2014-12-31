package cn.vacing.mwUdp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * udp soket类。
 * 这是udp socket的一个正式工程，可以实现udp的发送和接收的功能。
 * @author Gavin
 */
public class UdpSocket
{
	private DatagramSocket 	ds;
	private DatagramPacket 	dpSend;
	private DatagramPacket 	dpRece;
	private String 			ipAddressLocal = "127.0.0.1";	//default
	private int 			portLocal = 6001;				//default
	private Boolean isUdpSockOpened;
	
	/**
	 * 初始化udp socket
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public UdpSocket(String ipAddressLocal, int portLocal) throws SocketException, UnknownHostException
	{
		this.ipAddressLocal = ipAddressLocal;
		this.portLocal = portLocal;
//		InetAddress ia = InetAddress.getLocalHost();
		ds = new DatagramSocket(portLocal, InetAddress.getByName(ipAddressLocal));
//		System.out.println(ia.getHostAddress());
		isUdpSockOpened = new Boolean(true);
		ds.setSoTimeout(200);	//receive time out
//		ds.connect(new InetSocketAddress(this.ipAddressDest, this.portDest));
		System.out.println("Udp 启动成功!");
	}
	
	/**
	 * 设置本地端口和ip
	 */
	public void setIpPort(String ip, int port) {
		this.ipAddressLocal = ip;
		this.portLocal = port;
	}
	
	/**
	 * 获取udp socket是否建立
	 * @return
	 */
	public Boolean isUdpSockOpened()
	{
		return isUdpSockOpened;
	}
	
	/**
	 * 发送UDP数据
	 * @param ipAddressDest
	 * @param portDest
	 * @param mesg
	 */
	public void sendUdpMesg(String ipAddressDest, int portDest, byte[] mesg)
	{
		if(! isUdpSockOpened)
		{
			System.out.println("UDP 尚未建立连接。");
			return;
		}
//		System.out.println(ipAddressDest);
		synchronized(ds) {
			try
			{
				dpSend = new DatagramPacket(mesg, mesg.length, 
						InetAddress.getByName(ipAddressDest), portDest);
				ds.send(dpSend);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 接收udp数据包
	 * @param inBuffer
	 * @return
	 */
	public int receUdpMesg(byte[] inBuffer)
	{
		if(! isUdpSockOpened)
		{
			System.out.println("UDP 尚未建立连接。");
			return -1;
		}
		
		synchronized(ds)
		{
			try
			{
				dpRece = new DatagramPacket(inBuffer, inBuffer.length);
				ds.receive(dpRece);
			}
			catch (SocketTimeoutException e) {
				System.out.println("UDP socket receive is time out!!");
				return 0;
			}
			catch(Exception e)
			{
	//			e.printStackTrace();
			}
		}
		
		return dpRece.getLength();
	}
	
	/**
	 * 关闭创建的UDP链接。
	 * 首先检测链接是否创建，如果未创建，则提示错误；否则，关闭创建的链接。
	 */
	public void closeUdp()
	{
		if(!isUdpSockOpened)
			return;
		
		isUdpSockOpened = false;
		ds.close();
		System.out.println("Udp 关闭成功！");
	}
}
