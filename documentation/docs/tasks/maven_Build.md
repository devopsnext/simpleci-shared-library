# maven_Build

## Description
Maven projects require a configuration file pom.xml for downloading the dependencies and project configurations. Maven goal is to build the project and create a archieve file. This task is used to perform the operation of Maven build using the goal and pom.xml

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| maven_goals | no | 'config.MAVEN_GOALS' |
| maven_root_pom | no | 'config.MAVEN_ROOT_POM' |


* `maven_goals` defines the maven goal, here is it clean install
* `maven_root_pom` defines the pom.xml for the module.

## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `maven_goals`
* `maven_root_pom`

## Exceptions

None

## Example

```groovy
maven_Build
```
