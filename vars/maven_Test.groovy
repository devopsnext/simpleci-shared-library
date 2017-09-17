#!groovy
/**************************************************************************
***** Description :: This Custom Library is used for Maven Tests step *****
***** Author      :: Mukul Garg                                       *****
***** Date        :: 04/24/2017                                       *****
***** Revision    :: 2.0                                              *****
**************************************************************************/

import com.sapient.devops.maven.test

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      def mvnTest = new test()
      mvnTest.setValue("${config.MAVEN_ROOT_POM}")
      mvnTest.unitTest()
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
