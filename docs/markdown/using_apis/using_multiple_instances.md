# Using multiple instance

You can use multiple stub instances on running parallel tests.

## Prerequisite

1. Confirm **agentIdHeaderName** in stubConfig.json.

```
  "#Http header name for agent id (for multiple stub instances mode)": "",
  "agentIdHeaderName": "agentId",
```

2. Implement your app to set **agentId** in http header. agentId corresponds to **instanceKey** of stub instance.

## How to use

1. Register stub instance using registerInstance(API).

```
http://stub1/management/registerInstance?instanceKey=agentId1&profile=emulator1
```

2. Set data pattern using setDataPattern(API).

```
http://stub1/management/setDataPattern?urlPath=/product/list&dataPatternName=product/02
```

3. Test you app.

<br>

- [index](../index.md)

<br>
