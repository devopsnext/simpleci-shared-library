# buildTrigger

## Description
This step is used to trigger build using job id and build property. 

## Prerequsites
None

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|---------|
|job | yes | 'config.BUILD_JOB' |
| prop_build | yes | 'config.RUN_IF_FAIL' |

* `job` defines the name of the Job
* `prop_build` defines where the propogation property is set to true of false. If set to false will ensure your workflow will continue if particular job fails

## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `job`
* `prop_build`

## Exceptions

None

## Example

```groovy
buildTrigger
```
