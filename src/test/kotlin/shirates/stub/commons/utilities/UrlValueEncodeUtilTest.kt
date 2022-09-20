package shirates.stub.commons.utilities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlValueEncodeUtilTest {

    @Test
    fun replace_json() {

        // Arrange
        val input = """
{
  "URL1": "http://stub1/app/stubtest/images/ファイル名1.png"
  "URL2": "http://stub1/app/stubtest/images/ファイル名2.png"
}
        """.trimIndent()

        // Act
        val actual = UrlValueEncodeUtil.replace(input = input)
        // Assert
        val expected = """
{
  "URL1": "http://stub1/app/stubtest/images/%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB%E5%90%8D1.png"
  "URL2": "http://stub1/app/stubtest/images/%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB%E5%90%8D2.png"
}
        """.trimIndent()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun replace_xml() {

        // Arrange
        val input = """
<xml>
    <ITEM>
        <URL>http://stub1/app/stubtest/images/ファイル名1.png</URL>
        <INFO url="http://stub1/app/stubtest/images/ファイル名2.png"/>
    </ITEM>
</xml>
        """.trimIndent()
        // Act
        val actual = UrlValueEncodeUtil.replace(input = input)
        // Assert
        val expected = """
<xml>
    <ITEM>
        <URL>http://stub1/app/stubtest/images/%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB%E5%90%8D1.png</URL>
        <INFO url="http://stub1/app/stubtest/images/%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB%E5%90%8D2.png"/>
    </ITEM>
</xml>
        """.trimIndent()
        // Assert
        assertThat(actual).isEqualTo(expected)
    }


}