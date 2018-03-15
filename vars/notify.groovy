#!groovy
import com.yantrashala.devops.email.sendmail

def call(body) 
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   try {
      
      def email = new sendmail()
      email.sendmail("${config.TO}")
   }
   catch (Exception error)
   {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
   }
}