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


String DEV_HOST = "root@10.202.11.199"
String PROD_HOST_ONE
String PROD_HOST_TWO

/*************************************
 ** Function to set the variables.
 **************************************/
void setValue() {
	this.DEV_HOST      = "root@10.202.11.199"
	this.PROD_HOST_ONE = "root@10.150.6.134"
	this.PROD_HOST_TWO = "root@10.150.6.135"
}

/*******************************************************
 ** Function to copy the artifact to remote server
 *******************************************************/
def deploy() {
	try {
		//setValue()
		takeBackup()
		
		
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
def takeBackup() {
	try {
		println "take backup...!"

		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no $HOST_NAME tar -czvf /app/backup/appex/appex_nginx.tar.gz /etc/nginx/sites-enabled/")
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
		
			sh(returnStdout: true, script: "scp -r $WORKSPACE/artifactory.conf  $DEV_HOST:/etc/nginx/sites-enabled")
			
		
	}

	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to Copy artifact on remote server..."
			throw error
		}
	}
}

