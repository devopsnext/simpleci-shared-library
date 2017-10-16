#!groovy
/**************************************************************************
***** Description :: This Custom Library is used to Deploy AEM Author *****
***** Author      :: Mukul Garg                                       *****
***** Date        :: 04/24/2017                                       *****
***** Revision    :: 2.0                                              *****
**************************************************************************/

import com.sapient.devops.deploy.aem

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      echo "config obj : "+config
      def aemDeploy = new aem()
      aemDeploy.setValue("${config.AUTHOR_IP}", "${config.PUBLISH_IP}", "${config.ARTIFACT_NAME}", "${config.ARTIFACT_VERSION}", "${config.USERNAME}", "${config.PASSWORD}")
	  aemDeploy.validate()
      aemDeploy.deploy()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
