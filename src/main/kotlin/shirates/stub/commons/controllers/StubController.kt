package shirates.stub.commons.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import shirates.stub.commons.annotaions.StubServer
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.StopWatch
import shirates.stub.entities.StubData
import shirates.stub.models.StubDataManager
import javax.servlet.http.HttpServletRequest

@RestController
class StubController() {

    val stubDataManager: StubDataManager
        get() {
            return StubDataManager.instance
        }

    private var _stubInfo: StubServer? = null

    /**
     * stubInfo
     */
    val stubInfo: StubServer
        get() {
            if (_stubInfo == null) {
                _stubInfo = getStubAnnotation()
            }
            return _stubInfo!!
        }

    private fun getStubAnnotation(): StubServer {

        val annotations = this.javaClass.annotations
        val s = annotations.filterIsInstance<StubServer>().firstOrNull()
            ?: throw NotImplementedError("Put StubServer annotation to the Controller. ${this.javaClass.name}")
        return s
    }

    /**
     * getStubData
     */
    fun getStubData(
        request: HttpServletRequest,
        restTemplate: RestTemplate,
    ): StubData {

        val sw = StopWatch().start()
        val stubData = stubDataManager.getStubData(request = request)
        sw.stop()
        Logger.trace(
            apiName = stubData.apiName,
            dataPattern = stubData.dataPattern,
            message = "stubDataManager.getStubData(request)",
            elapsedMillisecond = sw.elapsedMillis
        )

        return stubData
    }

}