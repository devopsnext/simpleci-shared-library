# npm_Build

## Description
This step is used to build NPM modules

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| npm_run | no |  |  | 'config.NPM_RUN' |


* `npm_run` defines the command for the npm module to execute.



## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `npm_run`

## Exceptions

None

## Example

```groovy
npm_Build
```
