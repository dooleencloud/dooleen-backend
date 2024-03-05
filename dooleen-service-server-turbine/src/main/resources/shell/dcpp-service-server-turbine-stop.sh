#!/bin/sh
PID=$(cat /root/dcpp/data/dcpp-service-server-turbine.pid)
kill -9 $PID