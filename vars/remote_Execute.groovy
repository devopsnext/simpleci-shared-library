#!groovy
import com.devopsnext.devops.deploy.remote

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def remoteExe = new remote()
      remoteExe.setValue("${config.REMOTE_USER}", "${config.REMOTE_IP}", "${config.DEPLOY_PATH}", "${config.SCRIPT}")
      remoteExe.checkFile()
	  remoteExe.runCommand()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
