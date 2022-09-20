package shirates.stub.commons.extensions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

class StringDateExtensionTest {

    @Test
    fun toDate() {

        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val actual = "20210801".toDate()
            assertThat(actual).isEqualTo(expected)
        }

        assertThatThrownBy {
            "2021/13/01".toDate()
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid date. (date=2021/13/01)")
    }

    @Test
    fun toDateOrNull_auto() {

        // Length 8
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val actual = "20210801".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Length 10
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val actual = "2021/08/01".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Length 14
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val actual = "20210801123456".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Length 17
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val actual = "20210801123456123".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Length 19
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val actual = "2021/08/01 12:34:56".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Length 23
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val actual = "2021/08/01 12:34:56.123".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // Invalid date
        run {
            val actual = "2021/13/01".toDateOrNull()
            assertThat(actual).isNull()
        }

        // 2021/1/23
        run {
            val expected = SimpleDateFormat("yyyy/M/d").parse("2021/1/23")
            val actual = "2021/1/23".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // 2021/1/23 1:2:3
        run {
            val expected = SimpleDateFormat("yyyy/M/d H:m:s").parse("2021/1/23 1:2:3")
            val actual = "2021/1/23 1:2:3".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // 2021/1/23 1:2:3.45
        run {
            val expected = SimpleDateFormat("yyyy/M/d H:m:s.S").parse("2021/1/23 1:2:3.45")
            val actual = "2021/1/23 1:2:3.45".toDateOrNull()
            assertThat(actual).isEqualTo(expected)
        }

        // 1/23 -> null
        run {
            val actual = "1/23".toDateOrNull()
            assertThat(actual).isNull()
        }
    }

    @Test
    fun toDateOrNull_with_format() {

        // yyyyMMdd
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val actual = "20210801".toDateOrNull("yyyyMMdd")
            assertThat(actual).isEqualTo(expected)
        }

        // yyyy/MM/dd
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val actual = "2021/08/01".toDateOrNull("yyyy/MM/dd")
            assertThat(actual).isEqualTo(expected)
        }

        // yyyyMMddHHmmss
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val actual = "20210801123456".toDateOrNull("yyyyMMddHHmmss")
            assertThat(actual).isEqualTo(expected)
        }

        // yyyyMMddHHmmss.SSS
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val actual = "20210801123456123".toDateOrNull("yyyyMMddHHmmssSSS")
            assertThat(actual).isEqualTo(expected)
        }

        // yyyy/MM/dd HH:mm:ss
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val actual = "2021/08/01 12:34:56".toDateOrNull("yyyy/MM/dd HH:mm:ss")
            assertThat(actual).isEqualTo(expected)
        }

        // yyyy/MM/dd HH:mm:ss.SSS
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val actual = "2021/08/01 12:34:56.123".toDateOrNull("yyyy/MM/dd HH:mm:ss.SSS")
            assertThat(actual).isEqualTo(expected)
        }

        // Invalid date
        run {
            val actual = "2021/13/01".toDateOrNull("yyyy/MM/dd")
            assertThat(actual).isNull()
        }

        // 2021/1/23
        run {
            val expected = SimpleDateFormat("yyyy/M/d").parse("2021/1/23")
            val actual = "2021/1/23".toDateOrNull("yyyy/M/d")
            assertThat(actual).isEqualTo(expected)
        }

        // 2021/1/23 1:2:3
        run {
            val expected = SimpleDateFormat("yyyy/M/d H:m:s").parse("2021/1/23 1:2:3")
            val actual = "2021/1/23 1:2:3".toDateOrNull("yyyy/M/d H:m:s")
            assertThat(actual).isEqualTo(expected)
        }

        // 2021/1/23 1:2:3.45
        run {
            val expected = SimpleDateFormat("yyyy/M/d H:m:s.S").parse("2021/1/23 1:2:3.45")
            val actual = "2021/1/23 1:2:3.45".toDateOrNull("yyyy/M/d H:m:s.S")
            assertThat(actual).isEqualTo(expected)
        }

        // 1/23 -> 1970/1/23
        run {
            val actual = "1/23".toDateOrNull("M/d")
            assertThat(actual).isEqualTo("1970/1/23".toDate("yyyy/M/d"))
        }
    }

    @Test
    fun toDateOrNull_with_regex() {

        // yyyyMMdd
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val pattern = "(?<year>[0-9]{4})(?<month>[0-9]{2})(?<day>[0-9]{2})"
            val actual = "20210801".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        // yyyy/MM/dd
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd").parse("2021/08/01")
            val pattern = "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2})"
            val actual = "2021/08/01".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        // yyyyMMddHHmmss
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val pattern =
                "(?<year>[0-9]{4})(?<month>[0-9]{2})(?<day>[0-9]{2})(?<hour>[0-9]{2})(?<minute>[0-9]{2})(?<second>[0-9]{2})"
            val actual = "20210801123456".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        // yyyyMMddHHmmss.SSS
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val pattern =
                "(?<year>[0-9]{4})(?<month>[0-9]{2})(?<day>[0-9]{2})(?<hour>[0-9]{2})(?<minute>[0-9]{2})(?<second>[0-9]{2})(?<millisecond>[0-9]{3})"
            val actual = "20210801123456123".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        // yyyy/MM/dd HH:mm:ss
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2021/08/01 12:34:56")
            val pattern =
                "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2}) (?<hour>[0-9]{2}):(?<minute>[0-9]{2}):(?<second>[0-9]{2})"
            val actual = "2021/08/01 12:34:56".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        // yyyy/MM/dd HH:mm:ss.SSS
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val pattern =
                "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2}) (?<hour>[0-9]{2}):(?<minute>[0-9]{2}):(?<second>[0-9]{2}).(?<millisecond>[0-9]{3})"
            val actual = "2021/08/01 12:34:56.123".toDateOrNull(pattern.toRegex())
            assertThat(actual?.compareTo(expected)).isEqualTo(0)
        }

        "2021/06/31".toDateOrNull(regex = "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2})".toRegex())

        // Invalid date
        run {
            val pattern = "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2})"
            val actual = "2021/13/01".toDateOrNull(pattern.toRegex())
            assertThat(actual).isNull()
        }

        // Missing year
        run {
            val pattern = "(?<year>[0-9]{4})/(?<month>[0-9]{2})/(?<day>[0-9]{2})"
            val actual = "12/01".toDateOrNull(pattern.toRegex())
            assertThat(actual).isNull()
        }

        // Free format
        run {
            val expected = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse("2021/08/01 12:34:56.123")
            val pattern = """
year=(?<year>[0-9]{4})
month=(?<month>[0-9]{2})
day=(?<day>[0-9]{2})
hour=(?<hour>[0-9]{2})
minute=(?<minute>[0-9]{2})
second=(?<second>[0-9]{2})
millisecond=(?<millisecond>[0-9]{3})
            """.trimIndent()
            val actual = """
year=2021
month=08
day=01
hour=12
minute=34
second=56
millisecond=123
            """.trimIndent().toDateOrNull(pattern.toRegex())
            assertThat(actual).isEqualTo(expected)
        }
    }
}