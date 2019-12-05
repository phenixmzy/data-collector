#!/usr/bin/env bash
VERSION=0.0.1
BASE_DIR=$(dirname $(pwd))
JARS=""
for f in $BASE_DIR/lib/*.jar; do
        JARS=$JARS:$f
done
echo $JARS
java -cp $JARS:$BASE_DIR/data-collector-${VERSION}.jar com.phenix.bigdata.test.Setup 50000 vmphenix:9092,vmnarsi:9092,vmramon:9092 gameplay-input 5 10000 100000000 30
