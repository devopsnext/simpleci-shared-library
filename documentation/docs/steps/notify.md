# notify

## Description
This step is used to send notification email to the user.

## Prerequsites
none

## Parameters

| parameter | mandatory | default | possible values | derived from |
| ----------|-----------|---------|-----------------|--------------|
| to | yes |  |  | 'config.TO' |


* `to` defines the email address where the mail needs to be sent.


## Step configuration
The following parameters can also be specified as step parameters using the global configuration file:

* `to`

## Exceptions

None

## Example

```groovy
notify
```
