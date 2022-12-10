package shirates.stub.commons.utilities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import shirates.stub.controllers.management.ManagementApiController

class ApiNameUtilTest {

    @Test
    fun setupApiNameMap() {

        // Arrange
        val controllers = mutableListOf(ManagementApiController::class)
        ApiNameUtil.setupApiNameMap(controllers = controllers)
        // Act, Assert
        assertThat(ApiNameUtil.ApiNameMap["/management/resetInstance"]).isEqualTo("resetInstance(API)")
    }

    @Test
    fun getApiName() {

        // Arrange
        val controllers = mutableListOf(ManagementApiController::class)
        ApiNameUtil.setupApiNameMap(controllers = controllers)
        // Act, Assert
        assertThat(ApiNameUtil.getApiName("/management/resetInstance")).isEqualTo("resetInstance(API)")
    }

    @Test
    fun getUrlPath() {

        // Arrange
        val controllers = mutableListOf(ManagementApiController::class)
        ApiNameUtil.setupApiNameMap(controllers = controllers)

        run {
            // Act
            val actual = ApiNameUtil.getUrlPath(urlPathOrApiName = "resetInstance(API)")
            // Assert
            assertThat(actual).isEqualTo("/management/resetInstance")
        }
        run {
            // Act
            val actual = ApiNameUtil.getUrlPath("http://stub1/not/registered/url")
            // Assert
            assertThat(actual).isEqualTo("/not/registered/url")
        }
        run {
            // Act
            val actual = ApiNameUtil.getUrlPath("https://stub1/not/registered/url")
            // Assert
            assertThat(actual).isEqualTo("/not/registered/url")
        }
        run {
            // Act, Assert
            val actual = ApiNameUtil.getUrlPath("httpsx://stub1/invalid/url")
            // Assert
            assertThat(actual).isEqualTo("Invalid url format. Specify url-path or url. \"httpsx://stub1/invalid/url\"")
        }
        run {
            // Act
            val actual = ApiNameUtil.getUrlPath("/not/registered/url")
            // Assert
            assertThat(actual).isEqualTo("/not/registered/url")
        }
    }

    @Test
    fun getDateTimeLabelFromStubFile() {

        // Arrange
        val controllers = mutableListOf(ManagementApiController::class)
        ApiNameUtil.setupApiNameMap(controllers = controllers)
        // Act
        val actual =
            ApiNameUtil.getDateTimeLabelFromStubFile(stubFileName = "mldata.lawson.co.jp~app~app-coupon~couponlist.json..20191126 165513.020.json.plain.json")
        // Assert
        assertThat(actual).isEqualTo("20191126 165513.020")
    }
}