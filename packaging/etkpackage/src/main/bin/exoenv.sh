##################### Working Environment ##########################################
#PORTABLE_DIR=/home/philippe

##################### VARIABLE TO CUSTOMIZE ########################################
#PORTABLE_DIR=`echo $PORTABLE_DIR | sed -e 's/\\\/\\//g'`
#PORTABLE_DIR=`echo $PORTABLE_DIR | sed -e 's/\\/$//g'`

PORTABLE_DIR=$PWD

JAVA_DIR=$PORTABLE_DIR

#cd $JAVA_DIR
EXO_BASE_DIRECTORY=$PWD

#cd $OLDPWD
JAVA_HOME=$EXO_BASE_DIRECTORY/jdk1.5

#BSH_EXO_BASE_DIRECTORY=$JAVA_DIR
#BSH_JAVA_HOME=$JAVA_HOME
M2_REMOTE_REPOS="http://vnserver.exoplatform.org/maven2, http://192.168.1.10:9999/repository/"

##################################################################################
USER_HOME='/cygdrive/c/Documents\ and\ Settings/$USERNAME'

EXO_PROJECTS_SRC=$EXO_BASE_DIRECTORY/eXoProjects
EXO_SH_SCRIPT=$EXO_PROJECTS_SRC/tools/trunk/build/src/main/resources/linux
EXO_WORKING_DIR=$EXO_BASE_DIRECTORY/exo-working
EXO_DEPENDENCIES_DIR=$EXO_BASE_DIRECTORY/exo-dependencies

#CLEAN_SERVER=tomcat-6.0.16-cuphi-build
CLEAN_SERVER=tomcat-6.0.10
#CLEAN_SERVER=jboss-4.0.5.GA
#CLEAN_SERVER=JONAS_4_8_6

#FLEX_HOME=/home/philippe/java/flex_sdk_3

M2_HOME=$EXO_BASE_DIRECTORY/maven2
M2_REPO=$EXO_BASE_DIRECTORY/exo-dependencies/repository

# MAVEN_OPTS will be used as JVM options for the build by 'exobuild' command
MAVEN_OPTS="-Xshare:auto -Xms128m -Xmx512m -XX:MaxPermSize=256M" 

# JAVA_OPTS will be used by tomcat
JAVA_OPTS="-Xshare:auto -Xms256m -Xmx1024m -XX:MaxPermSize=256M -Dexo.directory.base=$EXO_BASE_DIRECTORY"

PATH=/usr/local/bin:$JAVA_HOME/bin:$M2_HOME/bin:$EXO_SH_SCRIPT:$PATH

export PATH
#export FLEX_HOME
export JAVA_OPTS JAVA_HOME M2_HOME M2_REPO MAVEN_OPTS CLEAN_SERVER M2_REMOTE_REPOS
#export EXO_BASE_DIRECTORY EXO_PROJECTS_SRC  BSH_EXO_BASE_DIRECTORY  BSH_M2_REPOS BSH_JAVA_HOME
export EXO_BASE_DIRECTORY EXO_PROJECTS_SRC
##################################################################################
# allways put and do not edit these following lines at the end this file 
################################################################################## 
if [ -e "$EXO_PROJECTS_SRC/tools/trunk/config/maven2/template-settings.xml" ] ; then
  JAVA_DIR_SUB=`echo $JAVA_DIR | sed -e 's/\\//\\\\\//g'`
  eval "sed -e 's/@java.dir@/$JAVA_DIR_SUB/g' $EXO_PROJECTS_SRC/tools/trunk/config/maven2/template-settings.xml > $EXO_BASE_DIRECTORY/maven2/conf/settings.xml"
fi

if [ -e "${PORTABLE_DIR}/tools/env.sh" ] ; then
  source "${PORTABLE_DIR}/tools/env.sh"
fi

if [ -e "${EXO_SH_SCRIPT}/exoscript.sh" ] ; then
  source "${EXO_SH_SCRIPT}/exoscript.sh"
fi
