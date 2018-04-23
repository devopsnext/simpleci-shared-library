#!groovy
import com.devopsnext.devops.maven.mavenOperations

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
    if ( "${env.GIT_BRANCH}" == "development" || "${env.GIT_BRANCH}" == "master" ) {
	  def mvnPublish = new mavenOperations()
      mvnPublish.setValue("${config.MAVEN_ROOT_POM}")
      mvnPublish.uploadArtifacts()
	}
	else {
	  println "\u001B[32m[INFO] uploading artifact allowed only from master/development branch..."
	}
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
