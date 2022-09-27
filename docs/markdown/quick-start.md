# Quick Start

## Installation

Install following prerequisite tool.

### IntelliJ IDEA

If you have not installed IntelliJ IDEA, download **Ultimate** or **Community** and install it.
(Community is open source product)

https://www.jetbrains.com/idea/

<br>

## Configuring hosts file
### Mac
1. Open `/private/etc/hosts` with editor.
2. Append following line.
```
127.0.0.1   stub1
```

### Windows
1. Open `C:\Windows\System32\drivers\etc\hosts` with editor.
2. Append following line.
```
127.0.0.1   stub1
```


<br>

## Getting shirates-stub project

Clone source code project using git.

```shell
git clone https://github.com/ldi-github/shirates-stub.git
```

<br>

## Opening project

1. Open `shirates-stub` project (right click on `build.gradle.kts` and open with IntelliJ IDEA).
2. Wait for a while until background tasks finish. It takes minutes.

<br>

## Running stub application

1. Select `ApplicationRunKt`.
2. Click on `Debug`.

![](_images/run_application.png)

3. Open http://stub1/ in browser.

![](_images/localhost.png)

If you encountered SDK problem, try following procedures.

<br>

## Setting SDKs

1. `File > Project Structure`

![](_images/sdks_1.png)

2. Delete 1.8, then click OK.

![](_images/sdks_2.png)

3. Build project. Click `Setup SDK`.

![](_images/select_project_sdk.png)

4. Select SDK. 11 or higher is required.

![](_images/setup_project_sdk.png)

5. Reload gradle

![](_images/reload_gradle.png)

6. Build project.

7. Run application.

### Console output

```
----------------------------------------------------------------------------------------------------
/// 
/// shirates-stub - a stub tool for testing mobile apps -
/// 
----------------------------------------------------------------------------------------------------
lineNo	logDateTime	[logType]	<threadId>	apiName	{dataPattern}	elapsed(ms)	message
1	2022/09/28 00:37:45.858	[INFO]	<19>	-	{}	-	Loading stub configuration. (file=/Users/wave1008/github/ldi-github/shirates-stub/data/config/stubConfig.json)
2	2022/09/28 00:37:45.870	[INFO]	<19>	-	{}	-	Loading keys. (file=/Users/wave1008/github/ldi-github/shirates-stub/data/config/keys/staging.keys.json)
3	2022/09/28 00:37:45.871	[INFO]	<19>	-	{}	-	Setting urlPath -> dataPatternName
4	2022/09/28 00:37:45.880	[INFO]	<19>	-	{}	-	"/customer/list" -> "default"
5	2022/09/28 00:37:45.881	[INFO]	<19>	-	{}	-	"/product/list" -> "default"
6	2022/09/28 00:37:45.882	[INFO]	<19>	-	{}	-	"/supplier/list" -> "default"
7	2022/09/28 00:37:45.885	[INFO]	<19>	-	{}	-	Mapping urlPath to ApiDescription.
8	2022/09/28 00:37:45.922	[INFO]	<19>	-	{}	-	["/management/decrypt"]="decrypt(API)"
9	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/encrypt"]="encrypt(API)"
10	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/getDataPattern"]="getDataPattern(API)"
11	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/listDataPattern"]="listDataPattern(API)"
12	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/resetDataPattern"]="resetDataPattern(API)"
13	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/resetStubDataManager"]="resetStubDataManager(API)"
14	2022/09/28 00:37:45.923	[INFO]	<19>	-	{}	-	["/management/setDataPattern"]="setDataPattern(API)"
15	2022/09/28 00:37:45.925	[INFO]	<19>	-	{}	-	["/management/changeDataPattern"]="changeDataPattern(Page)"
16	2022/09/28 00:37:45.925	[INFO]	<19>	-	{}	-	["/management/changeAllDataPatternsToDefault"]="changeAllDataPatternsToDefault(Page)"
17	2022/09/28 00:37:45.925	[INFO]	<19>	-	{}	-	["/management/dataPatternChanger"]="dataPatternChanger(Page)"
18	2022/09/28 00:37:45.925	[INFO]	<19>	-	{}	-	["/management/cryptTool"]="cryptTool(Page)"
19	2022/09/28 00:37:45.928	[INFO]	<19>	-	{}	-	["/customer/list"]="CustomerList"
20	2022/09/28 00:37:45.929	[INFO]	<19>	-	{}	-	["/product/list"]="ProductList"
21	2022/09/28 00:37:45.930	[INFO]	<19>	-	{}	-	["/supplier/list"]="SupplierList"
22	2022/09/28 00:37:45.931	[INFO]	<19>	-	{}	-	["/stubtest"]="StubTest"

[stubConfig]
 urlValueEncode: false
 outputRequestBody: true

[Stub management menu] http://stub1/
 Options
 plain ... Decrypt to plain text. ex. http://stub1/customer/list?plain
 format ... Format JSON. ex. http://stub1/customer/list?plain&format
 0 ... Decrypt to plain text, then format JSON. ex. http://stub1/customer/list?0
 ```

<br>

- [index]

[index]: index.md

<br>
