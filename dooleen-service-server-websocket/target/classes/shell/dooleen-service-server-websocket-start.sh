#!/bin/sh

cd /root/dooleen/dooleen-service-server-websocket
pwd
. cd /root/dooleen/dooleen-service-server-websocket

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx512m -Xms512m -Dloader.path=.,/root/dooleen/dooleen-service-server-websocket/lib dooleen-service-server-websocket-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-server-websocket.pid