# maven_Publish

## Description
This step is used to deploy the code using Maven and the POM specified

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| maven_root_pom | yes |  |  | 'config.MAVEN_ROOT_POM' |


* `maven_root_pom` defines the pom.xml for the module.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `maven_root_pom`

## Exceptions

None

## Example

```groovy
maven_Publish
```
