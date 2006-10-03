@echo off
REM $Id: sourceGen.bat,v 1.1 2006-10-03 19:42:17 marwahah Exp $
set JAVA=%JAVA_HOME%\bin\java
set cp=%CLASSPATH%
for %%i in (lib\*.jar) do call cp.bat %%i
set cp=%cp%;.


%JAVA% -classpath %CP% org.exolab.castor.builder.SourceGenerator %*





