#!/bin/sh

cd /root/dooleen/dooleen-service-api-doc
pwd
. cd /root/dooleen/dooleen-service-api-doc

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx512m -Xms512m -Dloader.path=.,/root/dooleen/dooleen-service-api-doc/lib dooleen-service-api-doc-1.0.0-SNAPSHOT.jar > /dev/null &
echo $! > /root/dooleen/data/dooleen-service-api-doc.pid