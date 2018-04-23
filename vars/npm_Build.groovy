#!groovy
import com.devopsnext.devops.npm.npmOperations

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def npm_build = new npmOperations()
      npm_build.setValue("${config.NPM_RUN}")
      npm_build.npmBuild()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
