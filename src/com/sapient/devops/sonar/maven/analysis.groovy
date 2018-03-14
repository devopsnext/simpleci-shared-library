#!groovy
/***********************************************************************
***** Description :: This Package is used for Maven Sonar Analysis *****
***** Author      :: Mukul Garg                                    *****
***** Date        :: 04/24/2017                                    *****
***** Revision    :: 2.0                                           *****
***********************************************************************/

package com.yantrashala.devops.sonar.maven

String MAVEN_ROOT_POM,SONAR_TOKEN

/**********************************************
***** Function to set the variables
***********************************************/
void setValue(String root_pom,String sonar_token)
{
   this.MAVEN_ROOT_POM  =  root_pom
   this.SONAR_TOKEN     =  sonar_token
}

/********************************************
***** Function to run the sonar analysis 
*********************************************/
def sonar()
{
   try {
      String BRANCH,SONAR_BRANCH
      
	  if ( "${MAVEN_ROOT_POM}" == "null" ) {
	    MAVEN_ROOT_POM = "pom.xml"
	  }
	  
	  if (fileExists("${MAVEN_ROOT_POM}"))
      {
         if("${SONAR_TOKEN}" != "null" && !SONAR_TOKEN.isEmpty() && !SONAR_TOKEN.trim().isEmpty())
         {
             env.SONAR_AUTH_TOKEN = "${SONAR_TOKEN}"
         }
         if ( "${env.GIT_BRANCH}" != "null" )
         {
             BRANCH = "${env.GIT_BRANCH}"
         }
         if ( "$BRANCH" == "null" )
         {
             error "\u001B[31m[ERROR] failed to proceed with Sonar analysis, unable to get Git branch..."
         }
         String[] tokens = "${BRANCH}".tokenize('/')
         count = tokens.size() - 1
         SONAR_BRANCH = tokens[count]
		 
		 String SONAR_KEY = "SimpleCi-" + getGroupId() + ":" + getArtifactId()
		 
         
		   wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] running sonar analysis with pom ${MAVEN_ROOT_POM}, please wait..."
            withSonarQubeEnv {   
              sh "mvn -f ${MAVEN_ROOT_POM} -Dsonar.projectKey=${SONAR_KEY} -DBranch=${env.GIT_BRANCH} -Dsonar.branch=${env.GIT_BRANCH} -e -B sonar:sonar"
            }
			currentBuild.result = 'SUCCESS'
		    step([$class: 'StashNotifier'])
           }	 
      }
      else
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[41m[ERROR] ${MAVEN_ROOT_POM} file does not exist..."
         }
      }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to run sonar analysis using ${MAVEN_ROOT_POM}..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}
def sonarPreview()
{
   try {
      String BRANCH,SONAR_BRANCH
      
	  if ( "${MAVEN_ROOT_POM}" == "null" ) {
	    MAVEN_ROOT_POM = "pom.xml"
	  }
	  
	  if (fileExists("${MAVEN_ROOT_POM}"))
      {
         if ( "${env.GIT_BRANCH}" != "null" )
         {
             BRANCH = "${env.GIT_BRANCH}"
         }
         if ( "$BRANCH" == "null" )
         {
             error "\u001B[31m[ERROR] failed to proceed with Sonar analysis, unable to get Git branch..."
         }
		 
		 String SONAR_KEY = "SimpleCi-" + getGroupId() + ":" + getArtifactId()
		 
		   wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] running sonar analysis with pom ${MAVEN_ROOT_POM}, please wait..."
            withSonarQubeEnv {   
			  sh "mvn -f ${MAVEN_ROOT_POM} -Dsonar.projectKey=${SONAR_KEY} -Dsonar.analysis.mode=preview -Dsonar.exclusions=**/*clickdummy*/** -Dsonar.dryRun=true -e -B sonar:sonar"
            }
		    currentBuild.result = 'SUCCESS'
		    step([$class: 'StashNotifier'])
           }	 
      }
      else
      {
         wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[41m[ERROR] ${MAVEN_ROOT_POM} file does not exist..."
         }
      }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to run sonar analysis using ${MAVEN_ROOT_POM}..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}
/********************************************
** Function to get the GroupId from POM
*********************************************/
def getGroupId()
{
   try {
      def gid = readFile("${MAVEN_ROOT_POM}") =~ '<groupId>(.+)</groupId>'
      gid ? gid[0][1] : null
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to get GroupId from ${MAVEN_ROOT_POM}..."
         throw error
      }
   }
}
/********************************************
** Function to get the ArtifactId from POM
*********************************************/
def getArtifactId()
{
   try {
      def aid = readFile("${MAVEN_ROOT_POM}") =~ '<artifactId>(.+)</artifactId>'
      aid ? aid[0][1] : null
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to get ArtifactId from ${MAVEN_ROOT_POM}..."
         throw error
      }
   }
}
