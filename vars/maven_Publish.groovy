#!groovy
/****************************************************************************
***** Description :: This Custom Library is used for Maven Publish step *****
***** Author      :: Mukul Garg                                         *****
***** Date        :: 04/24/2017                                         *****
***** Revision    :: 2.0                                                *****
****************************************************************************/

import com.sapient.devops.maven.publish

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
    if ( "${env.GIT_BRANCH}" == "development" || "${env.GIT_BRANCH}" == "master" ) {
	  def mvnPublish = new publish()
      mvnPublish.setValue("${config.MAVEN_ROOT_POM}")
      mvnPublish.uploadArtifacts()
	}
	else {
	  println "\u001B[32m[INFO] uploading artifact allowed only from master/development branch..."
	}
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
