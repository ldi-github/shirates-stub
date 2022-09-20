package shirates.stub.commons.filters

import org.apache.catalina.connector.RequestFacade
import org.springframework.stereotype.Component
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.models.StubConfig
import shirates.stub.models.StubDataManager
import javax.servlet.*


@Component
class LoggingFilter : Filter {

    override fun init(filterConfig: FilterConfig?) {

//        println("LoggingFilter#init")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {

        try {
            val req = request as RequestFacade
            val url = req.requestURL.toString()
            if (url.endsWith(".js") || url.endsWith(".css")) {
                return
            }

            val q = req.parameterMap.map {
                val value = it.value.firstOrNull()
                if (value.isNullOrBlank()) {
                    it.key
                } else {
                    "${it.key}=${value}"
                }
            }.joinToString("&")
            val query = if (q.isBlank()) "" else "?$q"
            Logger.info(
                message = "${request.method} $url$query",
                apiName = ApiNameUtil.getApiName(req.servletPath),
                dataPattern = StubDataManager.instance.getActiveDataPatternName(urlPath = url)
            )

            if (StubConfig.instance.outputRequestBody) {
                synchronized(this) {
                    for (line in request.reader.readLines()) {
                        println(line)
                    }
                }
            }
        } finally {
            chain.doFilter(request, response)
        }
    }

    override fun destroy() {
        Logger.info("LoggingFilter#destroy")
    }
}