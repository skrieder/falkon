#!/bin/bash


if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi

echo "creating falkon dir..."
mkdir ${FALKON_HOME}/falkon
cd ${FALKON_HOME}/falkon

echo "checking out minimum falkon dir..."
#cp -r container service worker client bin ibm-java2-ppc64-50 ibm-java2-ppc-50 falkon/
svn co https://svn.globus.org/repos/falkon/client https://svn.globus.org/repos/falkon/container https://svn.globus.org/repos/falkon/service https://svn.globus.org/repos/falkon/worker
echo "copying java..."
cp -r ../ibm-java2-ppc-50 .
             
cd ${FALKON_HOME}/falkon

echo "removing the .svn folders..."
find ./ -name ".svn" -exec rm {} \; > /dev/null 2>&1
find ./ -name ".svn" -exec rm -rf {} \; > /dev/null 2>&1

cd ${FALKON_HOME}

echo "creating compressed archive..."
tar cfz falkon.tgz falkon

echo "cleaning up falkon dir..."
rm -rf falkon

echo "archive succesfully written to ${FALKON_HOME}/falkon.tgz"

