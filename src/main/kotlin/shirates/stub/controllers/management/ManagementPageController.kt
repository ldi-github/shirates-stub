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
    fun dataPatternChanger(model: Model): String {

        val stubDataManager = StubDataManager.instance
        val list = mutableListOf<DataPatternSettingItem>()

        val group = stubDataManager.getUrlDataPatternList().groupBy { it.urlPath }
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

        return "/management/dataPatternChanger"
    }

    @ApiDescription("changeDataPattern(Page)")
    @GetMapping("/changeDataPattern")
    fun changeDataPattern(
        @RequestParam("urlPath") urlpath: String,
        @RequestParam("dataPatternName") dataPatternName: String
    ): String {

        DataPattern.setDataPattern(urlpath, dataPatternName)
        return "redirect:/management/dataPatternChanger"
    }

    @ApiDescription("changeAllDataPatternsToDefault(Page)")
    @GetMapping("/changeAllDataPatternsToDefault")
    fun changeDataPatternToDefault(): String {

        DataPattern.setAllUrlToDefault()
        return "redirect:/management/dataPatternChanger"
    }

    @ApiDescription("cryptTool(Page)")
    @GetMapping("/cryptTool")
    fun encodeTest(): String {

        return "/management/cryptTool"
    }

}
