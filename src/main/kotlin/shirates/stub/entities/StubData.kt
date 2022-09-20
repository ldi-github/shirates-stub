package shirates.stub.entities

class StubData(
    var apiName: String = "",
    var dataPattern: String = "",
    var filePath: String = "",
    var data: String = "",
    var encData: String = "",
) {

    /**
     * encrypted
     */
    val encrypted: Boolean
        get() {
            if (encData.isBlank()) {
                return false
            } else {
                return encData != data
            }
        }

    /**
     * toString
     */
    override fun toString(): String {

        if (encrypted) {
            return encData
        } else {
            return data
        }
    }
}