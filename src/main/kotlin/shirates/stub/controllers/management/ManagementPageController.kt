package shirates.stub.controllers.management

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import shirates.stub.commons.annotaions.ApiDescription
import shirates.stub.commons.utilities.ApiNameUtil
import shirates.stub.models.DataPattern
import shirates.stub.models.StubDataManager
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/management")
class ManagementPageController {

    data class DataPatternSettingItem(
        var groupNo: Int,
        var subNo: Int = 1,
        var urlPath: String,
        var dataPatternName: String,
        var activeDataPatternName: String = "",
        var apiName: String,
        var filePath: String
    ) {
        val encrypted: Boolean
            get() {
                return filePath.contains(".enc.")
            }
        val urlPathDecrypted: String
            get() {
                return if (encrypted) "$urlPath?plain"
                else ""
            }
        val urlPathDisplayed: String
            get() {
                return if (isHeaderLine) urlPath else ""
            }
        val urlPathDecryptedDisplayed: String
            get() {
                return if (encrypted && urlPathDisplayed.isNotBlank()) "[decrypt]"
                else ""
            }
        val isHeaderLine: Boolean
            get() {
                return subNo == 1
            }
        val cssClass: String
            get() {
                return if (isHeaderLine) "header-line" else ""
            }
        val apiNameDisplayed: String
            get() {
                return if (isHeaderLine) apiName else ""
            }
    }

    @ApiDescription("dataPatternChanger(Page)")
    @GetMapping("/dataPatternChanger")
    fun dataPatternChanger(
        request: HttpServletRequest,
        model: Model,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (profile != null && instanceKey == "") {
            throw IllegalArgumentException("profile not registered. (profile=$profile)")
        }
        val stubDataManager = StubDataManager.getInstance(profileOrInstanceKeyPrefix = instanceKey)
        val list = mutableListOf<DataPatternSettingItem>()

        val group = stubDataManager.getUrlDataPatternList(forceRefresh = true).groupBy { it.urlPath }
        val keys = group.keys
        for ((groupName, items) in group) {
            items.forEachIndexed { index, item ->
                val activeDataPatternName = stubDataManager.getActiveDataPatternName((item.urlPath))
                val settingItem = DataPatternSettingItem(
                    groupNo = keys.indexOf(groupName) + 1,
                    subNo = index + 1,
                    urlPath = item.urlPath,
                    dataPatternName = item.dataPatternName,
                    activeDataPatternName = activeDataPatternName,
                    apiName = ApiNameUtil.getApiName(item.urlPath),
                    filePath = stubDataManager.getDataFilePathFromUrl(item.urlPath) ?: ""
                )
                list.add(settingItem)
            }
        }

        model.addAttribute("list", list)
        val profileLabel = if (profile.isNullOrBlank()) "" else "($profile)"
        model.addAttribute("profileLabel", profileLabel)

        return "/management/dataPatternChanger"
    }

    @ApiDescription("changeDataPattern(Page)")
    @GetMapping("/changeDataPattern")
    fun changeDataPattern(
        request: HttpServletRequest,
        @RequestParam("urlPath") urlPath: String,
        @RequestParam("dataPatternName") dataPatternName: String,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (profile != null && instanceKey == "") {
            throw IllegalArgumentException("profile not registered. (profile=$profile)")
        }

        DataPattern.setDataPattern(
            instanceKey = instanceKey,
            urlPathOrApiName = urlPath,
            dataPatternName = dataPatternName
        )
        return "redirect:/management/dataPatternChanger"
    }

    @ApiDescription("changeAllDataPatternsToDefault(Page)")
    @GetMapping("/changeAllDataPatternsToDefault")
    fun changeDataPatternToDefault(
        request: HttpServletRequest,
        @RequestParam("profile") profile: String?
    ): String {

        val instanceKey = StubDataManager.getInstanceKey(profileOrInstanceKeyPrefix = profile)
        if (profile != null && instanceKey == "") {
            throw IllegalArgumentException("profile not registered. (profile=$profile)")
        }

        DataPattern.setAllUrlToDefault(instanceKey = instanceKey)
        var redirect = "redirect:/management/dataPatternChanger"
        if (profile.isNullOrBlank().not()) {
            redirect += "?profile=$profile"
        }
        return redirect
    }

    @ApiDescription("管理APIテスト画面")
    @GetMapping("/managementApiTest")
    fun managementApiTest(
        model: Model,
        @RequestParam("profile") profile: String?
    ): String {

        val m = StubDataManager.instanceProfileMap
        val list = m.keys.map { InstanceProfile(it, m[it]!!) }.toMutableList()
        list.add(0, InstanceProfile("", "default"))

        model.addAttribute("list", list)
        model.addAttribute("profile", profile)

        return "/management/managementApiTest"
    }

    data class InstanceProfile(
        var instanceKey: String = "",
        var profile: String = "",
    )

    @ApiDescription("cryptTool(Page)")
    @GetMapping("/cryptTool")
    fun encodeTest(): String {

        return "/management/cryptTool"
    }

}
