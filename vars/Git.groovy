#!groovy
import com.yantrashala.devops.scm.git
import com.yantrashala.devops.email.gitEmail

def call(body) 
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   try {
      def scm = new git()
	  // g.setValue("${config.UPSTREAM_JOBS}")
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
      Mukul Garg"""

      def gitMail = new gitEmail()
      gitMail.gitSendmail("$TO","$FROM","$SUBJECT","$BODY")
   }
}


