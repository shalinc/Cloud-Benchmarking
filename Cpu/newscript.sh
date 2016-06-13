#!/bin/sh

echo Plotting for 10 min for Integer Operations
for operation in 1
do 
	for i in 4
	do
        	./PLOT $operation $i >>PlottedIntegervalues.txt
	done
done

echo Completed Plotting IOPS

echo Plotting for 10 min for Floating Operation
for operation in 2
do
	for i in 4
	do
		./PLOT $operation $i >>PlottedFloatvalues.txt
	done
done

echo Completed Plotting FLOPS
