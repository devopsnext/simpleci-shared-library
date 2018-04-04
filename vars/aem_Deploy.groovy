#!groovy
import com.devopsnext.devops.deploy.aem

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
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
