#!groovy
/***************************************************************************
***** Description :: This Custom Library is used to Clean up Workspace *****
***** Author      :: Mukul Garg                                        *****
***** Date        :: 04/24/2017                                        *****
***** Revision    :: 2.0                                               *****
***************************************************************************/

def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  try {
      cleanWs cleanWhenAborted: false, cleanWhenFailure: false, cleanWhenNotBuilt: false, cleanWhenUnstable: false
  }
  catch (Exception error)
  {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
  }
}
