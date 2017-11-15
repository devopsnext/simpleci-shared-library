#!groovy
/*************************************************************************
***** Description :: This Custom Library is used to Archive Artifact *****
***** Author      :: Mukul Garg                                      *****
***** Date        :: 04/24/2017                                      *****
***** Revision    :: 2.0                                             *****
*************************************************************************/

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  try {
     wrap([$class: 'AnsiColorBuildWrapper']) {
        
		if ( "${config.ARTIFACT_PATH}" == "null" ) {
			 error "\u001B[41m[ERROR] Please mention Source to archive..."
		}
		
		println "\u001B[32m[INFO] archiving the artifact..."
		 echo "${config.ARTIFACT_PATH}"
		
       if ( "${config.EXCLUDE}" != "null" ) {
		  sh "cd ${env.WORKSPACE} zip -r ${BUILD_TAG}.zip ${config.ARTIFACT_PATH} -x ${config.EXCLUDE}\\*"
		}
		else {
          sh "cd ${env.WORKSPACE} ; zip -r ${BUILD_TAG}.zip ${config.ARTIFACT_PATH}"
		}		
		env.BUILD_ARTIFACT = "${BUILD_TAG}.zip"

     }
  }
  catch (Exception error) {
     wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
     }     
  }
}
