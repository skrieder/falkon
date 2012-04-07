#!/bin/bash


if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_ROOT}" ]; then
    echo "ERROR: invalid FALKON_ROOT set: $FALKON_ROOT" 1>&2
    return 1
fi
       
cd ${FALKON_ROOT}
   
svn co https://cogkit.svn.sourceforge.net/svnroot/cogkit/trunk/current/src/cog
