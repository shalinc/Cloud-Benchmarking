/*
 * Server Side Benchmarking 
 */
import java.io.*;
import java.net.*;

public class ServerBM implements Runnable 
{
	private static ServerSocket serverSock;
	private Socket clientSock;
	public static int bufLen;

	public static void main(String[] args) 
	{
		try 
		{
			System.out.println("Server is Starting");
			//creating the serverSocket on port number
			serverSock = new ServerSocket(5004);
			
			int numOfThreads = Integer.parseInt(args[0]);
			//bufLen = Integer.parseInt(args[1]);
			
			//accept the connection from the requesting client

			//Create a client Connection socket
			Socket clientConn = serverSock.accept();
			System.out.println("Client Connected");

			//create Thread for accepted connection
			for(int i = 1; i<=numOfThreads; i++)
			{
				ServerBM serverBM = new ServerBM();
				serverBM.clientSock=clientConn;
				new Thread(serverBM).start();
				Thread.sleep(1000);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		//System.out.println("Connection Established Successfully and Thread created");
		try
		{			
			int [] blockSizes = {1,1024,65505};
			for(int blk = 0; blk < blockSizes.length; blk++)
			{
				DataInputStream inpStream = new DataInputStream(clientSock.getInputStream());
				DataOutputStream opStream = new DataOutputStream(clientSock.getOutputStream());

				File file = new File("ClientA.txt");
				FileOutputStream fos = new FileOutputStream(file);
				FileInputStream fis = new FileInputStream(file);

				bufLen = blockSizes[blk];
				
				byte[] buffContent = new byte[bufLen];
				int numRead = 0, totalBytes=0;

				while ((numRead = inpStream.read(buffContent))!=-1) 
				{
					fos.write(buffContent, 0, numRead);
					totalBytes+=numRead;
					if (totalBytes == bufLen)
						break;
				}

				numRead = 0;
				totalBytes = 0;
			
				while((numRead = fis.read(buffContent))!=-1)
				{		
					opStream.write(buffContent,0,numRead);
					totalBytes+=numRead;
					if (totalBytes == bufLen)
						break;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
