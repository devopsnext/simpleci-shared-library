#!groovy
/************************************************************
***** Description :: This Package is used for NPM Build *****
***** Author      :: Mukul Garg                         *****
***** Date        :: 04/24/2017                         *****
***** Revision    :: 2.0                                *****
************************************************************/

package com.sapient.devops.npm

String NPM_RUN

/*************************************
** Function to set the variables.
**************************************/
void setValue(String npm_run)
{
   this.NPM_RUN    =  npm_run
}

/********************************************
** Function to Build npm modules
*********************************************/
def npmTest()
{
   try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
	     println "\u001B[32m[INFO] Testing NPM modules, please wait..."
		 if ( "${NPM_RUN}" == "null" ) {
		   sh "export DISPLAY=:99; npm test"
		 }
		 else {
		   sh """${NPM_RUN}"""
		 }
		 currentBuild.result = 'SUCCESS'
		 step([$class: 'StashNotifier'])
	  }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to install NPM modules..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}