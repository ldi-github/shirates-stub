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
lineNo	logDateTime	[logType]	<threadId>	(profile)	apiName	{dataPattern}	elapsed(ms)	message
1	2022/12/13 20:38:13.974	[INFO]	<19>	()	-	{}	-	Loading stub configuration. (file=/Users/wave1008/github/ldi-github/shirates-stub/data/config/stubConfig.json)
2	2022/12/13 20:38:13.985	[INFO]	<19>	()	-	{}	-	Loading keys. (file=/Users/wave1008/github/ldi-github/shirates-stub/data/config/keys/staging.keys.json)
3	2022/12/13 20:38:13.987	[INFO]	<19>	()	-	{}	-	StubDataManager instance created. (instanceKey="", profile=")
4	2022/12/13 20:38:13.987	[INFO]	<19>	()	-	{}	-	Setting urlPath -> dataPatternName
5	2022/12/13 20:38:13.998	[INFO]	<19>	()	-	{}	-	"/customer/list" -> "default"
6	2022/12/13 20:38:13.998	[INFO]	<19>	()	-	{}	-	"/product/list" -> "default"
7	2022/12/13 20:38:13.998	[INFO]	<19>	()	-	{}	-	"/supplier/list" -> "default"
8	2022/12/13 20:38:14.002	[INFO]	<19>	()	-	{}	-	Mapping urlPath to ApiDescription.
9	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/decode"]="decode(API)"
10	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/encode"]="encode(API)"
11	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/getDataPattern"]="getDataPattern(API)"
12	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/getInstanceInfo"]="getInstanceInfo(API)"
13	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/getInstanceProfileMap"]="getInstanceProfileMap(API)"
14	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/listDataPattern"]="listDataPattern(API)"
15	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/registerInstance"]="registerInstance(API)"
16	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/removeInstance"]="removeInstance(API)"
17	2022/12/13 20:38:14.038	[INFO]	<19>	()	-	{}	-	["/management/resetDataPattern"]="resetDataPattern(API)"
18	2022/12/13 20:38:14.039	[INFO]	<19>	()	-	{}	-	["/management/resetInstance"]="resetInstance(API)"
19	2022/12/13 20:38:14.039	[INFO]	<19>	()	-	{}	-	["/management/setDataPattern"]="setDataPattern(API)"
20	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/management/changeDataPattern"]="changeDataPattern(Page)"
21	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/management/changeAllDataPatternsToDefault"]="changeAllDataPatternsToDefault(Page)"
22	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/management/dataPatternChanger"]="dataPatternChanger(Page)"
23	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/management/cryptTool"]="cryptTool(Page)"
24	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/management/managementApiTest"]="managementApiTest(Page)"
25	2022/12/13 20:38:14.040	[INFO]	<19>	()	-	{}	-	["/customer/list"]="CustomerList"
26	2022/12/13 20:38:14.041	[INFO]	<19>	()	-	{}	-	["/product/list"]="ProductList"
27	2022/12/13 20:38:14.041	[INFO]	<19>	()	-	{}	-	["/supplier/list"]="SupplierList"
28	2022/12/13 20:38:14.041	[INFO]	<19>	()	-	{}	-	["/stubtest"]="StubTest"
29	2022/12/13 20:38:14.042	[INFO]	<19>	()	-	{}	-	Loading instanceProfileMap from data/temp/instanceProfileMap.json
30	2022/12/13 20:38:14.046	[INFO]	<19>	(p3)	-	{}	-	instanceKey mapped to profile. (3 -> p3)
31	2022/12/13 20:38:14.046	[INFO]	<19>	(p3)	-	{}	-	StubDataManager instance created. (instanceKey="3", profile="p3)
32	2022/12/13 20:38:14.046	[INFO]	<19>	(p3)	-	{}	-	Setting urlPath -> dataPatternName
33	2022/12/13 20:38:14.048	[INFO]	<19>	(p3)	-	{}	-	"/customer/list" -> "default"
34	2022/12/13 20:38:14.048	[INFO]	<19>	(p3)	-	{}	-	"/product/list" -> "default"
35	2022/12/13 20:38:14.048	[INFO]	<19>	(p3)	-	{}	-	"/supplier/list" -> "default"
36	2022/12/13 20:38:14.052	[INFO]	<19>	(profile1)	-	{}	-	instanceKey mapped to profile. (1 -> profile1)
37	2022/12/13 20:38:14.052	[INFO]	<19>	(profile1)	-	{}	-	StubDataManager instance created. (instanceKey="1", profile="profile1)
38	2022/12/13 20:38:14.052	[INFO]	<19>	(profile1)	-	{}	-	Setting urlPath -> dataPatternName
39	2022/12/13 20:38:14.054	[INFO]	<19>	(profile1)	-	{}	-	"/customer/list" -> "default"
40	2022/12/13 20:38:14.054	[INFO]	<19>	(profile1)	-	{}	-	"/product/list" -> "default"
41	2022/12/13 20:38:14.054	[INFO]	<19>	(profile1)	-	{}	-	"/supplier/list" -> "default"
42	2022/12/13 20:38:14.055	[INFO]	<19>	(profile2)	-	{}	-	instanceKey mapped to profile. (2 -> profile2)
43	2022/12/13 20:38:14.055	[INFO]	<19>	(profile2)	-	{}	-	StubDataManager instance created. (instanceKey="2", profile="profile2)
44	2022/12/13 20:38:14.055	[INFO]	<19>	(profile2)	-	{}	-	Setting urlPath -> dataPatternName
45	2022/12/13 20:38:14.056	[INFO]	<19>	(profile2)	-	{}	-	"/customer/list" -> "default"
46	2022/12/13 20:38:14.056	[INFO]	<19>	(profile2)	-	{}	-	"/product/list" -> "default"
47	2022/12/13 20:38:14.056	[INFO]	<19>	(profile2)	-	{}	-	"/supplier/list" -> "default"
48	2022/12/13 20:38:14.057	[INFO]	<19>	()	-	{}	-	instanceProfileMap loaded from data/temp/instanceProfileMap.json
49	2022/12/13 20:38:14.057	[INFO]	<19>	()	-	{}	-	[instanceKey]=profile
50	2022/12/13 20:38:14.057	[INFO]	<19>	()	-	{}	-	["3"]="p3"
51	2022/12/13 20:38:14.057	[INFO]	<19>	()	-	{}	-	["1"]="profile1"
52	2022/12/13 20:38:14.057	[INFO]	<19>	()	-	{}	-	["2"]="profile2"

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
