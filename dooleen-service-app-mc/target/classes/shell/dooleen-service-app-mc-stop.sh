#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-app-mc.pid)
kill -9 $PID