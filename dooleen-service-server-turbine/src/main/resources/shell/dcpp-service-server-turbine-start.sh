#!/bin/sh

cd /root/dcpp/dcpp-service-server-turbine
pwd
. cd /root/dcpp/dcpp-service-server-turbine

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export PATH=$JAVA_HOME/bin:$PATH

java -jar -Xmx512m -Xms512m  -Dloader.path=.,/root/dcpp/lib dcpp-service-server-turbine-1.0.0-SNAPSHOT.jar --spring.profiles.active=$1 > /root/dcpp/logs/dcpp-service-server-turbine.log &
echo $! > /root/dcpp/data/dcpp-service-server-turbine.pid