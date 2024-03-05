#!/bin/sh

cd /root/dooleen/dooleen-service-general-manage
pwd
. cd /root/dooleen/dooleen-service-general-manage

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx256m -Xms256m -Dloader.path=.,/root/dooleen/dooleen-service-general-manage/lib dooleen-service-general-manage-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-general-manage.pid