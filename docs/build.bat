REM Turn echo off and clear the screen.
@echo off
cls

REM Good batch file coding practice.
setlocal enabledelayedexpansion

REM Paths for Java
SET JAVA_HOME="C:\Program Files\Java\jdk-17.0.3\bin"
set PATH=%JAVA_HOME%; %PATH%

REM Paths for JavaFX
set USE_JAVAFX=true
set JAVAFX_HOME="C:\Program Files\Java\javafx-sdk-17.0.2\lib"
set JAVAFX_MODULES="javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media"
set JAVAFX_ARGS=

if %USE_JAVAFX%==true (set JAVAFX_ARGS=--module-path %JAVAFX_HOME% --add-modules=%JAVAFX_MODULES%)

REM Move to correct folder.
cd ..

REM Variables for batch
set ERRMSG=
set BIN=.\bin
set DOCS=.\docs
set LIB=.\lib\*
set SRC=.\src


REM Clean all class files from bin folder and the JavaDocs folder from docs folder.
:CLEAN
echo ------CLEANING PROJECT------
@REM DEL /S %BIN%/*.class
@REM DEL /S %BIN%
RMDIR /Q /S %DOCS%\JavaDocs
RMDIR /Q /S %BIN%

IF /I "%ERRORLEVEL%" NEQ "0" (
    echo !!! Error Cleaning Project !!!
)

cls

REM Compile project by compiling just Main. Main will reference required classes.
:COMPILE
@REM echo.
cls
@REM echo ------COMPILING PROGRAM------
javac %JAVAFX_ARGS% -sourcepath %SRC% -classpath %BIN%;%LIB% -d %BIN% %SRC%\Main.java

IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG=!!! Error compiling project !!!
    GOTO ERROR
)
@REM if no error occurs
IF /I "%ERRORLEVEL%" EQU "0" (
    @REM echo ----COMPILATION SUCCESSFUL----
    @REM echo.
)

@REM REM Generate JavaDoc for project for only acsse subpackage.
@REM :JAVADOCS
@REM echo ------GENERATING JAVADOCS------
@REM echo.
@REM javadoc %JAVAFX_ARGS% -sourcepath %SRC% -classpath %BIN%;%LIB% -use -version -author -d %DOCS%\JavaDocs -subpackages csc3a

@REM IF /I "%ERRORLEVEL%" NEQ "0" (
@REM     echo !!! Error Generating JavaDoc for Project !!!
@REM )

REM Run project by running Main.
:RUN
@REM echo.
echo -------RUNNING PROGRAM-------
@REM echo.
java %JAVAFX_ARGS% -Xmx4g -classpath %BIN%;%LIB%  Main

@REM if an error occurs 
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG=!!! Error Running Project !!!
    GOTO ERROR
)
GOTO END

REM Something went wrong, display error.
:ERROR
echo !!! Fatal Error with Project !!!
echo %ERRMSG%

REM Move back to docs folder and wait.
:END
echo ------PROGRAM TERMINATED------
@REM cd %DOCS%
PAUSE
