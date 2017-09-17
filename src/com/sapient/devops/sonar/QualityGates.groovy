#!groovy
/*********************************************************************
***** Description :: This Package is used for Sonar QualityGates *****
***** Author      :: Mukul Garg                                  *****
***** Date        :: 04/24/2017                                  *****
***** Revision    :: 2.0                                         *****
*********************************************************************/

package com.sapient.devops.sonar

/**************************************************************
***** Function to check the quality gates and pause pipeline
***************************************************************/
def checkQualityGates()
{
   try {
      
	   timeout(time: 1, unit: 'HOURS') {
         def qg = waitForQualityGate()
         if (qg.status != 'OK' && qg.status != 'WARN') {
            wrap([$class: 'AnsiColorBuildWrapper']) {
               error "\u001B[41m[ERROR] Pipeline aborted due to quality gate failure: ${qg.status}"
            }
         }
       }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         error "\u001B[41m[ERROR] ${error}"
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}
