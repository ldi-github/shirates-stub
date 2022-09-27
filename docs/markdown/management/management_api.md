# Management APIs

You can control shirates-stub with management APIs.

| Method | Url-Path(args)                                       | description                                    |
|:-------|:-----------------------------------------------------|:-----------------------------------------------|
| GET    | /management/resetStubDataManager                     | Reset stub-data-manager                        |
| GET    | /management/listDataPattern                          | Returns a list of data pattern                 |
| GET    | /management/getDataPattern(urlPath)                  | Returns data pattern name bound to the urlPath |
| GET    | /management/setDataPattern(urlPath, dataPatternName) | Binds dataPatternName to urlPath               |

<br>

## resetStubDataManager

### Usage

`http://stub1/management/resetStubDataManager`

### Console output

```
lineNo	logDateTime	[logType]	<threadId>	apiName	{dataPattern}	elapsed(ms)	message
24	2022/01/20 00:17:09.901	[INFO]	<35>	resetStubDataManager(API)	{}	-	GET http://stub1/management/resetStubDataManager
25	2022/01/20 00:17:09.902	[INFO]	<35>	-	{}	-	Setting urlPath -> dataPatternName
26	2022/01/20 00:17:09.908	[INFO]	<35>	-	{}	-	"/customer/list" -> "default"
27	2022/01/20 00:17:09.911	[INFO]	<35>	-	{}	-	"/product/list" -> "default"
28	2022/01/20 00:17:09.913	[INFO]	<35>	-	{}	-	"/supplier/list" -> "default"
```

<br>

## listDataPattern

### Usage

```http://stub1/management/listDataPattern```

### Response

```
[{"urlPath":"/customer/list","dataPatternName":"default"},{"urlPath":"/product/list","dataPatternName":"default"},{"urlPath":"/supplier/list","dataPatternName":"default"}]
```

<br>

## getDataPattern

### Usage

```http://stub1/management/getDataPattern?urlPath=/product/list```

### Response

```
default
```

<br>

## setDataPattern

### Usage

```http://stub1/management/setDataPattern?urlPath=/product/list&dataPatternName=product/02```

### Response

```
urlPath="/product/list"
dataPatternName="product/02"
dataFile=/Users/n.senba/dev/shirates-stub/data/workspaces/demo/product/02/server1.example.com~product~list..yyyyMMdd HHmmss.SSS.enc.json
```

### Using  urlPath as API Name

The value of urlPath may be **API name**. For example,

```http://stub1/management/setDataPattern?urlPath=Product%20list&dataPatternName=product/02```

In this case `ProductList` is not url-path, but API name of the urlPath(`/product/list`) annotated with **
@ApiDescription** in ProductController.

#### ProductController

```kotlin
    @ApiDescription("ProductList")
    @GetMapping("/product/list")
    fun getProducts(request: HttpServletRequest): String {
        val data = getStubData(request, restTemplate)
        return data.toString()
    }
```

<br>

## getDataPattern

### Usage

```http://stub1/management/getDataPattern?urlPath=/product/list```

### Response

```
product/02
```

### Using  urlPath as API Name

The value of urlPath may be **API name**. See **setDataPattern**.


<br>

- [index](../index.md)

<br>
