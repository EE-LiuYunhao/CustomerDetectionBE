#!/bin/bash

current=`date "+%Y-%m-%d %H:%M:%S"`  
timeStamp=`date -d "$current" +%s`   
currentTimeStamp=$((timeStamp*1000+`date "+%N"`/1000000)) 

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
export PATH=$JAVA_HOME/bin:$PATH
cd /home/cv001/ServerProgram/CustomerDetect/build/libs
nohup java -jar CustomerDetect-0.0.1-SNAPSHOT.jar > "$currentTimeStamp.log" 2>&1 &

#TODO: also start the CV prog
