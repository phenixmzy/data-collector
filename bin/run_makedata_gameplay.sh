#!/usr/bin/env bash
# vim run_makedate_gameplay.sh
# >set ff=unix

VERSION=0.0.1
BASE_DIR=$(dirname $(pwd))
JARS=""
for f in $BASE_DIR/lib/*.jar; do
        JARS=$JARS:$f
done
echo $JARS
java -cp $JARS:$BASE_DIR/data-collector-${VERSION}.jar com.phenix.bigdata.test.Setup 5 vmphenix:9092,vmnarsi:9092,vmramon:9092 source-gamebrowse source-gameplay  5 60 300 10000 100000000
