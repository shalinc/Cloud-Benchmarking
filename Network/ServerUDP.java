/*
 * Implementation of Client - Server Communication 
 * using UDP (Connection-less)
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class ServerUDP implements Runnable
{

	public static DatagramSocket serverSock = null;
	//public static DatagramPacket dPkt = null;
	public static int numOfThreads;
	public static int threadID;
	
	public static void main(String[] args) throws SocketException, InterruptedException 
	{
		// TODO Auto-generated method stub
		numOfThreads = Integer.parseInt(args[0]);
		serverSock = new DatagramSocket(5004);
		System.out.println("Server is started");
		
		for(int i = 1; i<= numOfThreads; i++)
		{
			ServerUDP serverUDP = new ServerUDP();
			serverUDP.threadID = i;
			new Thread(serverUDP).start();
			Thread.sleep(100);
		}
		
	}
	
	@Override
	public void run()
	{
		try
		{
			int [] blockSizes = {1,1024,65505};
			for(int blk =0; blk <blockSizes.length; blk++)
			{
				//InetAddress iNAddress = InetAddress.getByName("52.36.149.179");
				byte [] recvData = new byte[blockSizes[blk]];
			
				DatagramPacket dPkt = new DatagramPacket(recvData, recvData.length);
				serverSock.receive(dPkt);
			
				//			iNAddress = dPkt.getAddress();
			
				recvData = dPkt.getData();
				dPkt = new DatagramPacket(recvData, recvData.length, dPkt.getAddress(), dPkt.getPort());
				serverSock.send(dPkt);
				System.out.println("Packet Sent from Server Side");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
