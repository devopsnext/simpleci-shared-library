#!groovy
import com.devopsnext.devops.maven.build

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def mvnBuild = new build()
      mvnBuild.setValue("${config.MAVEN_GOALS}", "${config.MAVEN_ROOT_POM}")
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
