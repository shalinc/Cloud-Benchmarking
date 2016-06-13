import java.io.*;
import java.net.*;


public class ClientBM implements Runnable 
{
	static Socket clientSock;
	private int threadID;
	static long fileSize;
	public static int bufLen;
	public static int numOfThreads;

	public static void main(String args[]) throws UnknownHostException, IOException, Exception 
	{
		numOfThreads = Integer.parseInt(args[0]);
		
		//Connect with Server on Port
		clientSock = new Socket("52.26.161.78", 5004);	//!!! change of IP Required HERE !!!

		System.out.println("Connection Established Successfully");

		//Initiate the clients as per no. of Threads inputed
		for (int i = 1; i <= numOfThreads; i++)
		{
			ClientBM clientBM = new ClientBM();
			clientBM.threadID = i;
			new Thread(clientBM).start();
			Thread.sleep(1000);
		}
	}

	public void run() 
	{
		try 
		{
			int []blockSizes = {1,1024,65505};
			for(int blk = 0; blk< blockSizes.length; blk++)
			{
				bufLen = blockSizes[blk];
				//Create output stream to interact with sockets
				DataOutputStream dOut = new DataOutputStream(clientSock.getOutputStream());

				File file = new File("A.txt");
				fileSize = file.length();
				//System.out.println("File Size: " + fileSize + " bytes\n");
				FileInputStream fis = new FileInputStream(file);

				byte[] bufContent = new byte[bufLen];
				int numRead = 0;
				int totalBytes = 0;
				//System.out.println("Thread "+threadID+ " is Uploading the File");

				double start_time = System.nanoTime();

				//read the data from the file
				while ((numRead = fis.read(bufContent)) != -1) 
				{
					dOut.write(bufContent, 0, numRead);
					totalBytes+=numRead;
					if (totalBytes == bufLen)
						break;
				}

				//make a file and read the data back into it
				DataInputStream dIn = new DataInputStream(clientSock.getInputStream());
				File output = new File("Downloadedfile.txt");
				FileOutputStream fos = new FileOutputStream(output);
				numRead = 0;
				totalBytes = 0;
				bufContent = new byte[bufLen];

			
				while ((numRead = dIn.read(bufContent)) !=-1) 
				{
					fos.write(bufContent, 0, numRead);
					totalBytes+=numRead;
					if (totalBytes == bufLen)
						break;
				}

				double end_time = System.nanoTime();
				double totalElapsedTime = (end_time - start_time)/1000000;
				double throughput = (2*(bufLen/(1024.0*1024.0)))/(((totalElapsedTime)/1000));
				double latency = (totalElapsedTime)/2;
				//System.out.println("Thread: "+threadID+" Time : " + totalElapsedTime+ " ms");
				System.out.println("Thread: "+threadID+" Throughput for size "+bufLen+ " Byte(s) is "+ throughput + " MBPS");
				System.out.println("Thread: "+threadID+" Latency is: "+latency+" ms");
			}
		}			
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
