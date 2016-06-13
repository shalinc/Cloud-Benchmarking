#!/bin/sh

make

echo STARTING DISK BENCHMARKING ...
for threads in 1 2
do
	java DiskRandomAccess $threads 	
done

for threads in 1 2
do
	java DiskSequentialAccess $threads
done

echo DISK BENCHMARKING COMPLETED 
