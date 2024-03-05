#!/bin/sh

cd /root/dooleen/dooleen-service-server-admin
pwd
. cd /root/dooleen/dooleen-service-server-admin

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx256m -Xms256m -Dloader.path=.,/root/dooleen/dooleen-service-server-admin/lib dooleen-service-server-admin-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-server-admin.pid