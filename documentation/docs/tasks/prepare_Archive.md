# prepare_Archive

## Description
This task is used to package/archieve the artifact in the zip format

## Prerequsites
none

## Parameters

| parameter | mandatory  | derived from |
| ----------|------------|--------------|
| artifact | yes | 'config.ARTIFACT' |
| exclude | no | 'config.EXCLUDE' |


* `artifact` defines the artifact name that needs to be zipped.
* `exclude` defines the exclude file or formats if any in the zip file.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `artifact`
* `exclude`

## Exceptions

None

## Example

```groovy
prepare_Archive
```
