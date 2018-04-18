#!groovy
import com.devopsnext.devops.scm.git
import com.devopsnext.devops.email.gitEmail

def call(body) 
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   try {
      def scm = new git()
      scm.gitCheckout()
   }
   catch (Exception error)
   {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
   }
   finally {
      def TO = ''
      def FROM = ''
      def SUBJECT = "Stage | Code Checkout | ${currentBuild.result}"
      def BODY = """
      Hi,
       
        Code checkout stage is in progress...

      Regards,
      AAA"""

      def gitMail = new gitEmail()
      gitMail.gitSendmail("$TO","$FROM","$SUBJECT","$BODY")
   }
}


