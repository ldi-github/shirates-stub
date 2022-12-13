# stubConfig.json

You can configure workspace path, keyFile path, and other parameters in this file.

```
{
  "workspaces": "data/workspaces",
  "workspaceName": "demo",
  "keyFile": "data/config/keys/staging.keys.json",

  "#urlValueEncode": "true",
  "#outputRequestBody": "false",
  "#trace": "true",

  "#Http header name for agent id (for multiple stub instances mode)": "",
  "agentIdHeaderName": "agentId",


  "#[Note]: Restart process to for changes to take effect.": ""
}
```

**Note:** Name that starts with "#" is comment. Remove "#" to take effect.

| name              | description                                                       | default |
|:------------------|:------------------------------------------------------------------|:-------:|
| workspaces        | Parent directory of workspaceName                                 |         |
| workspaceName     | Workspace name                                                    |         |
| keyFile           | Path of keys.json                                                 |         |
| urlValueEncode    | Encodes url value in response data                                |  false  |
| outputRequestBody | Outputs request lines in console                                  |  true   |
| trace             | Outputs more information in console                               |  false  |
| agentIdHeaderName | Http header name for agent id (for multiple stub instances mode)  | agentId |

<br>

- [Data directory](data_directory.md)

<br>
