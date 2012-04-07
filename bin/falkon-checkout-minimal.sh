#!/bin/bash
                 
if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_ROOT}" ]; then
    echo "ERROR: invalid FALKON_ROOT set: $FALKON_ROOT" 1>&2
    return 1
fi

cd ${FALKON_HOME}
   
svn co https://svn.globus.org/repos/falkon/config https://svn.globus.org/repos/falkon/logs https://svn.globus.org/repos/falkon/ploticus https://svn.globus.org/repos/falkon/webserver https://svn.globus.org/repos/falkon/client https://svn.globus.org/repos/falkon/container https://svn.globus.org/repos/falkon/monitor https://svn.globus.org/repos/falkon/service https://svn.globus.org/repos/falkon/worker
mkdir workloads
cd workloads
svn co https://svn.globus.org/repos/falkon/workloads/sleep

