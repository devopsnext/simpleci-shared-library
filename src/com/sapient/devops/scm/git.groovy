#!groovy
/************************************************************
***** Description :: This Package is used for Git Clone *****
***** Author      :: Mukul Garg                         *****
***** Date        :: 04/24/2017                         *****
***** Revision    :: 2.0                                *****
************************************************************/

package com.sapient.devops.scm

String WORKSPACE = pwd()

/**************************************************
***** Function to checkout the code
***************************************************/
def gitCheckout()
{
   try {
       
	   BRANCH = scm.getBranches()[0]
       REPOSITORY = scm.getUserRemoteConfigs()[0].getUrl()
      
       if ( "${env.BRANCH_NAME}" != "null" ) {
	      BRANCH = "${env.BRANCH_NAME}"
	   }
	   if ( "$REPOSITORY" == "null" )
       {
          error "\u001B[41m[ERROR] unable to get Git repository name....."
       }
       if ( "$BRANCH" == "null" )
       {
          error "\u001B[41m[ERROR] unable to get Git Branch name...."
       } 
       wrap([$class: 'AnsiColorBuildWrapper']) {
          CREDENTIAL_ID = 'git-credentials'
          println "\u001B[32m[INFO] checking out from branch ${BRANCH}, please wait..."
          // checkout([$class: 'GitSCM', branches: [[name: "${BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', noTags: false, reference: '', shallow: true]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${CREDENTIAL_ID}", url: "${REPOSITORY}"]]])
		  checkout scm
          env.GIT_BRANCH = "${BRANCH}"
          env.GIT_URL = "$REPOSITORY"
          env.GIT_COMMIT = getGitCommitHash()
          env.GIT_AUTHOR_EMAIL = getCommitAuthorEmail()
       }
	   // currentBuild.result = 'SUCCESS'
   }
   catch (error) {
       wrap([$class: 'AnsiColorBuildWrapper']) {
          print "\u001B[41m[ERROR] clone for repository ${env.GIT_URL} failed, please check the logs..."
          throw error
       }
	   // currentBuild.result = 'FAILED'
   }
   // step([$class: 'StashNotifier'])
}

/************************************
***** Function to get the Git Hash 
*************************************/
def getGitCommitHash()
{
   try {
     gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
     shortCommit = gitCommit.take(8)
     return shortCommit
   }
   catch (Exception error)
   {
     wrap([$class: 'AnsiColorBuildWrapper']) {
          print "\u001B[41m[ERROR] failed to get last Git commit ID....."
          throw error
     }
   }
}

/**********************************************
***** Function to get the committer email id
***********************************************/
def getCommitAuthorEmail()
{
   try {
     def COMMIT = getGitCommitHash()
     sh "git log --format='%ae' $COMMIT > author"
     def author = readFile('author').trim()
     return author
   }
   catch (Exception error)
   {
     wrap([$class: 'AnsiColorBuildWrapper']) {
          print "\u001B[41m[ERROR] failed to get the last Git commit author email ID....."
          throw error
     }
   }
}
