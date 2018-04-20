# jobProperty

## Description
This task is used to determine the current job as Upstream or downstream.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| upstream_jobs | no | 'config.UPSTREAM_JOBS' |


* `upstream_jobs` defines as a configured project that triggers a project as of its execution.

## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `upstream_jobs`

## Exceptions

None

## Example

```groovy
jobProperty
```
