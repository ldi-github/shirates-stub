# keys.json

This file contains keys for encryption. File name is arbitrary.

## Sample

### staging.keys.json

```
{
  "description": "keys (staging)",
  "API_ENC_KEY": "012345678901234567890123456789012345678901234567890123456789",
  "API_ENC_KEY2": "",
  "API_ENC_KEY3": ""
}
```

`API_ENC_KEY` is primary encryption key. You can use `Crypt` to encrypt/decrypt as follows.

```kotlin
@ApiDescription("encrypt(API)")
@PostMapping("/encrypt")
fun encrypt(
    @RequestParam("targetData") targetData: String
): String {

    return Crypt.encrypt(targetData)
}

@ApiDescription("decrypt(API)")
@PostMapping("/decrypt")
fun decrypt(
    @RequestParam("targetData") targetData: String
): String {

    return Crypt.decrypt(targetData)
}
```

### stubConfig.json

Set `keyFile` attribute to the path of `staging.keys.json`.

```
{
  "workspaces": "data/workspaces",
  "workspaceName": "demo",
  "keyFile": "data/config/keys/staging.keys.json",
}
```

<br>

- [Data directory](data_directory.md)

<br>
