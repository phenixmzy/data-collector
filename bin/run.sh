#!/bin/bash
VERSION=0.0.1
BASE_DIR=$(dirname $(pwd))
JARS=""
for f in $BASE_DIR/lib/*.jar; do
        JARS=$JARS:$f
done
echo $JARS
java -cp $JARS:$BASE_DIR/data-collector-${VERSION}.jar com.phenix.bigdata.test.Setup 20170725 $BASE_DIR/conf
