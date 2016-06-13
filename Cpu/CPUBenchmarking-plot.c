/*
 * CPU.c
 *
 *  Created on: Feb 5, 2016
 *      Author: shalin
 */


#include <stdio.h>
#include <math.h>
#include <sys/time.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>

struct timeval st,et;
double start_time, end_time, total_time;
int i;

void *intBM();
void *floatBM();
void run_integer_BM(int );
void run_floatingPoint_BM(int );

int main(int argc, char *argv[])
{	
	int operation_to_perform = atoi(argv[1]);
	int num_of_threads = atoi(argv[2]);
	//printf("USAGE: <Operation to Perform 1(IOPS) 2(FLOPS) > <No of Threads (1,2,4)>")
	
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

//	printf("%lf\n",total_time);
	//printf("\nPerformed 31*10^9 Integer Operation @ %lf GFLOPS\n",((31*num_of_threads)/(end_time-start_time)));

}

//Calculate Benchmarks for IOPS
void run_floatingPoint_BM(int num_of_threads)
{
	//allocation of memory for threads
	pthread_t *thread_ID = malloc(sizeof(pthread_t)*num_of_threads);
	
	//Note the start time
	gettimeofday(&st, NULL);
	start_time = st.tv_sec+(st.tv_usec/1000000.0);

	//creation of threads
	for(i=0;i<num_of_threads;i++)
		pthread_create(&thread_ID[0],NULL,floatBM,NULL);
	for(i=0;i<num_of_threads;i++)
		pthread_join(thread_ID[i],NULL);
	
	//Note the End Time
	gettimeofday(&et, NULL);
	end_time = et.tv_sec+(et.tv_usec/1000000.0);
	
	//total Time taken for Execution
	total_time = (end_time-start_time);

//	printf("%lf\n",total_time);
	//printf("\nPerformed 31*10^9 Floating Point Operation @ %lf GFLOPS\n",((31*num_of_threads)/(end_time-start_time)));
}

//integer operations
void *intBM()
{

	int no_of_loops = 1000000000;
	int a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7;
	int i = 0, count_sec = 0;
	long count_op = 0;
	long time_to_exec = 10;
	
	gettimeofday(&st, NULL);

	long timer_start = st.tv_sec+(st.tv_usec/1000000.0);
	long timer_end,temp_timer = 0;
	
	gettimeofday(&et, NULL);
		
	while(count_sec <= (int)time_to_exec*60)
	{
		timer_end = (et.tv_sec+(et.tv_usec/1000000.0));
		gettimeofday(&et, NULL);

		if(timer_end!=temp_timer)
		{
			temp_timer = timer_end;
			//printf("Inside %ld\n",timer_end);
	
			printf("%ld,%d\n",count_op,count_sec);
			count_sec++;
			count_op = 0;	
		}
		count_op += 16;
		
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

	int no_of_loops = 1000000000;
	float a = 1.23, b = 2.34, c = 3.45, d = 4.56, e = 5.67, f = 6.78, g = 7.89;
	int i = 0, count_sec = 0;
	long count_op = 0;
	long time_to_exec = 10;
	
	gettimeofday(&st, NULL);

	long timer_start = st.tv_sec+(st.tv_usec/1000000.0);
	long timer_end,temp_timer = 0;
	
	gettimeofday(&et, NULL);
		
	while(count_sec <= (int)time_to_exec*60)
	{
		timer_end = (et.tv_sec+(et.tv_usec/1000000.0));
		gettimeofday(&et, NULL);

		if(timer_end!=temp_timer)
		{
			temp_timer = timer_end;
			//printf("Inside %ld\n",timer_end);
	
			printf("%ld,%d\n",count_op,count_sec);
			count_sec++;
			count_op = 0;	
		}
		count_op += 16;
		
		b + c * c + d;
        	d * e + e * f;
        	a + g / b * a;
        	c + d - d + a;
        	e / a * a / g;

		i++;
	}
	pthread_exit(NULL);
}

