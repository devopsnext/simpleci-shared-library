# remote_Deploy

## Description
This step is used to copy the artifacts to remote server, validate and run them

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| remote_user | yes |  |  | 'config.REMOTE_USER' |
| remote_ip | yes |  |  | 'config.REMOTE_IP' |
| deploy_path | yes |  |  | 'config.DEPLOY_PATH' |
| script | yes |  |  | 'config.SCRIPT' |


* `remote_user` defines the Author instance that is used to design, create and review the content which will be published on the application in the future. 
* `remote_ip` defines the Publish instance that is used to make the designed application available to the public and is located in a demilitarized zone. The default port used by the instance is 4503.
* `deploy_path` defines the name the artifact.
* `script` defines the version number of the artifact.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `remote_user`
* `remote_ip`
* `deploy_path`
* `script`

## Exceptions

None

## Example

```groovy
remote_Deploy
```
