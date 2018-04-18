# npm_Build

## Description
This task is used to build and run npm modules based on the input command.

## Prerequsites
none

## Parameters

| parameter | mandatory |  derived from |
| ----------|-----------|---------------|
| npm_run | no | 'config.NPM_RUN' |


* `npm_run` defines the command for the npm module to execute.



## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `npm_run`

## Exceptions

None

## Example

```groovy
npm_Build
```
