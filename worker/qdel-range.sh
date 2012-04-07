#!/bin/bash

 if [ -z "$2" ]; then 
              echo "usage: $0 <START_ID> <START_ID>"
              echo "usage: $0 111111 222222"
              exit
          fi


# Now, let's do the same, using C-like syntax.

START_ID=$1
END_ID=$2

for ((a=${START_ID}; a <= ${END_ID} ; a++))  # Double parentheses, and "LIMIT" with no "$".
do
  echo "removing job ID $a"
  qdel $a
done                           # A construct borrowed from 'ksh93'.

echo; echo
