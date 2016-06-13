#!/bin/sh

echo Performing CPU Benchmarking

make

for operation in 1 2
do
	for thread in 1 2 4
	do
		./CPU $operation $thread
	done
done

echo CPU Benchmarking Finished 
