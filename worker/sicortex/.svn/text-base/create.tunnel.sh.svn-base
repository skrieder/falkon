#!/bin/bash


 if [ -z "$3" ]; then 
              echo "usage: $0 <REMOTE_IP> <PORT1> <PORT2>"
              echo "usage: $0 communicado.ci.uchicago.edu 56000 56001"
              exit 1
          fi


LOCAL_IP=172.31.223.206
REMOTE_IP=$1
PORT1=$2
PORT2=$3

ssh -N -L $LOCAL_IP:$PORT1:$REMOTE_IP:$PORT1 -L $LOCAL_IP:$PORT2:$REMOTE_IP:$PORT2 $REMOTE_IP
