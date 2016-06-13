import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Random;

class RunRandomAccess extends Thread
{
	public static long bufLen;
	public static int threadNum;
	public static double randm_write_time;
	public static double randm_read_time;

	@Override
	public void run()
	{
		try 
		{
			Random_write(RunRandomAccess.bufLen, threadNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//This method is for Random Write Implementation
	public static void Random_write(long bufLen, int threadNum) throws Exception
	{
		//Create a random access file
		File file = new File("random_access_test.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		//System.out.println(raf.length());

		//to generate random for seeking 
		Random randNum = new Random();

		byte [] writeData = new byte[(int)(bufLen)];

		int randPos= randNum.nextInt((int)raf.length()); 
		
		double startTime = System.nanoTime();

		//seek to that random position in the file, and write the block of data
		raf.seek(randPos);
		raf.write(writeData);

		double endTime = System.nanoTime();
		double totalTimeElapsed = (endTime - startTime)/1000000000;
		//System.out.println("Write Thread "+threadNum+" Buffer Size "+bufLen+" "+totalTimeElapsed);
		randm_write_time = totalTimeElapsed;
		raf.close();
		Random_read();
	}

	public static void Random_read() throws IOException
	{
		//System.out.println("In read random");
		File file = new File("random_access_test.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		byte [] readData = new byte[(int)bufLen];
		Random randNum = new Random();

		int randPos= randNum.nextInt((int)raf.length()); 
		double startTime = System.nanoTime();

		raf.seek(randPos);
		raf.read(readData);
		
		double endTime = System.nanoTime();
		double totalTimeElapsed = (endTime - startTime)/(1000000000);
		randm_read_time = totalTimeElapsed;

		//System.out.println("Read Thread "+threadNum+" Buffer Size "+bufLen+" "+totalTimeElapsed);
		raf.close();
	}
}


public class DiskRandomAccess {

	public static void main(String[] args) throws InterruptedException 
	{
		// TODO Auto-generated method stub
		int [] testBlockSize = {1,1024,1024*1024};//, 1024, 1024*1024};
	
		System.out.println("Peforming RANDOM ACCESS FOR DISK BENCHMARK");
		for(int i = 0; i< testBlockSize.length;i++)
		{
			double temp_read_time = 0;
			double temp_write_time = 0;

			RunRandomAccess.bufLen = testBlockSize[i];
			RunRandomAccess[] rra = new RunRandomAccess[2];
			int noOfThreads = Integer.parseInt(args[0]);

			//int [] noOfThreads = {1,2};//, 2};

			for(int j =0; j < noOfThreads;j++)
			{
				RunRandomAccess.threadNum = j+1;
				rra[j] = new RunRandomAccess();
				rra[j].start();
				Thread.sleep(100);

				temp_write_time+=RunRandomAccess.randm_write_time;
				temp_read_time+=RunRandomAccess.randm_read_time;
				//System.out.println("New "+temp_write_time/noOfThreads);
			}
			System.out.println("For "+noOfThreads+" thread(s) Write ("+testBlockSize[i]+" Byte(s)) " +
					"having speed "+((testBlockSize[i]/(temp_write_time/noOfThreads))/(1024*1024))+" MBPS");
			System.out.println("Latency is: "+(temp_write_time/noOfThreads)*1000+" ms");
			System.out.println("For "+noOfThreads+" thread(s) Read ("+testBlockSize[i]+" Byte(s)) " +
					"having speed "+((testBlockSize[i]/(temp_read_time/noOfThreads))/(1024*1024))+" MBPS");
			System.out.println("Latency is: "+(temp_read_time/noOfThreads)*1000+" ms");
		}
	}
}
