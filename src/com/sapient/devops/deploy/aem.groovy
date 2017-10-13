#!groovy
/************************************************************
***** Description :: This Package is used to deploy AEM *****
***** Author      :: Mukul Garg                         *****
***** Date        :: 06/01/2017                         *****
***** Revision    :: 1.0                                *****
************************************************************/

package com.sapient.devops.deploy

String AUTHOR_IP,PUBLISH_IP,ARTIFACT_NAME,ARTIFACT_VERSION,USERNAME,PASSWORD

/*************************************
** Function to set the variables.
**************************************/
void setValue(String author_ip,String publish_ip,String art_name,String art_version,String username,String password)
{
   this.AUTHOR_IP = author_ip
   this.PUBLISH_IP = publish_ip
   this.ARTIFACT_NAME = art_name
   this.ARTIFACT_VERSION = art_version
   this.USERNAME = username
   this.PASSWORD = password
}

/*****************************************
** Function to validate the user inputs.
*****************************************/
def validate()
{
   try {
      if ( "${AUTHOR_IP}" == "null" && "${PUBLISH_IP}" == "null" ) {
	    error "\u001B[41m[ERROR] Please mention the Author and Publish Server IPs...."
	  }
	  if ( "${ARTIFACT_NAME}" == "null" ) {
	    error "\u001B[41m[ERROR] Please mention the Artifact Name...."
	  }
	  if ( "${ARTIFACT_VERSION}" == "null" ) {
	    error "\u001B[41m[ERROR] Please mention the Artifact Version...."
	  }
	  if ( "${USERNAME}" == "null" ) {
	    error "\u001B[41m[ERROR] Please mention the Server Username...."
	  }
	  if ( "${PASSWORD}" == "null" || !PASSWORD.contains("{") || !PASSWORD.contains("}") ) {
	    error "\u001B[41m[ERROR] Please mention the Server Password in encrypted format for security..."
	  }
   }
   catch (Exception excp) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] Perameter validation failed..." 
         throw excp
      }
   }
}

/*************************************
** Function to deploy the artifact.
**************************************/
def deploy()
{
   try {
	  
	  PASSWORD = hudson.util.Secret.fromString(PASSWORD).getPlainText()
	  
	  if ( ARTIFACT_VERSION.contains('SNAPSHOT') ) {
	    println "\u001B[32m[INFO] Downloading SNAPSHOT Artifact " + ARTIFACT_NAME + " Version " + ARTIFACT_VERSION
		getSnapshotArtifact()
	  }
	  else {
	    println "\u001B[32m[INFO] Downloading Release Artifact " + ARTIFACT_NAME + " Version " + ARTIFACT_VERSION
		getReleaseArtifact()
	  }
	  // getSnapshotArtifact()
	  
	  if ( "${AUTHOR_IP}" != "null" ) {
	    deployAem(AUTHOR_IP,4502)
	  }
	  if ( "${PUBLISH_IP}" != "null" ) {
	    deployAem(PUBLISH_IP,4503)
	  }
	  
   }
   catch (Exception excp) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to deploy artifact..." 
         throw excp
      }
   }
}
/*******************************************************
** Function to download the artifact from artifactory snapshot repo
*******************************************************/
def getSnapshotArtifact() {
   def VER = null
   try {
      if ( ARTIFACT_VERSION.contains('SNAPSHOT') ) {
       def token = ARTIFACT_VERSION.split('-')
       VER = token[0]    /* either access it within scope or define a gloabl null. */
       // println VER
      }
      def server = Artifactory.server 'Appx'
      def downloadSpec = """{
 "files": [
  {
      "pattern": "maven-snapshot/*(${ARTIFACT_NAME}-${VER}*).zip",
      "target": "Deploy/",
      "flat": "true"
    }
 ]
}"""
      server.download(downloadSpec)
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to get artifact from..."
         throw error
      }
   }
}

/*******************************************************
** Function to download the artifact from artifactory release repo
*******************************************************/
def getReleaseArtifact()
{
   try {
      def server = Artifactory.server 'Appx'
	  def downloadSpec = """{
 "files": [
  {
      "pattern": "maven-release/*(${ARTIFACT_NAME}-${ARTIFACT_VERSION}*).zip",
      "target": "Deploy/",
      "flat": "true"
    }
 ]
}"""
      server.download(downloadSpec)
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to get artifact from ${server}..."
         throw error
      }
   }
}

/********************************************************
** Function to Upload and Install the artifact On Author
********************************************************/
def deployAem(String SERVER_IP, int PORT)
{
   try {
	  ARTIFACT_ZIP = sh(returnStdout: true, script: 'ls Deploy/${ARTIFACT_NAME}*.zip | tail -n 1').trim()
	  println "\u001B[32m[INFO] Deploying the Artifact " + ARTIFACT_ZIP
	  
	  sh "curl -u ${USERNAME}:${PASSWORD} -F name=${ARTIFACT_NAME} -F file=@${ARTIFACT_ZIP} http://${SERVER_IP}:${PORT}/crx/packmgr/service.jsp?cmd=upload --verbose"
	  sh "curl -u ${USERNAME}:${PASSWORD} -F file=@${ARTIFACT_ZIP} -F name=${ARTIFACT_NAME} -F force=true -F install=true http://${SERVER_IP}:${PORT}/crx/packmgr/service.jsp"
      
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to Deploy the Artifact..." + ARTIFACT_ZIP
         throw error
      }
   }
}

def deployLatest() {
	try {
		println "deploy Latest...!"

		sh(returnStdout: true, script: "ssh  -o StrictHostKeyChecking=no root@10.202.11.199 cp -r /app/deployables/simpleci/* /app/appx_html/help/")
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] file " + FILE + "  is not available on remote server " 
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
		println "copying build files...!"
		sh(returnStdout: true, script: "scp -r _book/* root@del2vmplinvcto01.sapient.com:/app/deployables/simpleci/")
		//sh(returnStdout: true, script: "ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_IP} unzip -o ${DEPLOY_PATH}/${env.BUILD_ARTIFACT}")
	}
	catch (Exception error) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR] failed to Copy artifact on remote server..."
			throw error
		}
	}
}



