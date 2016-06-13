/*
 * CPU.c
 * 
 * This code is for Benchmarking the CPU and getting the IOPS & FLOPS 
 * by performing Arithmetic operatins using Floating point and Integer Operations
 *
 */


#include <stdio.h>
#include <math.h>
#include <sys/time.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>

//time struct for noting down time
struct timeval st,et;

double start_time, end_time, total_time;
int i;

//Function Declaration
void *intBM();
void *floatBM();
void run_integer_BM(int );
void run_floatingPoint_BM(int );

//main code
int main(int argc, char *argv[])
{	
	//command line args for operation t perform and num of threads
	int operation_to_perform = atoi(argv[1]);
	int num_of_threads = atoi(argv[2]);
	//printf("USAGE: <Operation to Perform 1(IOPS) 2(FLOPS) > <No of Threads (1,2,4)>")
	
	//switch according to operation to be performed
	switch(operation_to_perform)
	{
		case 1:
			run_integer_BM(num_of_threads);
			break;
		case 2:
			run_floatingPoint_BM(num_of_threads);
			break;
	}
	
return 0;
}

//Calculate Benchmarks for IOPS
void run_integer_BM(int num_of_threads)
{
	//allocation of memory for threads
	pthread_t *thread_ID = malloc(sizeof(pthread_t)*num_of_threads);
	
	printf("Benchmarking for Integer Operations using %d threads\n",num_of_threads);
	//Note the start time
	gettimeofday(&st, NULL);
	start_time = st.tv_sec+(st.tv_usec/1000000.0);

	//creation of threads
	for(i=0;i<num_of_threads;i++)
		pthread_create(&thread_ID[0],NULL,intBM,NULL);
	for(i=0;i<num_of_threads;i++)
		pthread_join(thread_ID[i],NULL);
	
	//Note the End Time
	gettimeofday(&et, NULL);
	end_time = et.tv_sec+(et.tv_usec/1000000.0);
	
	//total Time taken for Execution
	total_time = (end_time-start_time);

	//	printf("Time Taken: %lf sec",total_time);
	printf("Performed Integer Operation @ %lf GIOPS\n",((17*num_of_threads)/(end_time-start_time)));

}

//Calculate Benchmarks for IOPS
void run_floatingPoint_BM(int num_of_threads)
{
	//allocation of memory for threads
	pthread_t *thread_ID = malloc(sizeof(pthread_t)*num_of_threads);
	
	//calculate the loop Time

	printf("Benchmarking for Floating Point Operations using %d threads\n",num_of_threads);
	//Note the start time
	gettimeofday(&st, NULL);
	start_time = st.tv_sec+(st.tv_usec/1000000.0);

	//creation of threads
	for(i=0;i<num_of_threads;i++)
		pthread_create(&thread_ID[i],NULL,floatBM,NULL);
	for(i=0;i<num_of_threads;i++)
		pthread_join(thread_ID[i],NULL);
	
	//Note the End Time
	gettimeofday(&et, NULL);
	end_time = et.tv_sec+(et.tv_usec/1000000.0);
	
	//total Time taken for Execution
	total_time = (end_time-start_time);

	//printf("Time Taken: %lf sec",total_time);
	printf("Performed Floating Point Operation @ %lf GFLOPS\n",((16*num_of_threads)/(total_time)));
}

//integer operations
void *intBM()
{

	//Loop till 10E9
	int no_of_loops = 1000000000;
	
	//Initalize the integer variable
	int a = 10, b = 20, c = 30, d = 40, e = 50, f = 64, g = 77;
	int i = 0;
	long timer_end,temp_timer = 0;
	
	//perform 17 operations in a loop 10E9 times		
	while(i < no_of_loops)
	{
		b + c * c + d;
		d * e + e * f;
		a + g + b * a;
        	c + d - d + a;
		e + a * a * g;

		i++;
	}
		
	pthread_exit(NULL);
}

//floating point operations
void *floatBM()
{
	//init the loop till value of 10E9
	int no_of_loops = 1000000000;
	float a = 11.23f, b = 22.34f, c = 333.45f, d = 444.56f, e = 51.67f, f = 62.78f, g = 75.89f;
	int i = 0;

	//loop for float operations till 10E9 times
	while(i < no_of_loops)
	{
		
		b + c * c + d;
		d * e + e * f;
		a + g / b * a;
        	c + d - d + a;
		e / a * a / g;
		i++;
	}
	pthread_exit(NULL);
}

