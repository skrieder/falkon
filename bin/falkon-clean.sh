
#!/bin/bash

           
if [ -z "${FALKON_HOME}" ]; then
    echo "ERROR: environment variable FALKON_HOME not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_HOME}" ]; then
    echo "ERROR: invalid FALKON_HOME set: $FALKON_HOME" 1>&2
    return 1
fi

cd ${FALKON_HOME}

ant clean
     

