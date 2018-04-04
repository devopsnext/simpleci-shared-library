#!groovy
import com.devopsnext.devops.maven.test

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def mvnTest = new test()
      mvnTest.setValue("${config.MAVEN_ROOT_POM}")
      mvnTest.unitTest()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
