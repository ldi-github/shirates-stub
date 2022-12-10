package shirates.stub.controllers.management

import com.google.gson.GsonBuilder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shirates.stub.commons.annotaions.ApiDescription
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

        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(StubDataManager.getInstanceJson(profileOrInstanceKeyPrefix = profile))
    }

    @ApiDescription("getInstanceInfo(API)")
    @GetMapping("/getInstanceInfo")
    fun getInstanceInfo(
        request: HttpServletRequest,
        @RequestParam("profileOrInstanceKeyPrefix") profileOrInstanceKeyPrefix: String?
    ): String {

        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(StubDataManager.getInstanceJson(profileOrInstanceKeyPrefix = profileOrInstanceKeyPrefix))
    }

    @ApiDescription("getInstanceProfileMap(API)")
    @GetMapping("/getInstanceProfileMap")
    fun getInstanceProfileMap(
        request: HttpServletRequest
    ): String {

        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(StubDataManager.instanceProfileMap)
    }

    @ApiDescription("removeInstance(API)")
    @GetMapping("/removeInstance")
    fun removeInstance(
        request: HttpServletRequest,
        @RequestParam("profileOrInstanceKeyPrefix") profileOrInstanceKeyPrefix: String
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profileOrInstanceKeyPrefix)
        StubDataManager.removeInstance(instanceKey = instanceKey)

        return ""
    }

    @ApiDescription("resetInstance(API)")
    @GetMapping("/resetInstance")
    fun resetInstance(
        request: HttpServletRequest,
        @RequestParam("profileOrInstanceKeyPrefix") profileOrInstanceKeyPrefix: String?
    ) {
        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profileOrInstanceKeyPrefix)
        StubDataManager.resetStubDataManager(instanceKey = instanceKey)
    }

    @ApiDescription("resetDataPattern(API)")
    @GetMapping("/resetDataPattern")
    fun resetDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ) {
        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        Logger.info(message = "データパターンをリセットします。", instanceKey = instanceKey)
        DataPattern.resetDataPattern(instanceKey = instanceKey)
    }

    @ApiDescription("listDataPattern(API)")
    @GetMapping("/listDataPattern")
    fun listDataPattern(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        val list = DataPattern.listDataPattern(instanceKey = instanceKey)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(list)
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
            throw IllegalArgumentException("Specify urlPath or apiName.")

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
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
        return DataPattern.getDataPattern(instanceKey = instanceKey, urlPathOrApiName = urlPathOrApiName)
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
