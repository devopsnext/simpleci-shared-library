#!groovy


def call(body)
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         script {
            sh 'env > env.txt'
            String[] envs = readFile('env.txt').split("\r?\n")
            for(String vars: envs){
               println "\u001B[34m ${vars}"
            }
         }
      }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[41m[ERROR] $error"
        throw error
      }
   }
}
