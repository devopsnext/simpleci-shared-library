# jobProperty

## Description
This step is used to set the job property as UPSTREAM_JOB

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| upstream_jobs | no |  |  | 'config.UPSTREAM_JOBS' |


* `upstream_jobs` defines as a configured project that triggers a project as of its execution.

## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `upstream_jobs`

## Exceptions

None

## Example

```groovy
jobProperty
```
