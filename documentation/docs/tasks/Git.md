# Git

## Description
This step is used to perform Git clone operation.

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values |
| ----------|-----------|---------|-----------------|
| SCM | yes |  |  |
| branch | yes |  | 'scm.getBranches()[0]'|
| repository | yes |  | 'scm.getUserRemoteConfigs()[0].getUrl()' |

* `SCM` defines the Source Control Management, preferably GIT 
* `branch` defines the Git branch name
* `repository` defines the Git repository name


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `branch`
* `repository`

## Exceptions

None

## Example

```groovy
Git
```
