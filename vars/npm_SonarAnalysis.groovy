#!groovy
/**************************************************************************
***** Description :: This Custom Library is used for NPM Publish step *****
***** Author      :: Mukul Garg                                       *****
***** Date        :: 04/24/2017                                       *****
***** Revision    :: 2.0                                              *****
**************************************************************************/

import com.yantrashala.devops.sonar.npm.analysis
import com.yantrashala.devops.sonar.QualityGates

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
    if ( "${env.GIT_BRANCH}" == "development" || "${env.GIT_BRANCH}" == "master" ) {
	  def s = new analysis()
      s.setValue("${config.SONAR_PROPERTY}")
      s.sonar()
	  
	  println "\u001B[32m[INFO] Checking for the Sonar qualityGates status..."
	  
	  def qGates = new QualityGates()
      qGates.checkQualityGates()
	}
	else {
	  def npm_sonarPreview = new analysis()
      npm_sonarPreview.setValue("${config.SONAR_PROPERTY}")
      npm_sonarPreview.sonarPreview()
	  
	  println "\u001B[32m[INFO] ssonar analysis will only be published to DOJO-SONAR from master/development branch..."
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
