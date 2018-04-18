# maven_Build

## Description
This step is used to compile the code using maven and the Pom specified

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| maven_goals | yes |  |  | 'config.MAVEN_GOALS' |
| maven_root_pom | yes |  |  | 'config.MAVEN_ROOT_POM' |


* `maven_goals` defines the maven goal, here is it clean install
* `maven_root_pom` defines the pom.xml for the module.

## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `maven_goals`
* `maven_root_pom`

## Exceptions

None

## Example

```groovy
maven_Build
```
