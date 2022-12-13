package shirates.stub.models

import org.springframework.boot.json.GsonJsonParser
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.StubFileNameUtil
import shirates.stub.entities.StubKeys
import java.io.File
import java.io.FileNotFoundException

class StubConfig(stubConfigFile: String? = null) {

    val DEFAULT_CONFIG = "/data/config/stubConfig.json"

    companion object {

        lateinit var instance: StubConfig

        /**
         * setup
         */
        fun setup(stubConfigFile: String?) {

            this.instance = StubConfig(stubConfigFile)
        }

        val API_KEY: String
            get() {
                return instance.stubKeys.API_KEY
            }

        val API_KEY2: String
            get() {
                return instance.stubKeys.API_KEY2
            }

        val API_KEY3: String
            get() {
                return instance.stubKeys.API_KEY3
            }
    }

    /**
     * configFile
     */
    var configFile: String

    /**
     * keysFile
     */
    lateinit var keyFile: String

    /**
     * workspaces
     */
    lateinit var workspaces: String

    /**
     * workspaceName
     */
    lateinit var workspaceName: String

    /**
     * outputRequestBody
     */
    var outputRequestBody = true

    /**
     * trace
     */
    var trace = false

    /**
     * urlValueEncode
     */
    var urlValueEncode = false

    /**
     * agentIdHeaderName
     */
    var agentIdHeaderName = ""

    /**
     * workspaceDir
     */
    val workspaceDir: String
        get() {
            return "$workspaces/$workspaceName"
        }

    /**
     * stubKeys
     */
    lateinit var stubKeys: StubKeys

    /**
     * init
     */
    init {
        val s = stubConfigFile ?: DEFAULT_CONFIG
        configFile = StubFileNameUtil.getFullPath(s)
        loadStubConfigJson(configFile)
        loadKeys()
    }

    private fun getFilePath(jso: Map<String, Any>, key: String): String {

        if (!jso.containsKey(key)) {
            throw NoSuchElementException("Key not found. (key=$key, configFile=${configFile})")
        }
        val value = jso.get(key) as String? + ""
        val filePath = StubFileNameUtil.getFullPath(value)
        if (!File(filePath).exists()) {
            throw FileNotFoundException("File not found. (key=$key, value=${value})")
        }
        return filePath
    }

    private fun loadStubConfigJson(stubConfigFile: String) {

        Logger.info("Loading stub configuration. (file=$stubConfigFile)")

        val configData = File(stubConfigFile).readText()
        val jso = GsonJsonParser().parseMap(configData)

        // keyFile
        keyFile = getFilePath(jso, "keyFile")

        // workspaces
        workspaces = getFilePath(jso, "workspaces")

        // workspaceName
        if (!jso.containsKey("workspaceName")) {
            throw NoSuchElementException("Key not found. (key=workspaceName, configFile=$configFile)")
        }
        // outputRequestBody
        if (jso.containsKey("outputRequestBody")) {
            outputRequestBody = jso.getValue("outputRequestBody") == "true"
        }
        // trace
        if (jso.containsKey("trace")) {
            trace = jso.getValue("trace") == "true"
            Logger.enableTrace = trace
        }

        // urlValueEncode
        if (jso.containsKey("urlValueEncode")) {
            urlValueEncode = jso.getValue("urlValueEncode") == "true"
        }

        // agentIdHeaderName
        if(jso.containsKey("agentIdHeaderName")){
            agentIdHeaderName = jso.getValue("agentIdHeaderName").toString()
        }

        workspaceName = jso.get("workspaceName") as String + ""
        if (workspaceName.isBlank()) {
            throw IllegalArgumentException("workspaceName is not specified.(key=workspaceName, configFile=$configFile)")
        }
        if (!File(workspaceDir).exists()) {
            throw FileNotFoundException("workspace not found. ($workspaceDir)")
        }
    }

    private fun loadKeys() {

        Logger.info("Loading keys. (file=$keyFile)")

        val fileContent = File(this.keyFile).readText()
        val jso = GsonJsonParser().parseMap(fileContent)

        // stubKeys
        val s = StubKeys()
        s.description = jso.get("description") as String
        s.API_KEY = jso.get("API_ENC_KEY") as String
        s.API_KEY2 = jso.get("API_ENC_KEY2") as String
        s.API_KEY3 = jso.get("API_ENC_KEY3") as String
        this.stubKeys = s
    }

}
