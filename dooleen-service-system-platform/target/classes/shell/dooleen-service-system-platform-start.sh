#!/bin/sh

cd /root/dooleen/dooleen-service-system-platform
pwd
. cd /root/dooleen/dooleen-service-system-platform

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx256m -Xms256m -Dloader.path=.,/root/dooleen/dooleen-service-system-platform/lib dooleen-service-system-platform-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-system-platform.pid