#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-server-admin$1.pid)
kill -9 $PID