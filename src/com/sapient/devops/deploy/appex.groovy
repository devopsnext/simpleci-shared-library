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

/*************************************
 ** Function to set the variables.
 **************************************/
/*void setValue(String remote_usr,String remote_hos,String dist,String command)
 {
 this.REMOTE_USER = remote_usr
 this.REMOTE_IP = remote_hos
 this.DEPLOY_PATH = dist
 this.SCRIPT = command
 }
 */
/*******************************************************
 ** Function to copy the artifact to remote server
 *******************************************************/
def deploy() {
	try {
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
		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no root@10.202.11.199 sh /app/deployables/setup.sh")
		
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
		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no root@10.202.11.199 tar -czvf /app/backup/simpleci/appex_react.tar.gz /app/appx_html/help/")
		
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
		sh(returnStdout: true, script: "tar -czvf  /app/ciaas/APPEX/appex_gitbook.tar.gz _book/*")
		
		sh(returnStdout: true, script: "scp -r /app/ciaas/APPEX/appex_gitbook.tar.gz  root@del2vmplinvcto01.sapient.com:/app/deployables/simpleci/")
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
