# aem_Deploy

## Description
This step is used to perform deployment at AEM server

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values |
| ----------|-----------|---------|-----------------|
| author_ip | yes |  |  |
| publish_ip | yes |  |  |
| artifact_name | yes |  |  |
| artifact_version | yes |  |  |
| username | yes  |  |   |
| password | yes |  |   |


* `author_ip` defines the global script environment of the Jenkinsfile run. Typically `this` is passed to this parameter. This allows the function to access the [`commonPipelineEnvironment`](commonPipelineEnvironment.md) for retrieving e.g. configuration parameters.
* `publish_ip` defines the type of the artifact.
* `artifact_name` defines the tool which is used for building the artifact.
* `artifact_version` controls if the changed version is committed and pushed to the git repository. If this is enabled (which is the default), you need to provide `gitCredentialsId` and `gitSshUrl`.
* `username` specifies the source to be used for the main version which is used for generating the automatic version.

    * This can either be the version of the base image - as retrieved from the `FROM` statement within the Dockerfile, e.g. `FROM jenkins:2.46.2`
    * Alternatively the name of an environment variable defined in the Docker image can be used which contains the version number, e.g. `ENV MY_VERSION 1.2.3`
    * The third option `appVersion` applies only to the artifactType `appContainer`. Here the version of the app which is packaged into the container will be used as version for the container itself.

* Using `password` you could define a custom path to the descriptor file.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `author_ip`
* `publish_ip`
* `artifact_name`
* `artifact_version`
* `username`
* `password`


## Example

```groovy
artifactSetVersion script: this, buildTool: 'maven'
```


