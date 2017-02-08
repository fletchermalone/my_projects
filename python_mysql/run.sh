#!/bin/bash


cur="nyse_01"

java -jar tagsoup-1.2.1.jar --files ${cur} 

curx=`ls | grep ${cur}.xhtml`
replace=${curx//"xhtml"/xml} 
echo $replace
mv ${curx} ${replace}

makeFile.py