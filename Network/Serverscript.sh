#/bin/sh

echo ******TCP NETWORK******  
for threads in 1 2
do
	java ServerBM $threads
done

echo ******UDP NETWORK******
for threads in 1 2
do
	java ServerUDP $threads
done
echo NETWORK BENCHMARKING COMPLETED 
