# Using APIs for multiple instance

You can use APIs to manage multiple stub instances.

## API List

See [Management API](../management/management_api.md)

<hr>

## registerInstance

### Usage

```
http://stub1/management/registerInstance?instanceKey=agentId1&profile=emulator1
```

### Response

```json
{
  "instanceKey": "agentId1",
  "profile": "emulator1"
}
```

<hr>

## getInstanceInfo

### Usage

```
http://stub1/management/getInstanceInfo?profileOrInstanceKeyPrefix=emulator1
```

### Response

```json
{
  "instanceKey": "agentId1",
  "profile": "emulator1"
}
```

<hr>

## getInstanceProfileMap

### Usage

```
http://stub1/management/getInstanceProfileMap
```

### Response

```json
{
  "agentId1": "emulator1",
  "agentId2": "emulator2",
  "agentId3": "emulator3"
}
```

<hr>

## resetInstance

### Usage

```
http://stub1/management/resetInstance?profileOrInstanceKeyPrefix=emulator1
```

### Response

```json
{
  "agentId1": "emulator1",
  "agentId2": "emulator2",
  "agentId3": "emulator3"
}
```

<hr>

## removeInstance

### Usage

```
http://stub1/management/removeInstance?profileOrInstanceKeyPrefix=emulator1
```

### Response

```json
{
  "agentId1": "emulator1",
  "agentId2": "emulator2",
  "agentId3": "emulator3"
}
```

<br>

- [index](../index.md)

<br>
