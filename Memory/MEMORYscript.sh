#!/bin/sh

make
echo Performing MEMORY BENCHMARKING

for sizes in 1B 1KB 1MB
do
	for threads in 1 2
	do
		./MEMORY $threads $sizes
	done
done

echo MEMORY BENCHMARKING COMPLETED

