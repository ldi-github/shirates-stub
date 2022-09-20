package shirates.stub.commons.logging

import java.util.*

object Logger {

    val lines = mutableListOf<LogLine>()
    var enableTrace = false
    var headerPrinted = false

    internal fun getLogLine(
        message: String,
        logType: LogType = LogType.NONE,
        threadId: Long = Thread.currentThread().id,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {
        val msg = message
            .replace("\r", "\\r")
            .replace("\n", "\\n")

        synchronized(this) {
            val logLine = LogLine(
                lineNumber = lines.count() + 1,
                logDateTime = Date(),
                tid = "$threadId",
                apiName = apiName,
                dataPattern = dataPattern,
                elapsedMillisecond = elapsedMillisecond,
                message = msg,
                logType = logType,
            )
            lines.add(logLine)
            return logLine
        }
    }

    /**
     * printHeader
     */
    private fun printHeader() {
        println(LogLine.getHeader())
    }

    /**
     * write
     */
    fun write(
        message: String,
        logType: LogType = LogType.NONE,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {

        val logLine = getLogLine(
            message = message,
            logType = logType,
            apiName = apiName,
            dataPattern = dataPattern,
            elapsedMillisecond = elapsedMillisecond,
        )

        synchronized(this) {
            if (headerPrinted.not()) {
                printHeader()
                headerPrinted = true
            }
            println(logLine.toString())
        }

        return logLine
    }

    /**
     * trace
     */
    fun trace(
        message: String,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {

        if (enableTrace.not()) return LogLine()

        return write(
            message = message,
            logType = LogType.TRACE,
            apiName = apiName,
            dataPattern = dataPattern,
            elapsedMillisecond = elapsedMillisecond,
        )
    }

    /**
     * info
     */
    fun info(
        message: String,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {

        return write(
            message = message,
            logType = LogType.INFO,
            apiName = apiName,
            dataPattern = dataPattern,
            elapsedMillisecond = elapsedMillisecond,
        )
    }

    /**
     * warn
     */
    fun warn(
        message: String,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {

        return write(
            message = message,
            logType = LogType.WARN,
            apiName = apiName,
            dataPattern = dataPattern,
            elapsedMillisecond = elapsedMillisecond,
        )
    }

    /**
     * error
     */
    fun error(
        message: String,
        apiName: String = "",
        dataPattern: String = "",
        elapsedMillisecond: Long? = null
    ): LogLine {

        return write(
            message = message,
            logType = LogType.ERROR,
            apiName = apiName,
            dataPattern = dataPattern,
            elapsedMillisecond = elapsedMillisecond,
        )
    }
}