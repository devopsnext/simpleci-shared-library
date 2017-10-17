#!groovy
/***************************************************************************
 ***** Description :: This Package is used to deploy to remote location *****
 ***** Author      :: Mukul Garg                                        *****
 ***** Date        :: 06/01/2017                                        *****
 ***** Revision    :: 1.0                                               *****
 ***************************************************************************/

package com.sapient.devops.deploy

/*def REMOTE_USER
def REMOTE_IP
String DEPLOY_PATH,SCRIPT*/

String HOST_NAME
/*************************************
 ** Function to set the variables.
 **************************************/
void setValue()
 {
	String DEV_HOST = "root@10.202.11.199"
	String PROD_HOST_ONE = "root@10.150.6.134"
	String PROD_HOST_TWO = "root@10.150.6.135"
 }
 
/*******************************************************
 ** Function to copy the artifact to remote server
 *******************************************************/
def deploy() {
	try {
        setValue()
      	echo "DEV_HOST  : "+DEV_HOST
      	setupHost()
		copyBuildFiles()
		deployLatest()
		
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
def deployLatest() {
	try {
		println "deploy Latest...!"
		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no $HOST_NAME sh /app/deployables/setup.sh")
		
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "is not available on remote server " +error
			throw error
		}
	}
}

/*******************************************************
 ** Function to execute command on remote server
 *******************************************************/
def takeBackup() {
	try {
		println "take backup...!"
       
		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no $HOST_NAME tar -czvf /app/backup/simpleci/appex_react.tar.gz /app/appx_html/help/")
		
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to run the script on remote server " 
			throw error
		}
	}
}

/*******************************************************
 ** Function to execute command on remote server
 *******************************************************/
def copyBuildFiles() {
	try {
		println "tar _book...!"
       	echo $HOST_NAME
		sh(returnStdout: true, script: "tar -czvf  /app/ciaas/APPEX/appex_gitbook.tar.gz _book/*")
		
		sh(returnStdout: true, script: "scp -r /app/ciaas/APPEX/appex_gitbook.tar.gz  $HOST_NAME:/app/deployables/simpleci/")
		println "copying build files to dev server completed successfully...!"
      //sh(returnStdout: true, script: "ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_IP} unzip -o ${DEPLOY_PATH}/${env.BUILD_ARTIFACT}")
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to Copy artifact on remote server..."
			throw error
		}
	}
}
def setupHost(){
  if("${env.BRANCH_NAME}" != "master"){
  	  echo "brnach name in setup host : "+"${env.BRANCH_NAME}"
  }
}
