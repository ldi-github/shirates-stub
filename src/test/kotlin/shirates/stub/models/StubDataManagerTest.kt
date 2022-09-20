package shirates.stub.models

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


class StubDataManagerTest {

    val CONFIG_FILE = "/src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig.json"

    val CUSTOMER_LIST_URL = "/customer/list"
    val PRODUCT_LIST_URL = "/product/list"

    @Test
    fun init() {

        // Arrange, Act
        val m = StubDataManager.setup(StubConfig(CONFIG_FILE))
        // Assert
        assertThat(m.dataPatternMap.count()).isEqualTo(2)
        assertThat(m.startupDataPatternMap.count()).isEqualTo(m.dataPatternMap.count())
        assertThat(m.startupDataPatternMap).containsAllEntriesOf(m.dataPatternMap)
    }

    @Test
    fun saveStartupDataPatternMap_resumeStartupDataPatternMap() {

        // Arrange, Act
        val m = StubDataManager.setup(StubConfig(CONFIG_FILE))  // saveStartupDataPatternMap is called
        // Assert
        assertThat(m.dataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("default")
        assertThat(m.startupDataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("default")

        // Arrange
        m.setDataPatternName(CUSTOMER_LIST_URL, "customer/1")
        assertThat(m.dataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("customer/1")
        assertThat(m.startupDataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("default")
        // Act
        m.resumeStartupDataPatternMap()
        // Assert
        assertThat(m.dataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("default")
        assertThat(m.startupDataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("default")

        // Act
        m.setDataPatternName(CUSTOMER_LIST_URL, "customer/1")
        m.saveStartupDataPatternMap()
        // Assert
        assertThat(m.dataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("customer/1")
        assertThat(m.startupDataPatternMap[CUSTOMER_LIST_URL]).isEqualTo("customer/1")
    }

    @Test
    fun getActiveDataPatternName() {

        run {
            // Arrange
            val m = StubDataManager.setup(StubConfig(CONFIG_FILE))
            // Act
            val actual = m.getActiveDataPatternName(CUSTOMER_LIST_URL)
            // Assert
            assertThat(actual).isEqualTo("default")
        }

        run {
            // Arrange
            val m = StubDataManager.setup(StubConfig(CONFIG_FILE))
            // Act
            val actual = m.getActiveDataPatternName("/noexist/url")
            // Assert
            assertThat(actual).isEqualTo("")
        }
    }

    @Test
    fun setDataPatternName_getDataPatternName() {
        // Arrange
        val m = StubDataManager.setup(StubConfig(CONFIG_FILE))

        // Binds data-pattern-name to the URL
        run {
            // Act
            val dataPatternName = "customer/1"
            m.setDataPatternName(CUSTOMER_LIST_URL, dataPatternName)
            val actual = m.getActiveDataPatternName(CUSTOMER_LIST_URL)
            // Assert
            assertThat(actual).isEqualTo(dataPatternName)
        }

        // Binds another data-pattern-name to the URL
        run {
            // Act
            val dataPatternName = "customer/0"
            m.setDataPatternName(CUSTOMER_LIST_URL, dataPatternName)
            val actual = m.getActiveDataPatternName(CUSTOMER_LIST_URL)
            // Assert
            assertThat(actual).isEqualTo(dataPatternName)
        }

        // Binds data-pattern-name to the URL
        run {
            // Act
            val dataPatternName = "product/1"
            m.setDataPatternName(PRODUCT_LIST_URL, dataPatternName)
            val actual = m.getActiveDataPatternName(PRODUCT_LIST_URL)
            // Assert
            assertThat(actual).isEqualTo(dataPatternName)

            val actual2 = m.getActiveDataPatternName(CUSTOMER_LIST_URL)
            val expected2 = "customer/0"
            assertThat(actual2).isEqualTo(expected2)
        }

        // Binds invalid data-pattern-name to the URL
        run {
            // Act
            val dataPatternName = "invalid"
            assertThatThrownBy {
                m.setDataPatternName(PRODUCT_LIST_URL, dataPatternName)
            }.isInstanceOf(java.lang.IllegalArgumentException::class.java)
                .hasMessage("Could not set dataPattern to urlPath. (dataPatternName=$dataPatternName, urlPath=$PRODUCT_LIST_URL)")
        }
    }

    @Test
    fun getDataFilePathFromUrl() {

        // Arrange
        val m = StubDataManager.setup(StubConfig(CONFIG_FILE))
        val dataPatternName = "product/1"
        m.setDataPatternName(PRODUCT_LIST_URL, dataPatternName)

        // Act
        val actual = m.getDataFilePathFromUrl(PRODUCT_LIST_URL)

        // Assert
        val expected =
            m.stubConfig.workspaceDir + "/product/1/server1.example.com~product~list..yyyyMMdd HHmmss.SSS.enc.json"
        assertThat(actual).isEqualTo(expected)
    }

}
