#!/bin/bash
 if [ -z "$2" ]; then 
	               echo "usage: $0 <input> <output>"
		                     exit 1
				               fi
cat $1 >> $2
