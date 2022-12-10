# Management APIs

You can control shirates-stub with management APIs.<br>

## Instance APIs

| Method | Url-Path(args)                                     | Description                                       |
|:-------|:---------------------------------------------------|:--------------------------------------------------|
| GET    | /management/registerInstance(instanceKey, profile) | Set up stub instance for instanceKey and profile. |
| GET    | /management/getInstanceInfo(profile)               | Gets instance info.                               |
| GET    | /management/getInstanceProfileMap()                | Gets instance to profile map.                     |
| GET    | /management/resetInstance(profile)                 | Reset instance.                                   |
| GET    | /management/removeInstance(profile)                | Remove instance.                                  |

## Data Pattern APIs

| Method | Url-Path(args)                                                         | Description                                    |
|:-------|:-----------------------------------------------------------------------|:-----------------------------------------------|
| GET    | /management/resetDataPattern(profile)                                  | Reset data pattern.                            |
| GET    | /management/listDataPattern(profile)                                   | Returns a list of data patterns.               |
| GET    | /management/setDataPattern(profile, urlPathOrApiName, dataPatternName) | Binds dataPatternName to urlPath               |
| GET    | /management/getDataPattern(profile, urlPathOrApiName)                  | Returns data pattern name bound to the urlPath |
| GET    | /management/encode(targetData)                                         | For encoding test.                             |
| GET    | /management/decode(targetData)                                         | For decoding test.                             |

## Other APIs

| Method | Url-Path(args)                                                         | Description        |
|:-------|:-----------------------------------------------------------------------|:-------------------|
| GET    | /management/encode(targetData)                                         | For encoding test. |
| GET    | /management/decode(targetData)                                         | For decoding test. |

<br>

_profile_ is for multiple instance mode. When _profile_ is omitted, default instance is used.

<br>

- [Using data pattern APIs](../using_apis/using_data_pattern_apis.md)
- [Using multiple instances](../using_apis/using_multiple_instances.md)
- [Using APIs for multiple instances](../using_apis/using_apis_for_multiple_instances.md)
- [index](../index.md)

<br>
