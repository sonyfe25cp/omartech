#! /bin/bash

rm -rf engine-client/gen-java
rm -rf engine-server/gen-java

DIR=`pwd`
FILE="${DIR}/data.thrift"

(cd engine-client && thrift -gen java ${FILE})
(cd engine-server&& thrift -gen java ${FILE})


if [[ $# -ne 0 ]]; then
    (cd thrift-router && ./gen.sh)
fi
