#!groovy
import com.devopsnext.devops.npm.npmOperations

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def npm_test = new npmOperations()
      npm_test.setValue("${config.NPM_RUN}")
      npm_test.npmTest()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
