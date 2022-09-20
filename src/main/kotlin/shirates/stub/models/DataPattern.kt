package shirates.stub.models

import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.entities.UrlDataPattern

object DataPattern {

    /**
     * setDataPattern
     */
    fun setDataPattern(urlPathOrApiName: String, dataPatternName: String): String {

        val path = ApiNameUtil.getUrlPath(urlPathOrApiName)
        val m = StubDataManager.instance
        m.setDataPatternName(path, dataPatternName)

        val sb = StringBuilder()
        sb.append("urlPath=\"$path\"\ndataPatternName=\"$dataPatternName\"\n")
        val filePath = m.getDataFilePathFromUrl(path)
        if (filePath == null) {
            sb.append("data file not found.\n")
        } else {
            sb.append("dataFile=$filePath\n")
        }

        return sb.toString()
    }

    /**
     * getDataPattern
     */
    fun getDataPattern(urlPathOrApiName: String): String {

        val urlPath = ApiNameUtil.getUrlPath(urlPathOrApiName = urlPathOrApiName)
        return StubDataManager.instance.dataPatternMap.get(urlPath) ?: ""
    }

    /**
     * setAllUrlTo
     */
    fun setAllUrlTo(dataPatternName: String) {

        StubDataManager.instance.setAllUrlTo(dataPatternName)
    }

    /**
     * setAllUrlToDefault
     */
    fun setAllUrlToDefault() {

        StubDataManager.instance.setAllUrlToDefault()
    }

    /**
     * resetDataPattern
     */
    fun resetDataPattern() {

        StubDataManager.instance.resumeStartupDataPatternMap()
        for (m in StubDataManager.instance.dataPatternMap) {
            val urlPath = m.key
            val dataPatternName = m.value
            Logger.info("\"$urlPath\" -> \"$dataPatternName\"")
        }
    }

    /**
     * listDataPattern
     */
    fun listDataPattern(): List<UrlDataPattern> {

        val m = StubDataManager.instance
        return m.dataPatternMapList.map { e -> UrlDataPattern(e.key, e.value) }.toList()
    }

}