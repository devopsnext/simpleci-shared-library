#!groovy
/****************************************************************
***** Description :: This Package is used for Maven Publish *****
***** Author      :: Mukul Garg                             *****
***** Date        :: 04/24/2017                             *****
***** Revision    :: 2.0                                    *****
****************************************************************/

package com.yantrashala.devops.maven

String MAVEN_ROOT_POM

/*****************************************
***** Function to set the variables 
******************************************/
void setValue(String root_pom)
{
   this.MAVEN_ROOT_POM = root_pom
}

/***************************************
***** Function to upload artifacts
****************************************/
def uploadArtifacts()
{
   try
   {
       if ( "${MAVEN_ROOT_POM}" == "null" ) {
	    MAVEN_ROOT_POM = "pom.xml"
	   }
	   
	    wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[32m[INFO] uploading artifact to artifact management tool, please wait..."
          sh "mvn -f ${MAVEN_ROOT_POM} -e -B clean deploy"
		  currentBuild.result = 'SUCCESS'
		  step([$class: 'StashNotifier'])
        }       
   }
   catch (error) {
       wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[41m[ERROR] failed to upload artifact on artifact management tool, please check logs..."
		  currentBuild.result = 'FAILED'
		  step([$class: 'StashNotifier'])
          throw error
       }
   }
}
