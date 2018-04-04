#!groovy
import com.devopsnext.devops.sonar.maven.analysis
import com.devopsnext.devops.sonar.QualityGates

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
    if ( "${env.GIT_BRANCH}" == "development" || "${env.GIT_BRANCH}" == "master" ) {
	  def mvn_sonar = new analysis()
      mvn_sonar.setValue("${config.MAVEN_ROOT_POM}", "${config.SONAR_TOKEN}")
      mvn_sonar.sonar()
	  
	  println "\u001B[32m[INFO] Checking for the Sonar qualityGates status..."
	  
	  def qGates = new QualityGates()
      qGates.checkQualityGates()
	}
	else {
	  def mvn_sonarPreview = new analysis()
      mvn_sonarPreview.setValue("${config.MAVEN_ROOT_POM}", "${config.SONAR_TOKEN}")
      mvn_sonarPreview.sonarPreview()
	  
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
