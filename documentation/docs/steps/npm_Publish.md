# npm_Publish

## Description
This step is used to upload NPM modules

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| npm_publish | yes |  |  | 'config.NPM_PUBLISH' |
| npm_repo | yes |  |  | 'config.NPM_REPO' |


* `npm_publish` defines the publish artifacts. 
* `npm_repo` defines the name of the repo to bhi published or uploaded.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `npm_publish`
* `npm_repo`

## Exceptions

None

## Example

```groovy
npm_Publish
```
