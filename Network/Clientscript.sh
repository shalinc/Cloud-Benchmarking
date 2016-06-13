#!/bin/sh

echo ******TCP NETWORK******
for threads in 1 2
do
	echo *****NETWORK BENCHMARKING USING $threads threads****
	java ClientBM $threads
done
echo ******UDP NETWORK******
for threads in 1 2
do
	echo *****NETWORK BENCHMARKING USING $threads threads****
	java ClientUDP $threads
done
echo BENCHMARKING COMPLETED 
