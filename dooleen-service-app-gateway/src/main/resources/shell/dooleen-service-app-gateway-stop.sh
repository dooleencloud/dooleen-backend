#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-app-gateway.pid)
kill -9 $PID