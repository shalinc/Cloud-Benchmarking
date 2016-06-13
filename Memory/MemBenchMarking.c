/*
 * MemoryBenchMarking.c
 *
 */

#include <stdio.h>
#include <math.h>
#include <sys/time.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>

void* seq_access();
void* random_access();
//int ran_read(void*);
//int ran_write(void*);


long int memorySpace = 1024*1024*50;	//Allocating 50 MB of memory space
long int blockSize = 0;
struct timeval st, et;

int main(int argc, char *argv[])
{
        //int blockSize = 0;

        int noOfThreads = atoi(argv[1]);
        char* blockSizeInp = argv[2];
        double start_time, end_time, totalTimeElapsed;

        if(strcmp(blockSizeInp,"1B")==0)
                blockSize = 1;
        if(strcmp(blockSizeInp,"1KB")==0)
                blockSize = 1024;
        if(strcmp(blockSizeInp,"1MB")==0)
                blockSize = 1024*1024;

        //printf("Taking Command Line");
        int i, j, thread_loop;
        pthread_t threads[noOfThreads];

        //printf("Calling Threads");

	printf("Performing Memory Benchmarking for ***%s*** using %d thread(s)\n",argv[2],noOfThreads);
	printf("***SEQUENTIAL ACCESS OUTPUT***\n");
        gettimeofday(&st, NULL);
        start_time = st.tv_sec+(st.tv_usec/1000000.0);
        
	for(i=0; i<noOfThreads; i++)
                pthread_create(&threads[i],NULL,seq_access, NULL);
        for(j=0; j<noOfThreads; j++)
                pthread_join(threads[j],NULL);
        gettimeofday(&et, NULL);
        end_time = et.tv_sec+(et.tv_usec/1000000.0);
        totalTimeElapsed = (end_time-start_time);
        
	printf("Time taken: %lf\n",totalTimeElapsed);
        printf("Throughput is: %lf MBPS\n",((noOfThreads * memorySpace)/totalTimeElapsed)/(1024*1024));
        printf("Latency is: %0.12lf ms\n",(totalTimeElapsed*1000)/(memorySpace*noOfThreads*blockSize));
	
	
		printf("***RANDOM ACCESS OUTPUT***\n");
		gettimeofday(&st, NULL);
        start_time = st.tv_sec+(st.tv_usec/1000000.0);
        for(i=0; i<noOfThreads; i++)
                pthread_create(&threads[i],NULL,random_access, NULL);
        for(j=0; j<noOfThreads; j++)
                pthread_join(threads[j],NULL);
        gettimeofday(&et, NULL);
        end_time = et.tv_sec+(et.tv_usec/1000000.0);
        totalTimeElapsed = (end_time-start_time);
        printf("Time taken: %lf\n",totalTimeElapsed);
        printf("Throughput is: %lf\n",((noOfThreads * memorySpace)/totalTimeElapsed)/(1024*1024));
        printf("Latency is: %0.12lf\n",(totalTimeElapsed*1000)/(memorySpace*noOfThreads));
        return 0;
}


void* seq_access()
{
        //printf("Called");
        long int blockLen = blockSize;
        //printf("%d",blockLen);
        int loopTill = memorySpace/blockLen;
        int i=0, temp = 0;
	long int j;
        char *seqBlock1 = (char*)malloc(memorySpace*sizeof(char));
        char *seqBlock2 = (char*)malloc(blockLen*sizeof(char));

	memset(seqBlock1,'s',blockLen*sizeof(char));
       
	if(blockLen == 1)
	{
		while(i < loopTill)
       	 	{
                	memset(seqBlock1+temp,'s',1);
			memcpy(seqBlock2,seqBlock1+temp,blockLen);
                	temp+=blockLen;
                	i++;
        	}
	}
	else if(blockLen == 1024)
	{
		for(j =0; j<(1024*1024); j++)	
		while(i < loopTill)
       	 	{
                	memset(seqBlock1+temp,'s',1024);
                	memcpy(seqBlock2,seqBlock1+temp,blockLen);
                	temp+=blockLen;
                	i++;
        	}
	}
	else if(blockLen == (1024*1024))
	{
		//for(j =0; j<(50); j++)	
		while(i < loopTill)
       	 	{
                	//memset(seqBlock1+temp,'s',blockLen);
                	memcpy(seqBlock2,seqBlock1+temp,blockLen);
                	temp+=blockLen;
                	i++;
        	}
	}
        free(seqBlock1);
        free(seqBlock2);

        pthread_exit(NULL);
}

void* random_access()
{

        long int blockLen = blockSize;
        //printf("%d",blockLen);
        int loopTill = memorySpace/blockLen;
        int i=0, temp = 0, ran_loc=0;
	long int j;
        char *ranBlock1 = (char*)malloc(memorySpace*sizeof(char));
        char *ranBlock2 = (char*)malloc(blockLen*sizeof(char));

	//memset(ranBlock2,'s',blockLen*sizeof(char));
	//for(j=0;j<20;j++)
        if(blockLen == 1)
	{
		while(i < loopTill)
        	{
                	ran_loc = rand()%(memorySpace/blockLen);
               	 	memset(ranBlock1+ran_loc,'s',1);
			memcpy(ranBlock2,ranBlock1+ran_loc,blockLen);
                	i++;
        	}
	}	
	else if(blockLen == 1024)
	{
		for(j =0; j<(1024*1024*10);j++)	
		while(i < loopTill) 	
		{
                	ran_loc = rand()%(memorySpace/blockLen);
			memset(ranBlock1+ran_loc,'s',blockLen);
			memcpy(ranBlock2,ranBlock1+ran_loc,blockLen);
                	//temp+=blockLen;
                	i++;
        	}
	}
	else if(blockLen == (1024*1024))
	{
		for(j =0; j<(1024*1024); j++)	
		while(i < loopTill)
       	 	{
			ran_loc = rand()%(memorySpace/blockLen);
                	memset(ranBlock1+ran_loc,'s',blockLen);
			memcpy(ranBlock2,ranBlock1+ran_loc,blockLen);
                	//temp+=blockLen;
                	i++;
        	}
	}
	free(ranBlock1);
        free(ranBlock2);

        pthread_exit(NULL);
}
