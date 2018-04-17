# maven_Publish

## Description
This step is used to deploye the code using Maven and the POM specified

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values |
| ----------|-----------|---------|-----------------|
| author_ip | yes |  |  |
| publish_ip | yes |  |  |
| artifact_name | yes |  |  |
| artifact_version | yes |  |  |
| username | yes  |  |   |
| password | yes |  |   |


* `author_ip` defines the Author instance that is used to design, create and review the content which will be published on the application in the future. 
* `publish_ip` defines the Publish instance that is used to make the designed application available to the public and is located in a demilitarized zone. The default port used by the instance is 4503.
* `artifact_name` defines the name the artifact.
* `artifact_version` defines the version number of the artifact.
* `username` specifies the server username.
* `password` defines the server password in encryted form.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `author_ip`
* `publish_ip`
* `artifact_name`
* `artifact_version`
* `username`
* `password`

## Exceptions

None

## Example

```groovy
aem_Deploy
```
