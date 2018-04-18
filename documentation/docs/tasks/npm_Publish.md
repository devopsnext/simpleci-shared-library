# npm_Publish

## Description
This task is used to upload/publish npm modules based on the input command.If you then want to publish a package for the whole world to see, you can simply override the --registry option for that publish command.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| npm_publish | yes | 'config.NPM_PUBLISH' |
| npm_repo | yes | 'config.NPM_REPO' |


* `npm_publish` defines the publish artifacts. 
* `npm_repo` defines the name of the repo to be published or uploaded.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `npm_publish`
* `npm_repo`

## Exceptions

None

## Example

```groovy
npm_Publish
```
