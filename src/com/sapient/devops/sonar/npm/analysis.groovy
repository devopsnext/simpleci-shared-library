#!groovy
/*********************************************************************
***** Description :: This Package is used for NPM Sonar Analysis *****
***** Author      :: Mukul Garg                                  *****
***** Date        :: 04/24/2017                                  *****
***** Revision    :: 2.0                                         *****
*********************************************************************/

package com.sapient.devops.sonar.npm

String SONAR_PROPERTY

/**********************************************
***** Function to set the variables
***********************************************/
void setValue(String sonar_property)
{
   this.SONAR_PROPERTY     =  sonar_property
}

/********************************************
***** Function to run the sonar analysis 
*********************************************/
def sonar()
{
   env.SONAR_RUNNER_HOME = tool name: 'sonar-scanner-2.8', type: 'hudson.plugins.sonar.SonarRunnerInstallation'

   try {
      
      if ( "${SONAR_PROPERTY}" == "null" ) {
	    SONAR_PROPERTY = "sonar-runner.properties"
	  }
	  if (fileExists("${SONAR_PROPERTY}"))
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] running sonar analysis with file ${SONAR_PROPERTY}, please wait..."
            withSonarQubeEnv {
               sh "${env.SONAR_RUNNER_HOME}/bin/sonar-runner -Dproject.settings=${SONAR_PROPERTY}"
            }
			currentBuild.result = 'SUCCESS'
		    step([$class: 'StashNotifier'])
         }
      }
      else
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[41m[ERROR] ${SONAR_PROPERTY} file does not exist..."
         }
      }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to run sonar analysis using ${SONAR_PROPERTY}..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}

def sonarPreview()
{
   env.SONAR_RUNNER_HOME = tool name: 'sonar-scanner-2.8', type: 'hudson.plugins.sonar.SonarRunnerInstallation'

   try {
      
      if ( "${SONAR_PROPERTY}" == "null" ) {
	    SONAR_PROPERTY = "sonar-runner.properties"
	  }
	  if (fileExists("${SONAR_PROPERTY}"))
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] running sonar analysis with file ${SONAR_PROPERTY}, please wait..."
            withSonarQubeEnv {
               sh "${env.SONAR_RUNNER_HOME}/bin/sonar-runner -Dsonar.analysis.mode=preview -Dsonar.dryRun=true -Dproject.settings=${SONAR_PROPERTY}"
            }
			currentBuild.result = 'SUCCESS'
		    step([$class: 'StashNotifier'])
         }
      }
      else
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[41m[ERROR] ${SONAR_PROPERTY} file does not exist..."
         }
      }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to run sonar analysis using ${SONAR_PROPERTY}..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}