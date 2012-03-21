@echo off

@REM ==== ENV VALIDATION ====
if not "%EXOPACKAGE_HOME%" == "" goto OkExoHome
echo.
echo ERROR: EXOPACKAGE_HOME not found in your environment.
echo Please set the EXOPACKAGE_HOME variable in your environment to match the
echo location of your eXo BaseDirectory installation
echo.
goto error

:OkExoHome
if not "%EXO_BASE_DIRECTORY%" == "" goto OkExoBaseHome
echo.
echo ERROR: EXO_BASE_DIRECTORY not found in your environment.
echo Please set the EXO_BASE_DIRECTORY variable in your environment to match the
echo location of your eXo BaseDirectory installation
echo.
goto error

:OkExoBaseHome
if not "%JAVA_HOME%" == "" goto OkJHome
echo.
echo ERROR: JAVA_HOME not found in your environment.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto OkEnv
echo.
echo ERROR: JAVA_HOME is set to an invalid directory.
echo JAVA_HOME = "%JAVA_HOME%"
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto error

:OkEnv
if not "%M2_REPOS%" == "" goto OkM2Repo
echo.
echo ERROR: M2_REPOS not found in your environment.
echo Please set the M2_REPOS variable in your environment to match the
echo location of your Local Maven repository
echo.
goto error

:OkM2Repo

REM TODO Check M2_HOME exists
REM set M2_HOME=%EXO_BASE_DIRECTORY%\maven2
set M2_REPOS=file:%EXO_DEPENDENCIES_DIR%/repository

REM add http://maven2.exoplatform.org/rest/maven2, http://maven2.exoplatform.org/rest/maven2
REM  TODO Check CLEAN_SERVER exists
REM  TODO CLEAN_SERVER should be replaced by TOMCAT_6_HOME, JBOSS_4_2_0_HOME, ...
REM set CLEAN_SERVER=tomcat-6.0.10


set EXO_WORKING_DIR=%EXO_BASE_DIRECTORY%\exo-working
set EXO_PROJECTS_SRC=%EXO_BASE_DIRECTORY%\eXoProjects
set EXO_DEPENDENCIES_DIR=%EXO_BASE_DIRECTORY%\exo-dependencies

set CURRENT_DIR=%cd%

set EXO_OPTS=
REM set EXO_OPTS=-Dexo.java.home=%JAVA_HOME%
set EXO_OPTS=%EXO_OPTS% -Dexo.package.home=%EXOPACKAGE_HOME%
set EXO_OPTS=%EXO_OPTS% -Dexo.current.dir=%CURRENT_DIR%
set EXO_OPTS=%EXO_OPTS% -Dexo.base.dir=%EXO_BASE_DIRECTORY% 
set EXO_OPTS=%EXO_OPTS% -Dexo.working.dir=%EXO_WORKING_DIR%
set EXO_OPTS=%EXO_OPTS% -Dexo.src.dir=%EXO_PROJECTS_SRC%
set EXO_OPTS=%EXO_OPTS% -Dexo.dep.dir=%EXO_DEPENDENCIES_DIR%
set EXO_OPTS=%EXO_OPTS% -Dexo.conf.dir=%EXOPACKAGE_CONF_DIR%
set EXO_OPTS=%EXO_OPTS% -Dexo.m2.repos=%M2_REPOS%
REM TODO Cleanup
set EXO_OPTS=%EXO_OPTS% -Dclean.server=%CLEAN_SERVER%
set EXO_OPTS=%EXO_OPTS% -Dexo.m2.home=%M2_HOME%
  
set JAVA_OPTS=-Xshare:auto -Xms128m -Xmx512m
java %JAVA_OPTS% -classpath %EXOPACKAGE_HOME%\lib\js.jar %EXO_OPTS% org.mozilla.javascript.tools.shell.Main %EXOPACKAGE_HOME%\javascript\eXo\eXo.js %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%
cmd /C exit /B %ERROR_CODE%