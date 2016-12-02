@echo off
echo "Load system setting..."
echo ------------------------------------------------------

REM #set java environment
set JAVA_HOME=C:/Program Files/jrrt-3.1.2-1.6.0
echo JAVA_HOME=%JAVA_HOME%

set MIDPLAT_HOME=D:/release/hxlife_midplat/WEB-INF
echo MIDPLAT_HOME=%MIDPLAT_HOME%

set CLASSPATH=%MIDPLAT_HOME%/classes;%MIDPLAT_HOME%/lib/*
echo CLASSPATH=%CLASSPATH%

REM #out java version
"%JAVA_HOME%/bin/java" -version

REM #set jvm arg
set JVM_ARG=-Xms64M -Xmx64M
echo JVM_ARG=%JVM_ARG%

echo ------------------------------------------------------
echo.

"%JAVA_HOME%/bin/java" %JVM_ARG% com.sinosoft.midplat.net.SocketListener