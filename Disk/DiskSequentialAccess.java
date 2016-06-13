import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

class RunSequentialAccess extends Thread
{
	public static long bufLen;
	public static int threadNum;
	public static double seq_write_time;
	public static double seq_read_time;
	public static FileInputStream fis = null;
	public static FileOutputStream fos = null;

	@Override
	public void run()
	{
		try 
		{
			Sequential_write(bufLen, threadNum);
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Sequential_write(long bufLen, int threadNum) throws Exception
	{
		File file = new File("random_access_test.txt");
		FileOutputStream fos = new FileOutputStream(file,true);
		fos = new FileOutputStream(file);
		
		byte [] bufContent = new byte[(int)(bufLen)];
		for(long i = 0;i<bufLen;i++)
			bufContent[(int)i] = 'a';
		
		double startTime = System.nanoTime();
			fos.write(bufContent);
		double endTime = System.nanoTime();
		
		 //System.out.println("ST - ET "+(endTime-startTime)+"for "+bufLen);
		 double totalTimeElapsed1 = (endTime - startTime)/(1000000000);
		 seq_write_time = totalTimeElapsed1;
		
		 Sequential_read(bufLen);
	}

	public static void Sequential_read(double bufLen) throws Exception
	{
		File file = new File("random_access_test.txt");
		FileInputStream fileInpStream = new FileInputStream(file);
		int numRead = 0, count = 0;

		byte[] readData = new byte[(int)bufLen];

		double startTime = System.nanoTime();
			fileInpStream.read(readData);
		double endTime = System.nanoTime();

		double totalTimeElapsed = (endTime - startTime)/1000000000;
		seq_read_time = totalTimeElapsed;
	}
}


public class DiskSequentialAccess 
{

	public static void main(String[] args) throws InterruptedException 
	{
		System.out.println("PERFORMING SEQUENTIAL ACCESS ON DISK");
		// TODO Auto-generated method stub
		int [] testBlockSize = {1,1024,1024*1024};

		for(int i = 0; i< testBlockSize.length;i++)
		{
			double temp_read_time = 0;
			double temp_write_time = 0;

			RunSequentialAccess.bufLen = testBlockSize[i];
			RunSequentialAccess[] rsa = new RunSequentialAccess[2];
			
			int noOfThreads = Integer.parseInt(args[0]);

			for(int j = 0; j<noOfThreads; j++)
			{
				RunSequentialAccess.threadNum = j+1;
				rsa[j] = new RunSequentialAccess();
				rsa[j].start();
				Thread.sleep(1000);

				temp_write_time+=RunSequentialAccess.seq_write_time;
				temp_read_time+=RunSequentialAccess.seq_read_time;
			
			}	
			System.out.println("For "+noOfThreads+" thread(s) SEQ WRITE ("+testBlockSize[i]+" Byte(s)) " +
					"having speed "+(((testBlockSize[i])/(temp_write_time/noOfThreads)/(1024*1024))+ " MBPS"));
			System.out.println("Latency is: "+(temp_write_time/noOfThreads)*(1000)+" ms");	
			System.out.println("For "+noOfThreads+" thread(s) SEQ READ ("+testBlockSize[i]+" Byte(s)) " +
					"having speed "+(((testBlockSize[i])/(temp_read_time/noOfThreads)/(1024*1024))+" MBPS"));
			System.out.println("Latency is: "+(temp_read_time/noOfThreads)*(1000)+" ms");
		}
	}
}
