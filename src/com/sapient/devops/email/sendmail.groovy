#!groovy
package com.yantrashala.devops.email

def sendmail(String to)
{
   try {
    String currentResult = 'SUCCESS'
    def causes = currentBuild.rawBuild.getCauses()
    def cause = null
    if (!causes.isEmpty()) 
    {
        cause = causes[0].getShortDescription()
    }
    causes = null
    subject = "${env.JOB_NAME} | $currentResult"
    body = """
    <html>
    <body bgcolor=' #aed6f1'>
    <font face='verdana' color='black'><p><pre>
    Hi Team,

    <p>Job '${env.JOB_NAME}' has a '${currentBuild.currentResult}' in BuildNumber :- '${env.BUILD_NUMBER}'</p>
	<p>Full logs are present at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
	  
    <font face='verdana' color='red'>Note - this is an auto generated email, please don't reply to it.</font>

    <font face='verdana' color='blue'>Thanks,
    SimpleCI Team</font></pre></p></body></html>"""

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
