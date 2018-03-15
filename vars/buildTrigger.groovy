#!groovy


def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  String JOB  = "${config.BUILD_JOB}"
  Boolean PROP_BUILD = true
  try {
    // timestamps
	 if ( "${config.RUN_IF_FAIL}" != "null" ) {
	    PROP_BUILD = config.RUN_IF_FAIL
     }
	 wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[32m[INFO] Triggering build job $JOB"
          if ( "$JOB" != "null" )
		  {
		    stage ('\u2776 Build Triggred') {
			  // build job: "$JOB", parameters: [[$class: 'GitParameterValue', name: 'CUSTOM_BRANCH', value: 'origin/development']]
			  build job: "$JOB", propagate: PROP_BUILD
			}
			
		  }
     }
  }
  catch (Exception error) {
     wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
     }     
  }
}
