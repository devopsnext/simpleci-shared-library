# maven_SonarAnalysis

## Description
This step is used to perform SONAR analysis and publish to DOJO-SONAR

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| maven_root_pom | yes |  |  | 'config.MAVEN_ROOT_POM' |
| sonar_token      | yes |  |  | 'config.SONAR_TOKEN' |
| branch | yes |  |  |
| sonar_branch | yes |  |  |


* `maven_root_pom` defines the pom.xml for the module.
* `sonar_token` defines the sonar token.
* `branch` defines the name of the GIT branch.
* `sonar_branch` defines the sonar branch.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `maven_root_pom`
* `sonar_token`

## Exceptions

None

## Example

```groovy
maven_SonarAnalysis
```
