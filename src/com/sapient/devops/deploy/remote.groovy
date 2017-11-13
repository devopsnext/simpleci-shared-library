#!groovy
/***************************************************************************
***** Description :: This Package is used to deploy to remote location *****
***** Author      :: Mukul Garg                                        *****
***** Date        :: 06/01/2017                                        *****
***** Revision    :: 1.0                                               *****
***************************************************************************/

package com.sapient.devops.deploy

def REMOTE_USER
def REMOTE_IP
String DEPLOY_PATH,SCRIPT,COPY_PATH

/*************************************
** Function to set the variables.
**************************************/
void setValue(String remote_usr,String remote_hos,String dist,String command,String path)
{
   this.REMOTE_USER = remote_usr
   this.REMOTE_IP = remote_hos
   this.DEPLOY_PATH = dist
   this.SCRIPT = command
   this.COPY_PATH = path
}

/*******************************************************
** Function to copy the artifact to remote server
*******************************************************/
def deploy()
{
   try {

	 if ( "${REMOTE_USER}" == "null" ) {
	   error "\u001B[41m[ERROR] Please mention the Remote Server Username..."
	 }
	 if ( "${REMOTE_IP}" == "null" ) {
	   error "\u001B[41m[ERROR] Please mention the Remote Host/IP...."
	 }
	 if ( "${DEPLOY_PATH}" != "null" ) {
	   copyArtifact()
	 }
	 if ( "${SCRIPT}" != "null" ) {
	   checkFile()
	   runCommand()
	 }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to remote deploy..."
         throw error
      }
   }
}

/*******************************************************
** Function to execute command on remote server
*******************************************************/
def checkFile()
{
   try {
	 def tokens = SCRIPT.split(' ')
	 def FILE = tokens[1]
	 println "\u001B[32m[INFO] Checkng the file " + FILE + " on remote server " + REMOTE_IP
	 
	 sh(returnStdout: true, script: "ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_IP} \"if [ -e ${FILE} ]; then echo \"ok\"; else exit 1; fi\"")
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] file " + FILE + "  is not available on remote server " + REMOTE_IP
         throw error
      }
   }
}

/*******************************************************
** Function to execute command on remote server
*******************************************************/
def runCommand()
{
   try {
	 println "\u001B[32m[INFO] Executing script on remote server " + REMOTE_IP
	 sh(returnStdout: true, script: "ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_IP} ${SCRIPT}")
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to run the script on remote server " + REMOTE_IP
         throw error
      }
   }
}

/*******************************************************
** Function to execute command on remote server
*******************************************************/
def copyArtifact()
{
   try {
     echo "env.BRANCH_NAME : "+env.BRANCH_NAME
      echo "env.BUILD_ARTIFACT : "+env.BUILD_ARTIFACT
     if ( "${env.WORKSPACE}" == "null" && env.WORKSPACE.isEmpty() ) {
	   error "\u001B[41m[ERROR] please mention the artifact in prepare_Archive method..."
	 }
	 
	 println "\u001B[32m[INFO] Copying the artifact ${env.WORKSPACE} on server " + REMOTE_IP
	   
	 sh(returnStdout: true, script: "scp -o StrictHostKeyChecking=no -r ${COPY_PATH}/$BUILD_ARTIFACT ${REMOTE_USER}@${REMOTE_IP}:${DEPLOY_PATH}")
     sh(returnStdout: true, script: "ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_IP} unzip -o ${DEPLOY_PATH}/${env.BUILD_ARTIFACT} -d ${DEPLOY_PATH}")
	 
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to Copy artifact on remote server..."
         throw error
      }
   }
}

