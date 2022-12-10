package qmtest.stub.commons.extensions

import org.apache.catalina.connector.RequestFacade
import shirates.stub.models.StubConfig
import javax.servlet.ServletRequest

fun ServletRequest.getHeaderMap(): Map<String, String> {

    val req = this as RequestFacade
    val headerNames = req.headerNames.toList()
    val map = mutableMapOf<String, String>()

    for (headerName in headerNames) {
        map[headerName] = req.getHeader(headerName)
    }

    return map
}

fun ServletRequest.getAgentId(): String {

    val agentIdHeaderName = StubConfig.instance.agentIdHeaderName
    val headerMap = this.getHeaderMap()
    if (headerMap.containsKey(agentIdHeaderName).not()) {
        return ""
    }

    val agentId = headerMap.get(agentIdHeaderName).toString()

    return agentId
}