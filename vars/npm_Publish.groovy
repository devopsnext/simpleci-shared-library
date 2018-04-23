#!groovy
import com.devopsnext.devops.npm.npmOperations

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def npm_publish = new npmOperations()
      npm_publish.setValues("${config.NPM_PUBLISH}","${config.NPM_REPO}")
	  
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
