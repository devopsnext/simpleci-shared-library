#!groovy
package com.devopsnext.devops.npm

String NPM_PUBLISH, NPM_REPO, NPM_RELEASE

/*****************************************
***** Function to set the variables 
******************************************/
void setValue(String npm_publish, String npm_repo)
{
   this.NPM_PUBLISH = npm_publish
   this.NPM_REPO = npm_repo
}

/***************************************
***** Function to upload artifacts
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
/***************************************
***** Function to upload artifacts
****************************************/
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