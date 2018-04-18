# prepare_Archive

## Description
This step is used to package/archieve the artifact in the zip format

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| artifact | yes |  |  | 'config.ARTIFACT' |
| exclude | yes |  |  | 'config.EXCLUDE' |


* `artifact` defines the artifact name that needs to be zipped.
* `exclude` defines the exclude file or formats if any in the zip file.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `artifact`
* `exclude`

## Exceptions

None

## Example

```groovy
prepare_Archive
```
