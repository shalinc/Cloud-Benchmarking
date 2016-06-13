<h3>BENCHMARKING</h3>
<p>This project aims at Benchmarking AWS t2.micro instance for its CPU, MEMORY, DISK and NETWORK. </p>

<h4>CPU Benchmarking</h4>
<p>This program calculates the Integer and Floating point operations, in terms of GIOPS and GFLOPS. 
The main aim is to utilize the complete CPU cycles by executing different arithmetic instructions. 
Utilizing the CPU’s Floating point unit (FPU) completely so that it gives us the maximum FLOPS.</p>

<h4>DISK Benchmarking</h4>
<p>The design includes implementation for 3 different block sizes i.e. 1B, 1KB and 1MB each for Sequential and Random operations.
It implements 4 methods, sequential read & write and random read & write.
The sequential access is done using a file, and data is read from the file and written into it, in a sequential manner.
For random access, a random number is generated which lies within the file size, and is seeked to that location onto that file and read and write operations are performed.</p>

<h4>MEMORY Benchmarking</h4>
<p>For different block sizes i.e. 1 Byte, 1 KB and 1 MB, sequential and random access to the memory is made.
The disk access are made using memcpy() function which is used to perform read and write operation onto the memory.</p>

<h4>NETWORK Benchmarking</h4>
<p>The benchmarking is done for both TCP as well as UDP protocol. The code is written to be executed on two different instances of AWS.
This code does the basic packets transmission from Client to Server and back again, while implementing this we find the RTT of the transmission.
The packets transmitted are of various sizes i.e. 1 Byte, 1KB and 1MB.
The TCP being reliable and connection oriented requires pre connection setup and accepting of connection between client and server.
On the other hand UDP being connection less, the packets are sent and received without and pre established connection.</p>

<h4>README:</h4>

Considering the instance on which the program is going to be tested has the required compilers for Java and C programs
Extract the Folder named "Cloud-Benchmarking.zip" or Clone the Repository

<h4>How to run:</h4>

	"CPU BENCHMARKING"

1. goto the folder named "Cpu"
2. Open the terminal for the instance you are running on
3. execute the script file named "CPUscript.sh" as:
	sh CPUscript.sh
4. The desired output for GIOPS and GFLOPS will be displayed

	<code>"600 Sec plot values for CPU"</code>

1. In the "Cpu" folder, you will find a script "newscript.sh"
2. execute the script file as:
	sh newscript.sh
3. The terminal will display the appropriate msg for Integer and Floating point operations
4. The operations run for 10 mins each, giving 2 .txt files having the values for per second
5. The "Cpu" folder already contains "PlottedFloat" and "PlottedInteger" text files, which shows these values
5. For this script new files will be generated named "PlottedFloatvalues" and "PlottedIntegervalues" text files



	"DISK BENCHMARKING"

1. goto the folder named "Disk"
2. Open the terminal for the instance you are running on
3. execute the script file named "DISKscript.sh" as:
	sh DISKscript.sh
4. The desired output for RANDOM and SEQUENTIAL Read & Write operations will be displayed



	"MEMORY BENCHMARKING"

1. goto the folder named "Memory"
2. Open the terminal for the instance you are running on
3. execute the script file named "MEMORYscript.sh" as:
	sh MEMORYscript.sh
4. The desired output for RANDOM ACCESS and SEQUENTIAL ACCESS for Memory will be displayed



	"NETWORK BENCHMARKING"

1. goto the folder named "Network"
2. Here we will require 2 instances to be opened
3. one of the isntance will be acting as SERVER and another will be acting as CLIENT
4. Before execution, the files for Client and Server needs to modified with the IPAddress
5. For the file "ClientBM.java", change the LINE NO: 18, to the IPAddress of the Server
   For the file "ClientUDP.java", change the LINE NO: 42, to the IPAddress of the Server
6. Copy the entire "Network" folder on both the terminals
7. execute command javac *.java on both the terminals
8. On Server side execute the script as:
	sh Serverscript.sh
9. On Client side execute the script as:
	sh Clientscript.sh 
10. The desired output for TCP & UDP will be displayed on the Client Side terminal



"BENCHMARK TOOLS"

Considering that all the Benchmarks files are already present
 
"LINPACK BENCHMARK FOR CPU"
	1. goto "l_mklb_p_11.3.1.002/benchmarks_11.3.1/linux/mkl/benchmarks/linpack" path
	execute ./runme_xeon64 
	this will run the Benchmark with its own values and the output will be displayed
	
	2. to run with different input data, execute ./xlinpack_xeon64
	a message will be prompted press ENTER
	then type the values for 
	Number of equations to solve (problem size): 
	Leading dimension of array: (should be >= 5000)
	Number of trials to run:
	Data alignment value (in Kbytes): (should not be > 64)

"IOZONE BENCHMARK FOR DISK"
	1. goto iozone3_434/src/current path
	execute ./iozone -a
	This will give the Disk Benchmark values for different file sizes,
	
	2. To execute for a particular file size 
	execute ./iozone -g# -s 1024
	This will give the Output for file size of 1024 Kbytes	

"STREAM BENCHMARK FOR MEMORY"

	1. do, wget http://www.nersc.gov/assets/Trinity--NERSC-8-RFP/Benchmarks/Jan9/stream.tar
	This will download STREAM in your current working directory

	2. do gcc -o stream stream.c
	execute ./stream
	The desired output will be displayed


"IPERF BENCHMARK FOR NETWORK"

	1. do sudo apt-get install iperf, to install IPERF
		"FOR TCP"
	2. On one instance as Server execute: iperf -s and on Client execute: iperf -c <IPAddress of Server> -n <size of packet to send in bytes>
	3. It will send the packet and the bandwidth will be displayed accordingly
		"FOR UDP"
	4. On one instance as Server execute: iperf -su and 
	   On Client execute: iperf -c <IPAddress of Server> -u -n <size of packet to send in bytes>
	5. It will send the packet and the bandwidth will be displayed accordingly
