package shirates.stub.commons.logging

import java.text.SimpleDateFormat
import java.util.*

data class LogLine(
    var lineNumber: Int = 0,
    var logDateTime: Date = Date(),
    var apiName: String = "",
    var dataPattern: String = "",
    var tid: String = "",
    var instanceKey: String = "",
    var elapsedMillisecond: Long? = null,
    var message: String = "",
    var logType: LogType = LogType.NONE

) {

    companion object {
        private val dateFormatrer = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")

        /**
         * getHeader
         */
        fun getHeader(): String {

            return "lineNo\tlogDateTime\t[logType]\t<threadId>\t(instance)\tapiName\t{dataPattern}\telapsed(ms)\tmessage"
        }
    }

    /**
     * toString
     */
    override fun toString(): String {

        val dateTime = dateFormatrer.format(logDateTime)
        val api = if (apiName.isBlank()) "-" else apiName
        val ms = if (elapsedMillisecond == null) "-" else "(${elapsedMillisecond} ms)"
        return "$lineNumber\t$dateTime\t[$logType]\t<$tid>\t($instanceKey)\t$api\t{$dataPattern}\t$ms\t$message"
    }

}