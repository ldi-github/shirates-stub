package shirates.stub.commons.extensions

import org.apache.commons.lang3.time.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * toDate
 *
 * @param [format] parsePatterns for DateUtils.parseDateStrictly(str, parsePatterns)
 * When omitted or null, [format] is automatically set according to string length.
 * length -> format
 * 8 -> "yyyyMMdd"
 * 10 -> "yyyy/MM/dd"
 * 14 -> "yyyyMMddHHmmss"
 * 17 -> "yyyyMMddHHmmssSSS"
 * 19 -> "yyyy/MM/dd HH:mm:ss"
 * 23 -> "yyyy/MM/dd HH:mm:ss.SSS"
 * else -> ""
 */
fun String.toDate(format: String? = null): Date {

    val date = this.toDateOrNull(format = format)
        ?: throw IllegalArgumentException("Invalid date. (date=$this)")

    return date
}

/**
 * toDate
 *
 * @param [regex] is regular expression with name.
 * ex. regex="(?<year>)[0-9]{4})/(?<month>)[0-9]{2})/(?<day>)[0-9]{2})"
 */
fun String.toDate(regex: Regex): Date {

    val date = this.toDateOrNull(regex = regex)
        ?: throw IllegalArgumentException("Invalid date. (text=$this)")

    return date
}

/**
 * toDateOrNull
 *
 * @param [format] parsePatterns for DateUtils.parseDateStrictly(str, parsePatterns)
 * When omitted or null, [format] is automatically set according to string length.
 * length -> format
 * 8 -> "yyyyMMdd"
 * 14 -> "yyyyMMddHHmmss"
 * 17 -> "yyyyMMddHHmmssSSS"
 * else -> ""
 */
fun String.toDateOrNull(format: String? = null): Date? {

    val f: String = format ?: when (this.length) {
        8 -> "yyyyMMdd"
        14 -> "yyyyMMddHHmmss"
        17 -> "yyyyMMddHHmmssSSS"
        else -> ""
    }

    if (f == "") {
        try {
            val date =
                this.toDate("(?<year>[0-9]{4})/(?<month>[0-9]+)/(?<day>[0-9]+)\\s?(?<hour>[0-9]+)?:?(?<minute>[0-9]+)?:?(?<second>[0-9]+)?\\.?(?<millisecond>[0-9]+)?".toRegex())
            return date
        } catch (t: Throwable) {
            return null
        }
    }

    try {
        return DateUtils.parseDateStrictly(this, f)
    } catch (t: Throwable) {
        return null
    }
}

/**
 * toDateOrNull
 *
 * @param [regex] is regular expression with name.
 * ex. regex="(?<year>)[0-9]{4})/(?<month>)[0-9]{2})/(?<day>)[0-9]{2})"
 */
fun String.toDateOrNull(regex: Regex): Date? {

    fun MatchResult.get(name: String): String? {
        try {
            return this.groups[name]?.value
        } catch (t: Throwable) {
            return null
        }
    }

    fun MatchResult.getInt(name: String): Int {

        val value = this.get(name)
        if (value.isNullOrBlank()) return 0
        val v = value.toIntOrNull() ?: throw IllegalArgumentException("Invalid value. (name=$name, value=$value")
        return v
    }

    val match = regex.find(this)
        ?: return null

    val year = match.getInt(name = "year")
    val month = match.getInt(name = "month")
    val day = match.getInt(name = "day")
    val hour = match.getInt(name = "hour")
    val minute = match.getInt(name = "minute")
    val second = match.getInt(name = "second")
    val millisecond = match.getInt(name = "millisecond")

    val builder = Calendar.Builder().setLenient(false)
        .setDate(year, month - 1, day)
        .setTimeOfDay(hour, minute, second, millisecond)
    val cal: Calendar
    try {
        cal = builder.build()
    } catch (t: Throwable) {
        return null
    }
    val date = DateUtils.setMilliseconds(cal.time, millisecond)

    // Verification
    val expected = "$year/$month/$day $hour:$minute:$second.$millisecond"
    val actual = SimpleDateFormat("yyyy/M/d H:m:s.S").format(date)
    if (expected == actual)
        return date
    else
        return null
}
