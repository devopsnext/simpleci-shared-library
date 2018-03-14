#!groovy
/**************************************************************
***** Description :: This Package is used for Maven Build *****
***** Author      :: Mukul Garg                           *****
***** Date        :: 04/24/2017                           *****
***** Revision    :: 2.0                                  *****
**************************************************************/

package com.yantrashala.devops.maven

String MAVEN_GOALS,MAVEN_ROOT_POM

/*************************************
** Function to set the variables.
**************************************/
void setValue(String maven_goals,String root_pom)
{
   this.MAVEN_GOALS    =  maven_goals
   this.MAVEN_ROOT_POM =  root_pom
}

/*************************************
** Function to compile the code.
**************************************/
def compile()
{
   try {
      if ( "${MAVEN_ROOT_POM}" == "null" ) {
	    MAVEN_ROOT_POM = "pom.xml"
	  }
	  if ( "${MAVEN_GOALS}" == "null" ) {
	    MAVEN_GOALS = "clean install"
	  }
	  if (fileExists("${MAVEN_ROOT_POM}"))
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] compiling code from pom ${MAVEN_ROOT_POM}, please wait..."
            println "\u001B[32mPOM VERSION => " + getVersion()
            sh "mvn -f ${MAVEN_ROOT_POM} -B ${MAVEN_GOALS} -Dmaven.test.skip=true"
			currentBuild.result = 'SUCCESS'
			step([$class: 'StashNotifier'])
         }
      }
      else
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            error "\u001B[41m[ERROR] ${MAVEN_ROOT_POM} file does not exist..."
         }
      }
   }
   catch (Exception excp) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to compile code using ${MAVEN_ROOT_POM}..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw excp
      }
   }
}
/********************************************
** Function to get the version from POM
*********************************************/
def getVersion()
{
   try {
      def matcher = readFile("${MAVEN_ROOT_POM}") =~ '<version>(.+)-.*</version>'
      matcher ? matcher[0][1] : null
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to get version from ${MAVEN_ROOT_POM}..."
         throw error
      }
   }
}

