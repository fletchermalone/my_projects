#!/bin/bash

title="nyse"
maxTime=60
check_time=`date | cut -d":" -f2`
while true
do
	time_t=`date | cut -d":" -f2`
	if [ ${time_t} != ${check_time} ]
		then
			wget -O ${title}_${time_t} http://online.wsj.com/mdc/public/page/2_3021-activnyse-actives.html
			((maxTime--))
			check_time=$time_t
			
		fi
		if [ $maxTime -eq 0 ]
			then
			break
		fi
done
