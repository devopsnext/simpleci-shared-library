#!groovy

package com.sapient.devops.deploy


String HOST_NAME
String DEV_HOST
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
void deploy(){
    setValue()
	copyPlugins()
	takePluginsBackup()
	deployLatest()
}

def copyPlugins(){
	try{
		sh(returnStdout: true, script: "tar -czvf  /app/ciaas/APPEX/appex_plugins.tar.gz -C $WORKSPACE/ .")
		if("${env.BRANCH_NAME}" != "null" && "${env.BRANCH_NAME}" != "master"){
			sh(returnStdout: true, script: "scp -r /app/ciaas/APPEX/appex_plugins.tar.gz  $DEV_HOST:/app/deployables/appex/")
			
		}
	}catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to Copy artifact on remote server..."
			throw error
		}
	}
}
def takePluginsBackup() {
	try {
		println "take backup...!"
		
		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no $DEV_HOST tar -czvf /app/backup/appex/appex_plugins.tar.gz /app/artifactory/etc/plugins/*")
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to run the script on remote server "
			throw error
		}
	}
}

def deployLatest(){
   try{
   
	if("${env.BRANCH_NAME}" != "null" && "${env.BRANCH_NAME}" != "master"){
			sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no $DEV_HOST sh /app/deployables/appex/plugins/setup.sh")
		}
   }
   catch(Exception error){
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to run the script on remote server "
			throw error
		}
   }
}