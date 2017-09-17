#!groovy
/******************************************************************************************
***** Description :: This Custom Library is used to Execute commands on Remote Server *****
***** Author      :: Mukul Garg                                                       *****
***** Date        :: 04/24/2017                                                       *****
***** Revision    :: 2.0                                                              *****
******************************************************************************************/

import com.sapient.devops.deploy.remote

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def remoteExe = new remote()
      remoteExe.setValue("${config.REMOTE_USER}", "${config.REMOTE_IP}", "${config.DEPLOY_PATH}", "${config.SCRIPT}")
      remoteExe.checkFile()
	  remoteExe.runCommand()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
