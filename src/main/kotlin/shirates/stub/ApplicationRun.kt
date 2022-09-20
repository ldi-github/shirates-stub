package shirates.stub

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import shirates.stub.commons.annotaions.StubServer
import shirates.stub.commons.logging.Logger
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.controllers.management.ManagementApiController
import shirates.stub.controllers.management.ManagementPageController
import shirates.stub.models.StubConfig
import shirates.stub.models.StubDataManager
import kotlin.reflect.KClass


@SpringBootApplication
class ApplicationRun

fun main(args: Array<String>) {

    val appContext = runApplication<ApplicationRun>(*args) {
        setBannerMode(Banner.Mode.OFF)
        setLogStartupInfo(false)
    }

    println()
    println("----------------------------------------------------------------------------------------------------")
    println("/// ")
    println("/// shirates-stub - a stub tool for testing mobile apps -")
    println("/// ")
    println("----------------------------------------------------------------------------------------------------")

    val stubConfigFile = args.firstOrNull()
    StubConfig.setup(stubConfigFile)
    StubDataManager.setup(StubConfig.instance)

    val controllers = mutableListOf<KClass<*>>()
    controllers.add(ManagementApiController::class)
    controllers.add(ManagementPageController::class)
    controllers.addAll(appContext.getBeansWithAnnotation(StubServer::class.java).map { it.value::class })
    ApiNameUtil.setupApiNameMap(controllers = controllers)

    val config = StubConfig.instance

    println()
    println("[stubConfig]")
    println(" urlValueEncode: ${config.urlValueEncode}")
    println(" outputRequestBody: ${config.outputRequestBody}")
    println()
    println("[Stub management menu] http://stub1/")
    println(" Options")
    println(" plain ... Decrypt to plain text. ex. http://stub1/customer/list?plain")
    println(" format ... Format JSON. ex. http://stub1/customer/list?plain&format")
    println(" 0 ... Decrypt to plain text, then format JSON. ex. http://stub1/customer/list?0")
    println("")

    Logger.headerPrinted = false
}


