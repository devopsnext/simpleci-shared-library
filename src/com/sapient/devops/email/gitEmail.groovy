#!groovy
/**********************************************************************
***** Description :: This Package is used for Email Configuration *****
***** Author      :: Mukul Garg                                   *****
***** Date        :: 04/24/2017                                   *****
***** Revision    :: 2.0                                          *****
**********************************************************************/

package com.sapient.devops.email

def gitSendmail(String to, String from, String subject, String body)
{
   try {
    String currentResult = 'SUCCESS'
    // String previousResult = currentBuild.getPreviousBuild().result
    def causes = currentBuild.rawBuild.getCauses()
    def cause = null
    if (!causes.isEmpty()) 
    {
        cause = causes[0].getShortDescription()
    }
    causes = null
    subject = "Stage | Code Checkout | $currentResult"
    body = """
    <html>
    <body bgcolor=' #aed6f1'>
    <font face='verdana' color='black'><p><pre>
    Hi Team,

    Git checkout stage is completed successfully. Please find the Git repository details:

    GIT Repository URL  => ${env.GIT_URL}
    GIT Present Branch  => ${env.GIT_BRANCH}
    GIT Commit Hash     => ${env.GIT_COMMIT}
    Git Committer Email => ${env.GIT_AUTHOR_EMAIL}</font>


    <font face='verdana' color='red'>Note - this is an auto generated email, please don't reply to it.</font>

    <font face='verdana' color='blue'>Thanks,
     DevOps Team</font></pre></p></body></html>"""

    if (to != null && !to.isEmpty()) 
    {
        if (currentResult == 'SUCCESS') 
        {
            emailext attachLog: true, body: "${body}", mimeType: "text/html", compressLog: true, recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider'], [$class: 'FailingTestSuspectsRecipientProvider'], [$class: 'FirstFailingBuildSuspectsRecipientProvider'], [$class: 'DevelopersRecipientProvider']], subject: "${subject}", to: "${to}"
        }
        echo 'Sent email notification'
    }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper'])
      {
         println "\u001B[31m[ERROR] failed to send email...."
         throw error
      }
   }  
}
