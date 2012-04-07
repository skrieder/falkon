#/bin/sh

if [ -z "${FALKON_ROOT}" ]; then
    echo "ERROR: environment variable FALKON_ROOT not defined"  1>&2
    return 1
fi

if [ ! -d "${FALKON_ROOT}" ]; then
    echo "ERROR: invalid FALKON_ROOT set: $FALKON_ROOT" 1>&2
    return 1
fi
                              
cd ${FALKON_ROOT}/cog
ant distclean
ant redist

cd ${FALKON_ROOT}/cog/modules/vdsk
ant distclean
ant -Dwith-provider-deef redist
