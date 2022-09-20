package shirates.stub.commons.utilities

import shirates.stub.commons.extensions.format
import java.time.Duration
import java.util.*

/**
 * StopWatch
 */
class StopWatch {

    var startTime: Long = System.currentTimeMillis()
    var endTime: Long? = null
    val laps = mutableListOf<LapEntry>()

    init {
        start()
    }

    /**
     * elapsedMillis
     */
    val elapsedMillis: Long
        get() {
            if (endTime != null) {
                return endTime!! - startTime
            } else {
                return System.currentTimeMillis() - startTime
            }
        }

    /**
     * elapsedSeconds
     */
    val elapsedSeconds: Double
        get() {
            val millis = elapsedMillis
            return millis.toDouble() / 1000
        }

    /**
     * duration
     */
    val duration: Duration
        get() {
            return Duration.ofMillis(this.elapsedMillis)
        }

    /**
     * start
     */
    fun start(): StopWatch {
        laps.clear()
        startTime = System.currentTimeMillis()
        endTime = null

        return this
    }

    /**
     * lap
     */
    fun lap(label: String): StopWatch {

        val time = System.currentTimeMillis()

        val invalidLabel = laps.any { it.label == label } || label == "start" || label == "end"

        val label2 =
            if (invalidLabel) "${label}_${time}"
            else label
        val entry = LapEntry(label = label2, time = time, elapsedMilliSeconds = time - startTime)
        laps.add(entry)

        endTime = System.currentTimeMillis()

        return this
    }

    /**
     * stop
     */
    fun stop(): StopWatch {

        endTime = System.currentTimeMillis()

        return this
    }

    /**
     * getLapList
     */
    fun getLapList(): List<LapEntry> {

        val list = mutableListOf<LapEntry>()
        list.add(LapEntry(label = "start", time = startTime, elapsedMilliSeconds = 0))
        list.addAll(laps)
        val last = list.last()
        if (endTime != null && endTime != last.time) {
            list.add(LapEntry(label = "end", time = endTime!!, elapsedMilliSeconds = endTime!! - startTime))
        }
        return list
    }

    /**
     * toString
     */
    override fun toString(): String {
        return duration.label
    }

    /**
     * LapEntry
     */
    data class LapEntry(
        var label: String,
        var time: Long,
        var elapsedMilliSeconds: Long
    ) {
        override fun toString(): String {

            val date = Date(time)
            return "${label}\t${date.format("yyyy/MM/dd HH:mm:ss.SSS")}\t${elapsedMilliSeconds}"
        }

        /**
         * durationFrom
         */
        fun durationFrom(fromTime: Long): Duration {

            return Duration.ofMillis(time - fromTime)
        }
    }
}