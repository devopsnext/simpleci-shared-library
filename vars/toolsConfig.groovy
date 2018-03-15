#!groovy
def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  try {
     wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[32m[INFO] configuring JAVA tool"
          if ( "${config.JAVA_HOME}" != "null" )
		  {
		      env.JAVA_HOME = "${config.JAVA_HOME}"
		  }
		  else
		  {
		      env.JAVA_HOME = tool 'jdk8'
		  }
		  
		  println "\u001B[32m[INFO] configuring MAVEN build tool"
		  if ( "${config.MAVEN_HOME}" != "null" )
		  {
		      env.MAVEN_HOME = "${config.MAVEN_HOME}"
		  }
		  else
		  {
		      env.MAVEN_HOME = tool 'maven3'
		  }
		  env.PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
          println "\u001B[35mJAVA_HOME  => ${env.JAVA_HOME}"
          println "\u001B[35mMAVEN_HOME => ${env.MAVEN_HOME}"
          println "\u001B[35mPATH       => ${env.PATH}"
		  
		  if ( "${config.NPM_SCOPE}" != "null" ) {
            withCredentials([usernameColonPassword(credentialsId: 'git-credentials', variable: 'PUBLISH')]) {
			  def URL = ""
              sh "curl -k -u${PUBLISH} ${URL} > .npmrc"
              sh "echo 'strict-ssl=false' >> .npmrc"
			}
		  }
     }
  }
  catch (Exception error) {
     wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
     }     
  }
}
