# maven_Publish

## Description
Maven projects require a configuration file pom.xml for downloading the dependencies and project configurations. Maven goal is to clean and deploy the project on the server. This task is used to perform the operation of maven deploy using the goal and pom.xml

## Prerequsites
none

## Parameters

| parameter | mandatory |  derived from |
| ----------|-----------|---------------|
| maven_root_pom | yes | 'config.MAVEN_ROOT_POM' |


* `maven_root_pom` defines the pom.xml for the module.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `maven_root_pom`

## Exceptions

None

## Example

```groovy
maven_Publish
```
