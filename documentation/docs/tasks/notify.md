# notify

## Description
This purpose of this task is to send notification to the user on build initiation,  status completion or failure.

## Prerequsites
none

## Parameters

| parameter | mandatory | derived from |
| ----------|-----------|--------------|
| to | yes | 'config.TO' |


* `to` defines the email address where the mail needs to be sent.


## Configuration
The following parameters can also be specified as input parameters using the global configuration file:

* `to`

## Exceptions

None

## Example

```groovy
notify
```
