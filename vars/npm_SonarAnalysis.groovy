#!groovy
import com.devopsnext.devops.sonar.npm.npmSonarOp
import com.devopsnext.devops.sonar.QualityGates

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
    if ( "${env.GIT_BRANCH}" == "development" || "${env.GIT_BRANCH}" == "master" ) {
	  def npm_sonar = new npmSonarOp()
      npm_sonar.setValue("${config.SONAR_PROPERTY}")
      npm_sonar.sonar()
	  
	  println "\u001B[32m[INFO] Checking for the Sonar qualityGates status..."
	  
	  def qGates = new QualityGates()
      qGates.checkQualityGates()
	}
	else {
	  def npm_sonarPreview = new npmSonarOp()
      npm_sonarPreview.setValue("${config.SONAR_PROPERTY}")
      npm_sonarPreview.sonarPreview()
	  
	  println "\u001B[32m[INFO] sonar analysis will only be published to DOJO-SONAR from master/development branch..."
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
