#!/usr/bin/env bash

cd /Users/ddv/workspace/java/Qisi
#! 消息队列启动
activeMQ start
#! redis启动
#redis-server
mvn spring-boot:run
