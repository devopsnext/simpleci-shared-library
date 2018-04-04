#!groovy
import com.devopsnext.devops.npm.test

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def npmtest = new test()
      npmtest.setValue("${config.NPM_RUN}")
      npmtest.npmTest()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
