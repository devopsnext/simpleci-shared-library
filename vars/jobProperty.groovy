#!groovy
import com.devopsnext.devops.jobProperty.jobProperties

def call(body) 
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   try {
      def jobprop = new jobProperties()
	  jobprop.setValue("${config.UPSTREAM_JOBS}")
      jobprop.setProperties()
   }
   catch (Exception error)
   {
      wrap([$class: 'AnsiColorBuildWrapper']) {
         echo "\u001B[41m[ERROR] ${error}"
         throw error
      }
   }
}


