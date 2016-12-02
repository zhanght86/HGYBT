@echo off
echo "Load system setting..."
echo ------------------------------------------------------

REM #set java environment
set JAVA_HOME=C:/Program Files/Java/jre6
echo JAVA_HOME=%JAVA_HOME%

set MIDPLAT_HOME=D:/release/hxlife_midplat/WEB-INF
echo MIDPLAT_HOME=%MIDPLAT_HOME%

set CLASSPATH=%MIDPLAT_HOME%/classes;%MIDPLAT_HOME%/lib/*
echo CLASSPATH=%CLASSPATH%

REM #out java version
"%JAVA_HOME%/bin/java" -version

echo ------------------------------------------------------
echo.

"%JAVA_HOME%/bin/java" com.sinosoft.midplat.bat.BatListener