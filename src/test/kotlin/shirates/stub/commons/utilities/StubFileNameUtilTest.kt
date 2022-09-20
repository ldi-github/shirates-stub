package shirates.stub.commons.utilities

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class StubFileNameUtilTest {

    @Test
    fun escape() {

        val requestUrl = "/app/reserve_coupon/list/couponlist.json"
        val expected = "~app~reserve_coupon~list~couponlist.json"
        val actual = StubFileNameUtil.escape(requestUrl)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun unescape() {

        // Arrange
        val requestUrl = "/app/reserve_coupon/list/couponlist.json"
        val escaped = StubFileNameUtil.escape(requestUrl)

        // Act
        val actual = StubFileNameUtil.unescape(escaped)

        // Assert
        val expected = requestUrl
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getUrlFromStubFile() {

        val stubFile =
            "src/test/data/shirates/stub/models/StubConfigTest/workspaces/workspace1/default/server1.example.com~customer~list..yyyyMMdd HHmmss.SSS.enc.json"
        val actual = StubFileNameUtil.getUrlFromStubFile(stubFile)
        assertThat(actual.path).isEqualTo("/customer/list")
    }

    /**
     * Expects returns first item in descending by file name when multiple matched
     */
    @Test
    fun findMatchedFile1() {

        // Arrange
        val dirPath = "src/test/data/shirates/utilities/StubFileNameUtilTest/001"
        val url = "server1.example.com/customer/list"
        val expected = "server1.example.com~customer~list..yyyyMMdd HHmmss.SSS.enc.json"
        // Act
        val actual = StubFileNameUtil.findMatchedFile(dirPath, url)
        // Assert
        assertThat(actual!!.endsWith(expected)).isTrue()
    }

    /**
     * Expects returns null when nothing matched
     */
    @Test
    fun findMatchedFile2() {

        // Arrange
        val dirPath = "src/test/data/shirates/utilities/StubFileNameUtilTest/002"
        val url = "/app/reserve_coupon/list/couponlist.json"
        val expected: String? = null
        // Act
        val actual = StubFileNameUtil.findMatchedFile(dirPath, url)
        // Assert
        assertThat(actual).isEqualTo(expected)
    }

    /**
     * Expects throws exception when directory doe not exist
     */
    @Test
    fun findMatchedFile3() {

        val url = "/app/reserve_coupon/list/couponlist.json"
        val dirPath = "test/laxstub/utilities/data/noexist"
        assertThatThrownBy {
            StubFileNameUtil.findMatchedFile(
                dirPath,
                url
            )
        }.isInstanceOf(FileNotFoundException::class.java)
    }

    @Test
    fun trim() {

        run {
            val path = "/app/"
            val actual = StubFileNameUtil.trim(path, "/")
            val expected = "/app"
            assertThat(actual).isEqualTo(expected)
        }

        run {
            val path = "/app"
            val actual = StubFileNameUtil.trim(path, "/")
            val expected = "/app"
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun getProjectDir() {

        val expected = System.getProperty("user.dir").replace("\\", "/")
        val actual = StubFileNameUtil.projectDir
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun getDateTimeLabelFromStubFile() {

        run {
            // Arrange
            val FILE = "server1.example.com~customer~list..20200206 111932.571.enc.json"
            // Act
            val actual = StubFileNameUtil.getDateTimeLabelFromStubFile(FILE)
            // Assert
            val expected = "20200206 111932.571"
            assertThat(actual).isEqualTo(expected)
        }

        run {
            // Arrange
            val FILE = "getPoint~.20200206 111932.571.enc.json"
            // Act
            val actual = StubFileNameUtil.getDateTimeLabelFromStubFile(FILE)
            // Assert
            val expected = ""
            assertThat(actual).isEqualTo(expected)
        }

        run {
            // Arrange
            val FILE = "getPoint~..20200206 111932"
            // Act
            val actual = StubFileNameUtil.getDateTimeLabelFromStubFile(FILE)
            // Assert
            val expected = ""
            assertThat(actual).isEqualTo(expected)
        }
    }
}
