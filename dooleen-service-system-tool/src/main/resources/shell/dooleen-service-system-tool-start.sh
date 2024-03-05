#!/bin/sh

cd /root/dooleen/dooleen-service-system-tool
pwd
. cd /root/dooleen/dooleen-service-system-tool

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx512m -Xms512m -Dloader.path=.,/root/dooleen/dooleen-service-system-tool/lib dooleen-service-system-tool-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-system-tool.pid