# npm_SonarAnalysis

## Description
This task is used to perform SONAR analysis and publish to DOJO-SONAR. Also in parellel waits for SonarQube analysis to be completed and return quality gate status.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| sonar_property | yes | 'config.SONAR_PROPERTY' |


* `sonar_property` defines the properties of sonar for analysis in the form of properties file.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `sonar_property`


## Exceptions

None

## Example

```groovy
npm_SonarAnalysis

```
