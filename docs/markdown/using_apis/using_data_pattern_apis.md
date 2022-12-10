# Using data pattern APIs

You can use management APIs to change data pattern of the url.

## API List

See [Management API](../management/management_api.md)

<hr>

## listDataPattern

### Usage

```
http://stub1/management/listDataPattern
```

### Response

```json
[
  {
    "urlPath": "/customer/list",
    "dataPatternName": "default"
  },
  {
    "urlPath": "/product/list",
    "dataPatternName": "default"
  },
  {
    "urlPath": "/supplier/list",
    "dataPatternName": "default"
  }
]
```

<hr>

## getDataPattern

### Usage

```
http://stub1/management/getDataPattern?urlPathOrApiName=/product/list
```

### Response

```
default
```

<hr>

## setDataPattern (urlPath)

### Usage

```
http://stub1/management/setDataPattern?urlPathOrApiName=/product/list&dataPatternName=product/02
```

### Response

```json
{
  "urlPath": "/product/list",
  "dataPatternName": "product/02",
  "message": "data file found."
}
```

<hr>

## setDataPattern(apiName)

### Usage

```
http://stub1/management/setDataPattern?urlPathOrApiName=ProductList&dataPatternName=product/02
```

### Response

```json
{
  "urlPath": "/product/list",
  "dataPatternName": "product/02",
  "message": "data file found."
}
```

<br>

In this case `ProductList` is API name of the urlPath(`/product/list`) annotated with **@ApiDescription** in
ProductController.

```kotlin
@ApiDescription("ProductList")
@GetMapping("/product/list")
fun getProducts(request: HttpServletRequest): String {
    val data = getStubData(request, restTemplate)
    return data.toString()
}
```

<hr>

## getDataPattern(urlPath)

### Usage

```
http://stub1/management/getDataPattern?urlPathOrApiName=/product/list
```

### Response

```
product/02
```

<hr>

## getDataPattern(apiName)

### Usage

```
http://stub1/management/getDataPattern?urlPathOrApiName=ProductList
```

### Response

```
product/02
```

<br>

In this case `ProductList` is API name of the urlPath(`/product/list`) annotated with **@ApiDescription** in
ProductController.

```kotlin
@ApiDescription("ProductList")
@GetMapping("/product/list")
fun getProducts(request: HttpServletRequest): String {
    val data = getStubData(request, restTemplate)
    return data.toString()
}
```

<br>

- [index](../index.md)

<br>
