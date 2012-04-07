#!/bin/bash

 if [ -z "$3" ]; then 
              echo "usage: $0 <input> <output> <sleep>"
              exit -1
          fi

/bin/cp $1 $2

if [ "$?" -ne "0" ]; then
  echo "Error in copy command... exit code $?"
  exit $?
fi


/bin/sleep $3

if [ "$?" -ne "0" ]; then
  echo "Error in sleep command... exit code $?"
  exit $?
fi
