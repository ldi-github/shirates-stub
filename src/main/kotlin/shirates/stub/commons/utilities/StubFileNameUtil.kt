package shirates.stub.commons.utilities

import shirates.stub.models.StubDataManager
import java.io.File
import java.io.FileNotFoundException
import java.net.URL

object StubFileNameUtil {

    /**
     * Project directory
     */
    val projectDir: String
        get() = System.getProperty("user.dir").replace("\\", "/")

    /**
     * Get full path
     */
    fun getFullPath(path: String): String {

        var fullPath = projectDir + "/" + path
        fullPath = fullPath.replace("//", "/")
        return fullPath
    }

    /**
     * Escape url characters for valid file name
     * ":" -> "="
     * "/" -> "~"
     */
    fun escape(url: String): String {

        return url.replace(":", "=").replace("/", "~")
    }

    /**
     * Unescape url
     * "=" -> ":"
     * "~" -> "/"
     */
    fun unescape(escapedUrl: String): String {

        return escapedUrl.replace("=", ":").replace("~", "/")
    }

    /**
     * Get url from stub file name.
     */
    fun getUrlFromStubFile(stubFileName: String): URL {

        val file = File(stubFileName)
        val escapedUrl = file.name.split("..").first()
        val url = URL("http://" + unescape(escapedUrl))
        return url
    }

    /**
     * Find a file that matches the url.
     * (Sort by file name descending and returns the first.)
     **/
    fun findMatchedFile(searchDir: String, url: String): String? {

        val dir = File(searchDir)
        val files = dir.listFiles() ?: throw FileNotFoundException("searchDir not found. $searchDir")
        val partOfFileName = escape(url)
        val list = files.map { it.name }
            .filter { name -> (name + "").contains(partOfFileName) }
            .sortedByDescending { name -> name }
            .toList()
        val fileName = list.firstOrNull()
        return if (fileName == null) null
        else dir.absolutePath.replace("\\", "/") + "/" + fileName
    }

    /**
     * Trim the end of path.
     */
    fun trim(path: String, trimChar: String?): String {
        var str = path

        if (trimChar == null) {
            throw IllegalArgumentException("trimChar = null")
        }
        if (trimChar.length > 1) {
            throw IllegalArgumentException("trimChar.length must be grater than 1.")
        }

        if (str.endsWith(trimChar)) {
            str = str.substring(0, str.length - 1)
        }
        return str
    }

    /**
     * Get the date and time label from the stub file.
     */
    fun getDateTimeLabelFromStubFile(stubFileName: String): String {

        val parts = stubFileName.split("..")
        if (parts.count() < 2) {
            return ""
        }
        val components = parts.last().split(".")
        if (components.count() < 2) {
            return ""
        }
        val dateTimeLabel = components[0] + "." + components[1]
        return dateTimeLabel
    }

    /**
     * Get the data pattern name from the file.
     */
    fun getDataPatternNameFromFile(file: File): String {

        val dataPatternName = file.parent
            .replace("\\", "/")
            .replace(StubDataManager.defaultInstance.stubConfig.workspaceDir, "")
            .trim('/')
        return dataPatternName
    }

}
