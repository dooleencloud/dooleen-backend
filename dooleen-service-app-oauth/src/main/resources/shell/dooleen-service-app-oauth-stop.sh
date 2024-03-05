#!/bin/sh
PID=$(cat /root/dooleen/data/dooleen-service-app-oauth.pid)
kill -9 $PID