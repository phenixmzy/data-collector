#!/usr/bin/env bash
VERSION=0.0.1
BASE_DIR=$(dirname $(pwd))
JARS=""
for f in $BASE_DIR/lib/*.jar; do
        JARS=$JARS:$f
done
echo $JARS
java -cp $JARS:$BASE_DIR/data-collector-${VERSION}.jar com.phenix.bigdata.test.Setup 10000 yzj-client-01:9092,yzj-client-02:9092,yzj-client-03:9092 gameplay-log-input 5 30
