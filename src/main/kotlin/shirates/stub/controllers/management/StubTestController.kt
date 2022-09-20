package shirates.stub.controllers.management

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import shirates.stub.commons.annotaions.ApiDescription
import shirates.stub.commons.annotaions.StubServer
import shirates.stub.commons.controllers.StubController
import javax.servlet.http.HttpServletRequest

@StubServer("template")
@RestController
@RequestMapping("/", produces = ["text/plain"])
class StubTestController(val restTemplate: RestTemplate) : StubController() {

    @ApiDescription("StubTest")
    @GetMapping("/stubtest")
    fun template(request: HttpServletRequest): String {

        val data = getStubData(request, restTemplate)
        return data.toString()
    }
}
