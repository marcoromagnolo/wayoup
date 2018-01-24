@ECHO off

set BUILD_PATH="src\server"
set JBOSS_DIR="src\platform\jboss-as"

echo Using JAVA_HOME: "%JAVA_HOME%"
echo Using BUILD_PATH: %BUILD_PATH%
echo Using JBOSS_DIR: %JBOSS_DIR%

cd %BUILD_PATH%
call mvn clean install -Dmaven.test.skip=true
call copy server-core-ear\target\wayoup-server-core-ear*.ear %JBOSS_DIR%\standalone\deployments\
pause
