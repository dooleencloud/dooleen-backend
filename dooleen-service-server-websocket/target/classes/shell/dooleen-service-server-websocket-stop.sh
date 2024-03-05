#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-server-websocket.pid)
kill -9 $PID