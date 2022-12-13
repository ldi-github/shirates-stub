package shirates.stub.controllers.management

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shirates.stub.commons.annotaions.ApiDescription
import shirates.stub.commons.extensions.toJson
import shirates.stub.commons.logging.Logger
import shirates.stub.entities.HttpException
import shirates.stub.models.Crypt
import shirates.stub.models.DataPattern
import shirates.stub.models.StubDataManager
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/management", produces = ["application/json"])
class ManagementApiController {

    @ApiDescription("registerInstance(API)")
    @GetMapping("/registerInstance")
    fun registerInstance(
        request: HttpServletRequest,
        @RequestParam("instanceKey") instanceKey: String,
        @RequestParam("profile") profile: String
    ): String {

        StubDataManager.registerInstanceForProfile(instanceKey = instanceKey, profile = profile)

        val jso = StubDataManager.getInstanceJson(profileOrInstanceKeyPrefix = profile)
        return jso.toJson()
    }

    @ApiDescription("getInstanceInfo(API)")
    @GetMapping("/getInstanceInfo")
    fun getInstanceInfo(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        val obj = StubDataManager.getInstanceJson(profileOrInstanceKeyPrefix = profile)
        return obj.toJson()
    }

    @ApiDescription("getInstanceProfileMap(API)")
    @GetMapping("/getInstanceProfileMap")
    fun getInstanceProfileMap(
        request: HttpServletRequest
    ): String {

        return StubDataManager.instanceProfileMap.toJson()
    }

    private fun messageJson(message: String): String {

        return mapOf("message" to message).toJson()
    }

    @ApiDescription("resetInstance(API)")
    @GetMapping("/resetInstance")
    fun resetInstance(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        val result = StubDataManager.resetStubDataManager(instanceKey = instanceKey)
        return messageJson("$result (profile=$profile)")
    }

    @ApiDescription("removeInstance(API)")
    @GetMapping("/removeInstance")
    fun removeInstance(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        val result = StubDataManager.removeInstance(instanceKey = instanceKey)
        return messageJson("$result (profile=$profile)")
    }

    @ApiDescription("resetDataPattern(API)")
    @GetMapping("/resetDataPattern")
    fun resetDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {
        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        Logger.info(message = "Resetting data pattern.", profile = profile ?: "")
        DataPattern.resetDataPattern(instanceKey = instanceKey)
        return messageJson("Reset done.")
    }

    @ApiDescription("listDataPattern(API)")
    @GetMapping("/listDataPattern")
    fun listDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        val list = DataPattern.listDataPattern(instanceKey = instanceKey)
        return list.toJson()
    }

    @ApiDescription("setDataPattern(API)")
    @GetMapping("/setDataPattern")
    fun setDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?,
        @RequestParam("urlPathOrApiName") urlPathOrApiName: String?,
        @RequestParam("dataPatternName") dataPatternName: String
    ): String {

        if (urlPathOrApiName == null)
            throw IllegalArgumentException("Specify urlPathOrApiName.")

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        try {
            return DataPattern.setDataPattern(
                instanceKey = instanceKey,
                urlPathOrApiName = urlPathOrApiName,
                dataPatternName = dataPatternName
            )
        } catch (t: IllegalArgumentException) {
            throw HttpException(
                status = HttpStatus.BAD_REQUEST,
                message = "dataPattern corresponding to urlPath not found. (urlPathOrApiName=$urlPathOrApiName, dataPatternName=$dataPatternName)"
            )
        }
    }

    @ApiDescription("getDataPattern(API)")
    @GetMapping("/getDataPattern")
    fun getDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?,
        @RequestParam("urlPathOrApiName") urlPathOrApiName: String
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (instanceKey.isBlank() && profile.isNullOrBlank().not()) {
            val message = "No instance. (profile=$profile)"
            Logger.warn(message, profile ?: "")
            return messageJson(message)
        }
        val dataPatternName = DataPattern.getDataPattern(instanceKey = instanceKey, urlPathOrApiName = urlPathOrApiName)
        return mapOf("dataPatternName" to dataPatternName).toJson()
    }

    @ApiDescription("encode(API)")
    @PostMapping("/encode")
    fun encode(
        request: HttpServletRequest,
        @RequestParam("targetData") targetData: String
    ): String {

        return Crypt.encrypt(targetData)
    }

    @ApiDescription("decode(API)")
    @PostMapping("/decode")
    fun decode(
        request: HttpServletRequest,
        @RequestParam("targetData") targetData: String
    ): String {

        return Crypt.decrypt(targetData)
    }

}
