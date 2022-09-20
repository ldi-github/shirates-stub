package shirates.stub.entities

import shirates.stub.commons.utilities.StubFileNameUtil
import java.io.File

class StubFileItem(stubFile: File) {

    var urlPath: String
    var dataPatternName: String
    var stubFile: String
    var dateTimeLabel: String

    init {
        this.stubFile = stubFile.absolutePath
        urlPath = StubFileNameUtil.getUrlFromStubFile(stubFile.name).path
        dataPatternName = StubFileNameUtil.getDataPatternNameFromFile(stubFile)
        dateTimeLabel = StubFileNameUtil.getDateTimeLabelFromStubFile(stubFile.name)
    }
}