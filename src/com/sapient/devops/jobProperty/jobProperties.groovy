#!groovy
/*****************************************************************
***** Description :: This Package is used for Job Properties *****
***** Author      :: Mukul Garg                              *****
***** Date        :: 04/24/2017                              *****
***** Revision    :: 2.0                                     *****
*****************************************************************/

package com.yantrashala.devops.jobProperty

String WORKSPACE = pwd()
String UPSTREAM_JOBS

/*************************************
** Function to set the variables.
**************************************/
void setValue(String upstreamJobs)
{
   this.UPSTREAM_JOBS    =  upstreamJobs
}

/**************************************************
***** Function to set the property
***************************************************/
def setProperties()
{
   try {
	  /***
	  properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '2', numToKeepStr: '2')), 
	              parameters([[$class: 'GitParameterDefinition', branch: '', branchFilter: '.*', defaultValue: '', description: '', name: 'CUSTOM_BRANCH', quickFilterEnabled: false, selectedValue: 'NONE', sortMode: 'ASCENDING', tagFilter: '*', type: 'PT_BRANCH_TAG']]),
	              pipelineTriggers([upstream(threshold: hudson.model.Result.SUCCESS, upstreamProjects: "${UPSTREAM_JOBS}")])])
***/

      properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '2', numToKeepStr: '2')), 
	              pipelineTriggers([upstream(threshold: hudson.model.Result.SUCCESS, upstreamProjects: "${UPSTREAM_JOBS}"), pollSCM('H/10 * * * *')])])
   }
   catch (error) {
       wrap([$class: 'AnsiColorBuildWrapper']) {
          print "\u001B[41m[ERROR] clone for repository ${env.GIT_URL} failed, please check the logs..."
          throw error
       }
   }
}