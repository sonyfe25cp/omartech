#! /bin/bash

rm -rf engine-client/gen-java
rm -rf engine-server/gen-java
rm -rf omartech-utils/gen-java
rm -rf proxy/proxy-client/gen-java
rm -rf proxy/proxy-spider/gen-java
rm -rf weixin/weixin-web/gen-java
rm -rf weixin/weixin-spider/gen-java
rm -rf campus-recruiting-robot/gen-java

DIR=`pwd`
FILE="${DIR}/data.thrift"

(cd engine-client && thrift -gen java ${FILE})
(cd engine-server&& thrift -gen java ${FILE})

(cd campus-recruiting-robot&& thrift -gen java ${FILE})

(cd omartech-utils&& thrift -gen java ${FILE})
(cd proxy/proxy-client&& thrift -gen java ${FILE})
(cd proxy/proxy-spider&& thrift -gen java ${FILE})
(cd weixin/weixin-web&& thrift -gen java ${FILE})
(cd weixin/weixin-spider&& thrift -gen java ${FILE})

(cd python-scripts && python gen_hooks.py --file ../data.thrift --mode gen-java > ../engine-client/src/main/java/com/omartech/engine/client/DataClients.java)

if [[ $# -ne 0 ]]; then
    (cd thrift-router && ./gen.sh)
fi
