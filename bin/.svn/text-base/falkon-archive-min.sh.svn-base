#!/bin/bash


if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"
    exit 1
fi

echo "creating falkon dir..."
mkdir ${FALKON_HOME}/falkon
cd ${FALKON_HOME}

echo "copying the minimum falkon dir..."
#cp -r container service worker client bin ibm-java2-ppc64-50 ibm-java2-ppc-50 falkon/
cp -r container ibm-java2-ppc-50 falkon/
rm falkon/container/etc/security-config.xml falkon/container/etc/client-security-config.xml falkon/container/etc/Falkon.config falkon/container/etc/Provisioner.config falkon/container/etc/worker-security-config.xml
cp config/security-config.xml config/client-security-config.xml config/Falkon.config config/Provisioner.config config/worker-security-config.xml falkon/container/etc/

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

