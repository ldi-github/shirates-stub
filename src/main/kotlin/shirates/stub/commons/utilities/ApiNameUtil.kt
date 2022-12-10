package shirates.stub.commons.utilities

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import shirates.stub.commons.annotaions.ApiDescription
import shirates.stub.commons.logging.Logger
import java.net.MalformedURLException
import java.net.URL
import kotlin.reflect.KClass
import kotlin.reflect.full.functions

object ApiNameUtil {

    lateinit private var mApiNameMap: MutableMap<String, String>

    /**
     * URL-Path to API name
     */
    val ApiNameMap: Map<String, String>
        get() {
            return mApiNameMap
        }

    /**
     * setupApiNameMap
     */
    fun setupApiNameMap(controllers: List<KClass<*>>): MutableMap<String, String> {

        Logger.info("Mapping urlPath to ApiDescription.")

        val map = mutableMapOf<String, String>()

        for (c in controllers) {
            for (f in c.functions) {

                val requestMapping = c.annotations.firstOrNull() { it is RequestMapping } as RequestMapping ?: continue
                val prefix = requestMapping.value.firstOrNull() ?: ""
                val apiDescription = f.annotations.firstOrNull() { it is ApiDescription } as ApiDescription? ?: continue

                val getMapping = f.annotations.firstOrNull() { it is GetMapping } as GetMapping?
                if (getMapping != null) {
                    val urls = getMapping.value
                    for (url in urls) {
                        val fullUrl = "$prefix$url".replace("//", "/")
                        map[fullUrl] = apiDescription.description
                        Logger.info("[\"$fullUrl\"]=\"${apiDescription.description}\"")
                    }
                }

                val postMapping = f.annotations.firstOrNull() { it is PostMapping } as PostMapping?
                if (postMapping != null) {
                    val urls = postMapping.value
                    for (url in urls) {
                        val fullUrl = "$prefix$url".replace("//", "/")
                        map[fullUrl] = apiDescription.description
                        Logger.info("[\"$fullUrl\"]=\"${apiDescription.description}\"")
                    }
                }
            }
        }

        mApiNameMap = map
        return map
    }

    /**
     * Get API name for URL-Path
     */
    fun getApiName(urlPath: String): String {

        val map = ApiNameMap
        val noSlash = urlPath.trimEnd('/')
        val withSlash = noSlash + "/"
        if (map.containsKey(noSlash))
            return map[noSlash] as String
        else if (map.containsKey(withSlash)) {
            return map[withSlash] as String
        } else
            return "(name not defined)"
    }

    /**
     * Get urlPath for urlPathOrApiName
     */
    fun getUrlPath(urlPathOrApiName: String): String {

        if (ApiNameMap.containsValue(urlPathOrApiName)) {
            return ApiNameMap.toList().first { it.second == urlPathOrApiName }.first
        }

        if (urlPathOrApiName.startsWith("/")) {
            try {
                val url = URL("http://stub1$urlPathOrApiName")
                return url.path
            } catch (e: Throwable) {
                // NOP
            }
        }
        if (urlPathOrApiName.startsWith("http")) {
            try {
                // Extract urlPath
                val url = URL(urlPathOrApiName)
                return url.path
            } catch (e: MalformedURLException) {
                return "Invalid url format. Specify url-path or url. \"$urlPathOrApiName\""
            }
        }

        return urlPathOrApiName
    }

    /**
     * Get date and time label for the stub file
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
}
