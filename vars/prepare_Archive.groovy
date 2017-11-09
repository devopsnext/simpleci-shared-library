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
        
		if ( "${config.ARTIFACT}" != "null" ) {
		  env.BUILD_ARTIFACT="${config.ARTIFACT}"
		}
		else {
		  env.BUILD_ARTIFACT=env.WORKSPACE"/_book/*"
		}
		  
		println "\u001B[32m[INFO] archiving the artifact..."
		echo "${env.BUILD_ARTIFACT}"
		
		if ( "${config.EXCLUDE}" != "null" ) {
		  sh "zip -r ${BUILD_TAG}.zip ${env.BUILD_ARTIFACT} -x ${config.EXCLUDE}\\*"
		}
		else {
		  sh "zip -r ${BUILD_TAG}.zip ${env.BUILD_ARTIFACT}"
		}
		
		archiveArtifacts artifacts: "${BUILD_TAG}.zip"
		// archiveArtifacts artifacts: "${env.BUILD_ARTIFACT}", excludes: "${config.EXCLUDE}"
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
