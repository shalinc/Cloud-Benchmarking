import java.io.*;
import java.net.*;

public class ClientUDP implements Runnable
{
	public static DatagramSocket clientSock;
	
	public static int numOfThreads;
	public static int threadID;
	
	public static FileInputStream fis;
	public static File file = null;
	
	public static double throughput;

	double latency;
	public static long timeNote, time_note_temp;
	
	public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException 
	{
		// TODO Auto-generated method stub
		numOfThreads = Integer.parseInt(args[0]);
		clientSock = new DatagramSocket();
		
		//do for num. of threads time
		for (int i=1;i<=numOfThreads;i++)
		{
			ClientUDP clientUDP = new ClientUDP();
			clientUDP.threadID = i;
			new Thread(clientUDP).start();
			Thread.sleep(100);
		}
	}

	@Override
	public void run()
	{
		try
		{
			int [] blockSizes = {1,1024,65505};
			for(int blk = 0; blk< blockSizes.length;blk++)
			{
				//get the INET Address for sending packet to server
				InetAddress iNAddress = InetAddress.getByName("52.26.161.78");	//Change IP Required here !!!
				file = new File("A.txt");
			
				byte [] sendData = new byte[blockSizes[blk]];
				byte [] recvData = new byte[blockSizes[blk]];
			
				//read the data from a file to send to server
				FileInputStream fis = new FileInputStream(file);
				fis.read(sendData);
			
				double start_time = System.nanoTime();
			
				//create a datagram packet to be sent onto the Server side
				DatagramPacket dPkt = new DatagramPacket(sendData, sendData.length, iNAddress, 5004);
				clientSock.send(dPkt);
				//			System.out.println("Packet Sent to Server");
		
				//receive the data back into a packet on client sidde
				dPkt = new DatagramPacket(recvData, recvData.length);
				//				System.out.println("Client "+sendData.length);	
				clientSock.receive(dPkt);
				//			System.out.println("Packet received from Server");
				
				//receive from server
			
				double end_time = System.nanoTime();
				double totalElapsedTime = (end_time-start_time)/(1000000000);	//nano to seconds
			
				//calculate the throughput and latency
				throughput = 2*((blockSizes[blk]/totalElapsedTime)/(1024*1024));
				latency = (totalElapsedTime*1000)/2;
			
				System.out.println("Thread "+ClientUDP.threadID+": Throughput for "+blockSizes[blk]+" Byte(s) is "+throughput+" MBPS");
				System.out.println("Latency is "+latency+" ms");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
