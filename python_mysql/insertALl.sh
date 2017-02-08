#!/bin/bash

i=59

while [ $i -ne 0 ]
do
	if [ $i -ge 10 ]
		then
	python hm9.py nyse_${i}
	
else
	python hm9.py nyse_0${i}
	
fi

	((i--))
done
