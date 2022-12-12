package shirates.stub.commons.filters

import org.apache.catalina.connector.RequestFacade
import org.springframework.stereotype.Component
import qmtest.stub.commons.extensions.getAgentId
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

            /**
             * Determine instanceKey
             */
            var instanceKey = req.getAgentId()
            if (instanceKey.isBlank()) {
                val profile = req.getParameter("profile")
                instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
            }

            /**
             * Output log
             */
            val q = req.parameterMap.map {
                val value = it.value.firstOrNull()
                if (value.isNullOrBlank()) {
                    it.key
                } else {
                    "${it.key}=${value}"
                }
            }.joinToString("&")
            val query = if (q.isBlank()) "" else "?$q"
            val stubDataManager = StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey)
            val activeDataPatternName = stubDataManager.getActiveDataPatternName(urlPath = url)
            Logger.info(
                message = "${request.method} $url$query",
                profile = stubDataManager.profile,
                apiName = ApiNameUtil.getApiName(req.servletPath),
                dataPattern = activeDataPatternName
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