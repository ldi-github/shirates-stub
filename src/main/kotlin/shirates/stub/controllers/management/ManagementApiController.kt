package shirates.stub.controllers.management

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shirates.stub.commons.annotaions.ApiDescription
import shirates.stub.commons.logging.Logger
import shirates.stub.entities.HttpException
import shirates.stub.entities.UrlDataPattern
import shirates.stub.models.Crypt
import shirates.stub.models.DataPattern
import shirates.stub.models.StubDataManager

@RestController
@RequestMapping("/management", produces = ["application/json"])
class ManagementApiController {

    @ApiDescription("resetStubDataManager(API)")
    @GetMapping("/resetStubDataManager")
    fun resetStubDataManager() {

        StubDataManager.resetStubDataManager()
    }

    @ApiDescription("resetDataPattern(API)")
    @GetMapping("/resetDataPattern")
    fun resetDataPattern() {

        Logger.info("Resetting data patterns")
        DataPattern.resetDataPattern()
    }

    @ApiDescription("listDataPattern(API)")
    @GetMapping("/listDataPattern")
    fun listDataPattern(): List<UrlDataPattern> {

        return DataPattern.listDataPattern()
    }

    @ApiDescription("setDataPattern(API)")
    @GetMapping("/setDataPattern")
    fun setDataPattern(
        @RequestParam("urlPath") urlPath: String?,
        @RequestParam("apiName") apiName: String?,
        @RequestParam("dataPatternName") dataPatternName: String
    ): String {

        val urlPathOrApiName = urlPath ?: apiName ?: throw IllegalArgumentException("Specify urlPath or apiName.")
        try {
            return DataPattern.setDataPattern(urlPathOrApiName, dataPatternName)
        } catch (t: IllegalArgumentException) {
            throw HttpException(
                status = HttpStatus.BAD_REQUEST,
                message = "dataPattern corresponding to urlPath not found. (apiName=$apiName, dataPatternName=$dataPatternName)"
            )
        }
    }

    @ApiDescription("getDataPattern(API)")
    @GetMapping("/getDataPattern")
    fun getDataPattern(
        @RequestParam("urlPath") urlPath: String?,
        @RequestParam("apiName") apiName: String?
    ): String {

        val urlPathOrApiName = urlPath ?: apiName ?: throw IllegalArgumentException("Specify urlPath or apiName.")
        return DataPattern.getDataPattern(urlPathOrApiName)
    }

    @ApiDescription("encrypt(API)")
    @PostMapping("/encrypt")
    fun encrypt(
        @RequestParam("targetData") targetData: String
    ): String {

        return Crypt.encrypt(targetData)
    }

    @ApiDescription("decrypt(API)")
    @PostMapping("/decrypt")
    fun decrypt(
        @RequestParam("targetData") targetData: String
    ): String {

        return Crypt.decrypt(targetData)
    }


}
