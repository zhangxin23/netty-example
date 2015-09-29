#!/bin/bash

if [[ -z $1 ]]; then
    echo "compiler-sefl.sh app_name"
    exit
fi

app_name="com.sandbox.netty.echov1.$1"

java -Dfile.encoding=UTF-8 -classpath /home/zhangxin/sandbox/netty-sandbox/target/classes:/home/zhangxin/.m2/repository/io/netty/netty-all/5.0.0.Alpha1/netty-all-5.0.0.Alpha1.jar ${app_name}
