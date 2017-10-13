#!groovy
/********************************************************************************
***** Description :: This Custom Library is used to Deploy to Remote Server *****
***** Author      :: Mukul Garg                                             *****
***** Date        :: 04/24/2017                                             *****
***** Revision    :: 2.0                                                    *****
********************************************************************************/

import com.sapient.devops.deploy.aem

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def remoteappex = new aem()
    	echo "-----------appexDeploy------------"
    	echo "appex deploy : "+appex
        echo "-----------appexDeploy------------"
    	remoteappex.takeBackup()
    	remoteappex.copyBuildFiles()
    	remoteappex.deployLatest()	
    	
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
