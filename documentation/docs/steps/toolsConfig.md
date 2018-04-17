# toolsConfig

## Description
This step is used to perform deployment at AEM server

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values |
| ----------|-----------|---------|-----------------|
| java_home | yes |  |  | 'config.JAVA_HOME' |
| maven_home | yes |  |  | 'config.MAVEN_HOME'|
| npm_scope | yes |  |  | 'config.NPM_SCOPE' |


* `java_home` defines the path of java sdk.
* `maven_home` defines the path of maven.
* `npm_scope` defines the npm scope.

## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `java_home`
* `maven_home`
* `npm_scope`

## Exceptions

None

## Example

```groovy
toolsConfig
```
