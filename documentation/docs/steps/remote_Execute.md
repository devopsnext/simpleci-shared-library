# remote_Execute

## Description
This step is used to validate the artifacts on remote machine and run them

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|---------------|
| remote_user | yes |  |  | 'config.REMOTE_USER'|
| remote_ip | yes |  |  | 'config.REMOTE_IP' |
| deploy_path | yes |  |  | 'config.DEPLOY_PATH' |
| script | yes |  |  | 'config.SCRIPT' |


* `remote_user` defines the remote server username.
* `remote_ip` defines the remote server IP address.
* `deploy_path` defines the path to deploy the script on server.
* `script` defines the script needed to be deployed on server.

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
remote_Execute
```
