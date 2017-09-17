#!groovy
/**************************************************************************
***** Description :: This Custom Library is used for NPM Publish step *****
***** Author      :: Mukul Garg                                       *****
***** Date        :: 04/24/2017                                       *****
***** Revision    :: 2.0                                              *****
**************************************************************************/

import com.sapient.devops.npm.publish

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def npm_publish = new publish()
      npm_publish.setValue("${config.NPM_PUBLISH}","${config.NPM_REPO}")
	  
	  if ( "${config.NPM_PUBLISH}" != "null" ) {
	    npm_publish.publishModule()
	  }
      else {
	    npm_publish.uploadModule()
	  }
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
  finally {
    cleanWs cleanWhenAborted: false, cleanWhenFailure: false, cleanWhenNotBuilt: false, cleanWhenUnstable: false
  }
}
