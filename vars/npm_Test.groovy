#!groovy
/************************************************************************
***** Description :: This Custom Library is used for NPM Tests step *****
***** Author      :: Mukul Garg                                     *****
***** Date        :: 04/24/2017                                     *****
***** Revision    :: 2.0                                            *****
************************************************************************/

import com.yantrashala.devops.npm.test

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
