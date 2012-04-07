#!/bin/bash

for i in `seq 1 50`;
        do
                echo "Doing test ${i} ..."      
                ./run.worker-debug-nocreate.sh 0 0 tg-viz-login2 50002 etc/client-security-config.xml > /dev/null 2>&1 &
        done 
