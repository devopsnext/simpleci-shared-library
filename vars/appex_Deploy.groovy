#!groovy
/********************************************************************************
***** Description :: This Custom Library is used to Deploy to Remote Server *****
***** Author      :: Mukul Garg                                             *****
***** Date        :: 04/24/2017                                             *****
***** Revision    :: 2.0                                                    *****
********************************************************************************/

import com.sapient.devops.deploy.appex
String branchName
appex_Deploy(String branch){
  this.branchName = branch
}
def call(body) {
  echo "body : "+body
  def config = [:]
  echo "config obj : "+config

  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def remoteappex = new appex()
    	echo "branch : "+branchName
        echo "-----------appexDeploy------------"
    	remoteappex.deploy()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
