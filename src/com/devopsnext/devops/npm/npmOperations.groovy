#!groovy
package com.devopsnext.devops.npm

String NPM_RUN

/*************************************
** Function to set the variables.
**************************************/
void setValue(String npm_run)
{
   this.NPM_RUN    =  npm_run
}

/*****************************************
***** Function to set the variables 
******************************************/
void setValues(String npm_publish, String npm_repo)
{
   this.NPM_PUBLISH = npm_publish
   this.NPM_REPO = npm_repo
}

/********************************************
** Function to Build npm modules
*********************************************/
def npmBuild()
{
   try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
	     println "\u001B[32m[INFO] Building NPM modules, please wait..."
		 if ( "${NPM_RUN}" == "null" ) {
           sh "npm install"
		   sh "npm run build"
		 }
		 else {
		   sh """${NPM_RUN}"""
		 }
		 currentBuild.result = 'SUCCESS'
		 step([$class: 'StashNotifier'])
	  }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to install NPM modules..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}

/********************************************
** Function to test npm modules
*********************************************/
def npmTest()
{
   try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
	     println "\u001B[32m[INFO] Testing NPM modules, please wait..."
		 if ( "${NPM_RUN}" == "null" ) {
		   sh "export DISPLAY=:99; npm test"
		 }
		 else {
		   sh """${NPM_RUN}"""
		 }
		 currentBuild.result = 'SUCCESS'
		 step([$class: 'StashNotifier'])
	  }
   }
   catch (Exception error) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] failed to install NPM modules..."
		 currentBuild.result = 'FAILED'
		 step([$class: 'StashNotifier'])
         throw error
      }
   }
}

/***************************************
***** Function to publish artifacts
****************************************/
def publishModule()
{
   try
   {
	   wrap([$class: 'AnsiColorBuildWrapper']) {
	      println "\u001B[32m[INFO] uploading modules to artifact management tool, please wait..."
          sh """${NPM_PUBLISH}"""
		  currentBuild.result = 'SUCCESS'
		  step([$class: 'StashNotifier'])
       }
   }
   catch (error)
   {
       wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[41m[ERROR] failed to upload artifact on artifact management tool, please check logs..."
		  currentBuild.result = 'FAILED'
		  step([$class: 'StashNotifier'])
          throw error
       }
   }
}
/********************************************
***** Function to publish globally artifacts
*********************************************/
def uploadModule()
{
   def NPM_RELEASE = ""
   
   try
   {
	   wrap([$class: 'AnsiColorBuildWrapper'])
       {
		  if ( "${env.GIT_BRANCH}" == "master" || "${env.GIT_BRANCH}" == "development" ) {
		    println "\u001B[32m[INFO] uploading modules to artifact management tool ${NPM_RELEASE}, please wait..."
            sh "npm publish --registry ${NPM_RELEASE}"
		  }
		  else {
		    println "\u001B[32m[INFO] upload modules will only be allowed from master/development branch..."
		  }
          currentBuild.result = 'SUCCESS'
		  step([$class: 'StashNotifier'])
       }
   }
   catch (error)
   {
       wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[41m[ERROR] failed to upload artifact on artifact management tool, please check logs..."
		  currentBuild.result = 'FAILED'
		  step([$class: 'StashNotifier'])
          throw error
       }
   }
}
