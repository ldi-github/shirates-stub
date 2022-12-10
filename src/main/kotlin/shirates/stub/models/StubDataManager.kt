package shirates.stub.models

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.springframework.boot.json.GsonJsonParser
import org.springframework.http.HttpStatus
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.commons.utilities.FileLockUtility
import shirates.stub.commons.utilities.StubFileNameUtil
import shirates.stub.commons.utilities.UrlValueEncodeUtil
import shirates.stub.entities.HttpException
import shirates.stub.entities.StubData
import shirates.stub.entities.StubFileItem
import shirates.stub.entities.UrlDataPattern
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.AbstractMap.SimpleEntry
import javax.servlet.http.HttpServletRequest

/**
 * StubDataManager
 */
class StubDataManager(var stubConfig: StubConfig) {

    companion object {

        /**
         * instanceMap
         */
        val instanceMap = mutableMapOf<String, StubDataManager>()

        /**
         * instanceProfileMap
         */
        val instanceProfileMap = mutableMapOf<String, String>()

        /**
         * defaultInstance
         */
        val defaultInstance: StubDataManager
            get() {
                return instanceMap[""]!!
            }

        /**
         * registerInstanceForProfile
         */
        fun registerInstanceForProfile(instanceKey: String, profile: String) {

            if (instanceKey.isBlank()) {
                throw IllegalArgumentException("instanceKey=$instanceKey")
            }
            if (profile.isBlank()) {
                throw IllegalArgumentException("profile=$profile")
            }

            /**
             * Delete profile entries
             */
            for (key in instanceProfileMap.keys.toList()) {
                if (instanceProfileMap[key] == profile) {
                    instanceProfileMap.remove(key)
                }
            }

            /**
             * Map instanceKey to profile
             */
            instanceProfileMap[instanceKey] = profile
            Logger.info(
                message = "instanceKey mapped to profile. ($instanceKey -> $profile)",
                instanceKey = instanceKey
            )

            /**
             * Create StubManager instance for the userAgentId
             */
            if (instanceMap.containsKey(instanceKey).not()) {
                setup(instanceKey = instanceKey)
            }

            saveInstanceProfileMap()
        }

        private fun saveInstanceProfileMap() {

            val gson = GsonBuilder().setPrettyPrinting().create()
            val file = "data/temp/instanceProfileMap.json"
            FileLockUtility.lockFile(Path.of(file)) {
                val json = gson.toJson(instanceProfileMap)
                File("data/temp/instanceProfileMap.json").writeText(text = json)
            }
        }

        /**
         * removeInstance
         */
        fun removeInstance(instanceKey: String) {

            if (instanceMap.containsKey(instanceKey).not()) {
                return
            }
            instanceMap.remove(instanceKey)

            if (instanceProfileMap.containsKey(instanceKey).not()) {
                return
            }
            instanceProfileMap.remove(instanceKey)

            saveInstanceProfileMap()
        }

        /**
         * registerInstanceFromFile
         */
        fun registerInstanceFromFile(path: Path = Path.of("data/temp/instanceProfileMap.json")) {

            if (Files.exists(path).not()) {
                return
            }
            Logger.info("Loading instanceProfileMap from $path")

            val json = File(path.toUri()).readText()
            val map = GsonJsonParser().parseMap(json)
            for (instanceKey in map.keys) {
                val profile = map[instanceKey].toString()
                registerInstanceForProfile(instanceKey = instanceKey, profile = profile)
            }

            Logger.info("instanceProfileMap loaded from $path")
            Logger.info("[instanceKey]=profile")
            for (key in map.keys) {
                val value = map[key].toString()
                Logger.info("[\"$key\"]=\"$value\"")
                instanceProfileMap[key] = value
            }
        }

        /**
         * getInstanceKey
         */
        fun getInstanceKey(profileOrInstanceKeyPrefix: String?): String {

            if (profileOrInstanceKeyPrefix.isNullOrBlank()) {
                return ""
            }

            val searchWord = profileOrInstanceKeyPrefix
            val entry =
                instanceProfileMap.map { it }.firstOrNull() { it.value == searchWord || it.key.startsWith(searchWord) }
            return entry?.key ?: ""
        }

        /**
         * getInstanceJson
         */
        fun getInstanceJson(profileOrInstanceKeyPrefix: String?): JsonObject {

            val instance = getInstance(profileOrInstanceKeyPrefix = profileOrInstanceKeyPrefix)
            val profile = instanceProfileMap[instance.instanceKey]

            val jso = JsonObject()
            jso.addProperty("instanceKey", instance.instanceKey)
            jso.addProperty("profile", profile)

            return jso
        }

        /**
         * getInstance
         */
        fun getInstance(profileOrInstanceKeyPrefix: String?): StubDataManager {

            val instanceKey = getInstanceKey(profileOrInstanceKeyPrefix = profileOrInstanceKeyPrefix)

            return instanceMap[instanceKey]!!
        }

        /**
         * setup
         */
        fun setup(instanceKey: String): StubDataManager {
            val instance = StubDataManager(StubConfig.instance)
            instance.instanceKey = instanceKey
            this.instanceMap[instanceKey] = instance
            Logger.info(
                message = "StubDataManager instance created. (instanceKey=\"$instanceKey\")",
                instanceKey = instanceKey
            )

            instance.setAllUrlTo("default")
            instance.saveStartupDataPatternMap()
            return instance
        }

        /**
         * resetStubDataManager
         */
        fun resetStubDataManager(instanceKey: String) {
            setup(instanceKey = instanceKey)
        }
    }

    init {

    }

    /**
     * properties
     */

    /**
     * instanceKey
     */
    var instanceKey = ""

    /**
     * defaultDataPatternName
     */
    var defaultDataPatternName: String = "default"

    /**
     * startupDataPatternMap
     */
    var startupDataPatternMap: MutableMap<String, String> = HashMap()

    /**
     * dataPatternMap
     */
    var dataPatternMap: MutableMap<String, String> = HashMap()

    /**
     * dataPatternMapList
     */
    val dataPatternMapList: List<SimpleEntry<String, String>>
        get() {

            val keys = dataPatternMap.keys.sortedBy { it }.toList()
            val list = ArrayList<SimpleEntry<String, String>>()

            for (key in keys) {
                val entry = SimpleEntry<String, String>(key, dataPatternMap[key])
                list.add(entry)
            }

            return list
        }

    /**
     * functions
     */

    /**
     * saveStartupDataPatternMap
     */
    fun saveStartupDataPatternMap() {

        startupDataPatternMap.clear()
        startupDataPatternMap.putAll(dataPatternMap)
    }

    /**
     * resumeStartupDataPatternMap
     */
    fun resumeStartupDataPatternMap() {

        dataPatternMap.clear()
        dataPatternMap.putAll(startupDataPatternMap)
    }

    /**
     * getStubFileList
     */
    fun getStubFileList(): List<StubFileItem> {

        val list = mutableListOf<StubFileItem>()
        val files = File(stubConfig.workspaceDir).walkTopDown().maxDepth(100)
            .filter { !it.isHidden && !it.isDirectory }
        for (file in files) {
            val item = StubFileItem(file)
            if (item.urlPath != "") {
                list.add(item)
            }
        }
        return list
    }

    /**
     * setDataPatternName
     */
    fun setDataPatternName(urlPath: String, dataPatternName: String) {

        if (canSetDataPattern(urlPath, dataPatternName)) {
            val urlPathEndsWithSlash = "$urlPath/"
            if (dataPatternMap.containsKey(urlPathEndsWithSlash)) {
                dataPatternMap[urlPathEndsWithSlash] = dataPatternName
            } else {
                dataPatternMap[urlPath] = dataPatternName
            }
            Logger.info("\"$urlPath\" -> \"$dataPatternName\"")
        } else {
            throw IllegalArgumentException("Could not set dataPattern to urlPath. (dataPatternName=$dataPatternName, urlPath=$urlPath)")
        }
    }

    /**
     * getActiveDataPatternName
     */
    fun getActiveDataPatternName(urlPath: String): String {

        val dataPatternName: String = dataPatternMap[urlPath] ?: ""
        return dataPatternName
    }

    /**
     * getUrlList
     */
    fun getUrlList(): List<String> {

        val list = getStubFileList()
            .map { it.urlPath }
            .distinctBy { it }
            .sortedBy { it }
        return list
    }

    private var _urlDataPatternList: List<UrlDataPattern>? = null

    /**
     * getUrlDataPatternList
     */
    fun getUrlDataPatternList(forceRefresh: Boolean = false): List<UrlDataPattern> {

        if (_urlDataPatternList == null || forceRefresh) {
            _urlDataPatternList = getStubFileList()
                .map { UrlDataPattern(it.urlPath, it.dataPatternName) }
                .distinctBy { it }
                .sortedBy { "${it.urlPath}|${it.dataPatternName}" }
        }
        return _urlDataPatternList!!
    }

    /**
     * setAllUrlTo
     */
    fun setAllUrlTo(dataPatternName: String) {

        Logger.info(message = "Setting urlPath -> dataPatternName", instanceKey = instanceKey)

        val urlList = getUrlList()
        for (urlPath in urlList) {
            setDataPatternName(urlPath, dataPatternName)
        }
    }

    /**
     * canSetDataPattern
     */
    fun canSetDataPattern(urlPath: String, dataPatternName: String): Boolean {

        val urlDataPattern = getUrlDataPatternList()
            .filter { (it.urlPath == urlPath || it.urlPath == "$urlPath/") && it.dataPatternName == dataPatternName }
            .firstOrNull()
        val result = urlDataPattern != null
        return result
    }

    /**
     * setAllUrlToDefault
     */
    fun setAllUrlToDefault() {

        setAllUrlTo(defaultDataPatternName)
    }

    /**
     * getDataFilePathFromUrl
     */
    fun getDataFilePathFromUrl(url: String): String? {

        val dataPatternName = this.getActiveDataPatternName(url)

        val wsDir = "${stubConfig.workspaceDir}/$dataPatternName".replace("//", "/")
        if (!File(wsDir).exists()) {
            Logger.warn("Data pattern file not found corresponding to the url(url=$url, dataPatternName=$dataPatternName)")
            return null
        }
        return StubFileNameUtil.findMatchedFile(wsDir, url)
    }

    /**
     * getStubData
     */
    fun getStubData(
        request: HttpServletRequest,
    ): StubData {

        val urlPath = request.requestURI
        val parameterNames = request.parameterNames.toList()
        val plainAndFormat = parameterNames.contains("0")
        val hasFormat = parameterNames.contains("format") || plainAndFormat
        val asPlain = parameterNames.contains("plain") || plainAndFormat
        val stubData = getStubData(
            urlPath = urlPath,
            format = hasFormat
        )

        if (asPlain) {
            stubData.encData = stubData.data
        }

        return stubData
    }

    /**
     * getStubData
     */
    fun getStubData(
        urlPath: String,
        format: Boolean = true,
        urlValueEncode: Boolean = StubConfig.instance.urlValueEncode
    ): StubData {
        val stubData = StubData()

        stubData.apiName = ApiNameUtil.getApiName(urlPath)
        stubData.dataPattern = getActiveDataPatternName(urlPath)
        val filePath = getDataFilePathFromUrl(urlPath)
        if (filePath.isNullOrBlank()) {
            Logger.warn(
                "File for the urlPath not found. (urlPath=$urlPath, dataPattern=${stubData.dataPattern})",
                instanceKey = instanceKey,
                apiName = stubData.apiName,
                dataPattern = stubData.dataPattern
            )
            return stubData
        }
        stubData.filePath = filePath
        Logger.info(
            "file=$filePath",
            instanceKey = instanceKey,
            apiName = stubData.apiName,
            dataPattern = stubData.dataPattern
        )

        var text = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8)

        if (urlValueEncode) {
            text = UrlValueEncodeUtil.replace(input = text)
        }

        stubData.data = text

        // Encrypt
        if (filePath.contains(".enc.")) {
            stubData.encData = Crypt.encrypt(stubData.data)
        }

        // Error
        if (filePath.contains(".error")) {
            filePath.split(".")
                .find { it.startsWith("error") }
                ?.substring("error".length)
                ?.let { statusCode ->
                    throw HttpException(HttpStatus.valueOf(statusCode.toInt()))
                }
        }

        if (format) {
            val jso = GsonJsonParser().parseMap(stubData.data)
            val gson = GsonBuilder().setPrettyPrinting().create()
            stubData.data = gson.toJson(jso)
        }

        return stubData
    }

}

