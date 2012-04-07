#!/bin/sh

DATE=`date`
TMPDIR=/tmp/globus_`id -u`
TMPFILE=$TMPDIR/glue_detected

# dump some simple data to a file to be sure it was triggered
if [ ! -d $TMPDIR ]; then
   mkdir $TMPDIR
fi

if [ ! -O $TMPDIR ]; then
   echo $TMPDIR does not exist or has wrong owner
   exit 1
fi

if [ -f $TMPFILE -o ! -e $TMPFILE ]; then
   echo "GLUECE RP was detected in the output at $DATE" >> $TMPFILE
fi

cat <<FIN
<?xml version="1.0" encoding="UTF-8"?>
<exampleGLUETriggerActionScriptOutput>
  <glueDataDetected>true</glueDataDetected>
</exampleGLUETriggerActionScriptOutput>
FIN
