package shirates.stub.models

import com.google.gson.GsonBuilder
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.entities.UrlDataPattern

object DataPattern {

    /**
     * setDataPattern
     */
    fun setDataPattern(
        instanceKey: String,
        urlPathOrApiName: String,
        dataPatternName: String
    ): String {

        val urlPath = ApiNameUtil.getUrlPath(urlPathOrApiName)
        val m = StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey)
        m.setDataPatternName(urlPath = urlPath, dataPatternName = dataPatternName)

        val map = mutableMapOf<String, String>()
        map["urlPath"] = urlPath
        map["dataPatternName"] = dataPatternName
        val filePath = m.getDataFilePathFromUrl(urlPath)
        if (filePath == null) {
            map["message"] = "Data file not found."
        } else {
            map["message"] = "Data file found."
        }

        return GsonBuilder().setPrettyPrinting().create().toJson(map)
    }

    /**
     * getDataPattern
     */
    fun getDataPattern(
        instanceKey: String,
        urlPathOrApiName: String
    ): String {

        val urlPath = ApiNameUtil.getUrlPath(urlPathOrApiName = urlPathOrApiName)
        return StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey).dataPatternMap.get(urlPath) ?: ""
    }

    /**
     * setAllUrlTo
     */
    fun setAllUrlTo(
        instanceKey: String,
        dataPatternName: String
    ) {

        StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey).setAllUrlTo(dataPatternName)
    }

    /**
     * setAllUrlToDefault
     */
    fun setAllUrlToDefault(
        instanceKey: String
    ) {

        StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey).setAllUrlToDefault()
    }

    /**
     * resetDataPattern
     */
    fun resetDataPattern(
        instanceKey: String
    ) {

        val stubDataManager = StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey)
        stubDataManager.resumeStartupDataPatternMap()
        for (m in stubDataManager.dataPatternMap) {
            val urlPath = m.key
            val dataPatternName = m.value
            Logger.info(message = "\"$urlPath\" -> \"$dataPatternName\"", profile = stubDataManager.profile)
        }
    }

    /**
     * listDataPattern
     */
    fun listDataPattern(
        instanceKey: String
    ): List<UrlDataPattern> {

        val m = StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey)
        return m.dataPatternMapList.map({ e -> UrlDataPattern(e.key, e.value) }).toList()
    }

}