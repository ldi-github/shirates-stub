package shirates.stub.commons.utilities

import org.apache.commons.codec.net.URLCodec

object UrlValueEncodeUtil {

    val pattern = "([\">])(http|https)?://(.+?)/(.*?)([\"<])"

    /**
     * UrlValueEncodeUtil
     */
    fun replace(input: String): String {

        val output = input.replace(pattern.toRegex()) {
            val start = it.groups[1]!!.value
            val scheme = it.groups[2]!!.value
            val host = it.groups[3]!!.value
            val path = it.groups[4]!!.value
            val end = it.groups[5]!!.value
            val tokens = path.split("/")
            val codec = URLCodec("UTF-8")
            val encodedPath = tokens.map { token -> codec.encode(token, "utf-8") }.joinToString("/")
            val result = "$start$scheme://$host/$encodedPath$end"
            result
        }

        return output
    }
}