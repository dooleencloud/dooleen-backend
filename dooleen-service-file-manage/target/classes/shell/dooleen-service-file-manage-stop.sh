#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-file-manage.pid)
kill -9 $PID