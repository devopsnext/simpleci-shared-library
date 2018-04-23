#!groovy
import com.devopsnext.devops.maven.mavenOperations

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def mvnBuild = new mavenOperations()
      mvnBuild.setValues("${config.MAVEN_GOALS}", "${config.MAVEN_ROOT_POM}")
      mvnBuild.compile()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
