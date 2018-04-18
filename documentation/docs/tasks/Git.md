# Git

## Description
This is a reusable task to perform the GIT cloning operation.

The global variable scm is used to get the branch name and the repository details. The details for checkout or cloning are also fetched from enviornment variables 'env' if not found in global configurations. The GIT variables are generated i.e. the GIT hash, the commit id and code is cloned.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| branch | yes |  env var/ scm |
| repository | yes | scm |

* `scm` defines the Source Control Management, preferably GIT 
* `branch` defines the Git branch name
* `repository` defines the Git repository name


## Step configuration
The following parameters can also be derived from environment variables:

* `branch`

## Exceptions

None

## Example

```groovy
Git
```
