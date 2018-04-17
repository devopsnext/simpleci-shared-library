# buildTrigger

## Description
This step is used to trigger build using job id and build property

## Prerequsites
None

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
|job | yes |  | | 'config.BUILD_JOB' |
| prop_build | yes | true |true or false | 'config.RUN_IF_FAIL' |

* `job` defines the build number of the Job
* `prop_build` defines the build property of the job.

## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `job`
* `prop_build`

## Exceptions

None

## Example

```groovy
buildTrigger
```
