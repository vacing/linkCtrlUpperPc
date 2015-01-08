package cn.vacing.mw.udp;

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
	private volatile Boolean isUdpSockOpened;
	
	/**
	 * 初始化udp socket
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public UdpSocket(String ipAddressLocal, int portLocal) throws SocketException, UnknownHostException
	{
		ds = new DatagramSocket(portLocal, InetAddress.getByName(ipAddressLocal));
		isUdpSockOpened = new Boolean(true);
		ds.setSoTimeout(20);	//receive time out
		System.out.println("Udp 启动成功!");
	}
	
	/**
	 * 获取udp socket是否建立标识。
	 * @return
	 */
	public Boolean isUdpSockOpened()
	{
		synchronized(isUdpSockOpened) {
			return isUdpSockOpened;
		}
	}
	
	/**
	 * 发送UDP数据
	 * @param ipAddressDest
	 * @param portDest
	 * @param mesg
	 */
	public void sendUdpMesg(String ipAddressDest, int portDest, byte[] mesg)
	{
		synchronized(isUdpSockOpened) {
			if(! isUdpSockOpened)
			{
				System.out.println("UDP 尚未建立连接。");
				return;
			}
		}
		
//		System.out.println(ipAddressDest);
		try
		{
			DatagramPacket dpSend = new DatagramPacket(mesg, mesg.length, 
					InetAddress.getByName(ipAddressDest), portDest);
			synchronized(ds) {
				ds.send(dpSend);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收udp数据包
	 * @param inBuffer
	 * @return
	 */
	public int receUdpMesg(byte[] inBuffer)
	{		
		synchronized (isUdpSockOpened) {
			if(! isUdpSockOpened)
			{
				System.out.println("UDP 尚未建立连接。");
				return -1;
			}
		}

		try
		{
			DatagramPacket dpRece = new DatagramPacket(inBuffer, inBuffer.length);
			synchronized(ds) {
				ds.receive(dpRece);
			}
			return dpRece.getLength();
		}
		catch (SocketTimeoutException e) {
			System.out.println("UDP socket receive is time out!!");
			return -1;
		}
		catch(Exception e)
		{
	//			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 关闭创建的UDP链接。
	 * 首先检测链接是否创建，如果未创建，则提示错误；否则，关闭创建的链接。
	 */
	public synchronized void closeUdp()
	{
		if(!isUdpSockOpened)
			return;
		
		isUdpSockOpened = false;
		ds.close();
		System.out.println("Udp 关闭成功！");
	}
}
