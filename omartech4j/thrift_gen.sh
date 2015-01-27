#! /bin/bash

rm -rf engine-client/gen-java
rm -rf engine-server/gen-java
rm -rf omartech-utils/gen-java
rm -rf proxy/proxy-client/gen-java
rm -rf proxy/proxy-spider/gen-java
rm -rf weixin/weixin-web/gen-java
rm -rf weixin/weixin-spider/gen-java

DIR=`pwd`
FILE="${DIR}/data.thrift"

(cd engine-client && thrift -gen java ${FILE})
(cd engine-server&& thrift -gen java ${FILE})


(cd omartech-utils&& thrift -gen java ${FILE})
(cd proxy/proxy-client&& thrift -gen java ${FILE})
(cd proxy/proxy-spider&& thrift -gen java ${FILE})
(cd weixin/weixin-web&& thrift -gen java ${FILE})
(cd weixin/weixin-spider&& thrift -gen java ${FILE})


if [[ $# -ne 0 ]]; then
    (cd thrift-router && ./gen.sh)
fi
