#alias ls="ls -G"
alias cdhome="cd ~"
alias cdwinhome="cd $USER_HOME"
alias cdback='cd  $OLDPWD'

alias mvnrepoclean="rm -rf $M2_REPO/org/exoplatform/* $M2_REPO/javax/portlet/*"

alias cdtomcat="cd $EXO_WORKING_DIR/tomcat"
alias cdtomcatbin="cd $EXO_WORKING_DIR/tomcat/bin"
alias tomcatClean="cd $EXO_WORKING_DIR/tomcat/bin &&
                   rm -rf ../temp/* &&
                   rm -rf ../work/* &&
                   rm -rf ../logs/* "
   
alias tomcatRun="cd $EXO_WORKING_DIR/tomcat/bin && chmod +x *.sh && ./eXo.sh run"
alias tomcatCleanRun="cd $EXO_WORKING_DIR/tomcat/bin &&
		                  rm -rf ../temp/* &&
                      rm -rf ../work/* &&
                      rm -rf ../logs/*  &&
                      chmod +x *.sh && ./eXo.sh run "

alias openfireRun="cd $EXO_WORKING_DIR/openfire/bin && chmod +x openfire && ./openfire start"

alias exoproject="exo.sh exoproject"

alias exobuild="exo.sh exobuild"
#alias exosvn="exobsh.sh exosvn"
alias exosvn="exo.sh exosvn"
alias rmdb="rm -rf $EXO_WORKING_DIR/tomcat/temp/data"

alias jbossRun="cd $EXO_WORKING_DIR/jboss/bin && chmod +x *.sh && ./eXo.sh run"