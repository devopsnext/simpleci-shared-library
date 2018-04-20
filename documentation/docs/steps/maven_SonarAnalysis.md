# maven_SonarAnalysis

## Description
This task is used to perform SONAR analysis and publish to DOJO-SONAR. Also in parellel waits for SonarQube analysis to be completed and return quality gate status.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| maven_root_pom | no | 'config.MAVEN_ROOT_POM' |
| sonar_token  | yes | 'config.SONAR_TOKEN' |


* `maven_root_pom` defines the pom.xml for the module.
* `sonar_token` defines the sonar token.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `maven_root_pom`
* `sonar_token`

## Exceptions

None

## Example

```groovy
maven_SonarAnalysis
```
