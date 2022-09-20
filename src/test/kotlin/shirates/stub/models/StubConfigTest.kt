package shirates.stub.models

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import shirates.stub.commons.utilities.StubFileNameUtil
import java.nio.file.Path


class StubConfigTest {

    @Test
    fun initWithoutFileArg() {

        // Act
        val config = StubConfig()
        // Assert
        val path = Path.of("data/config/stubConfig.json").toAbsolutePath()
        assertThat(config.configFile).isEqualTo(path.toString())
    }

    @Test
    fun init_missingKey() {

        // Arrange
        val file = "src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig_missingKey.json"
        val path = Path.of(file).toAbsolutePath().toString()
        // Act, Assert
        assertThatThrownBy {
            StubConfig(stubConfigFile = file)
        }.hasMessage("Key not found. (key=keyFile, configFile=$path)")
    }

    @Test
    fun init_missingWorkspaceName() {

        // Arrange
        val file = "src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig_missingWorkspaceName.json"
        val path = Path.of(file).toAbsolutePath().toString()
        // Act, Assert
        assertThatThrownBy {
            StubConfig(stubConfigFile = file)
        }.hasMessage("Key not found. (key=workspaceName, configFile=$path)")
    }

    @Test
    fun init_default() {

        // Act
        val config =
            StubConfig(stubConfigFile = "src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig.json")
        // Assert
        assertThat(config.outputRequestBody).isEqualTo(true)
        assertThat(config.trace).isEqualTo(false)
    }

    @Test
    fun init_1() {

        // Act
        val config =
            StubConfig(stubConfigFile = "src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig1.json")
        // Assert
        assertThat(config.outputRequestBody).isEqualTo(true)
        assertThat(config.trace).isEqualTo(true)
    }

    @Test
    fun init_false() {

        // Act
        val config =
            StubConfig(stubConfigFile = "src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig2.json")
        // Assert
        assertThat(config.outputRequestBody).isEqualTo(false)
        assertThat(config.trace).isEqualTo(false)
    }

    @Test
    fun init() {

        val project = StubFileNameUtil.projectDir
        val config = StubConfig("src/test/data/shirates/stub/models/StubConfigTest/config/stubConfig.json")
        assertThat(config.keyFile).isEqualTo("$project/src/test/data/shirates/stub/models/StubConfigTest/config/keys/staging.keys.json")
        assertThat(config.workspaces).isEqualTo("$project/src/test/data/shirates/stub/models/StubConfigTest/workspaces")
        assertThat(config.workspaceName).isEqualTo("workspace1")
        assertThat(config.workspaceDir).isEqualTo("$project/src/test/data/shirates/stub/models/StubConfigTest/workspaces/workspace1")

        // keys
        assertThat(config.stubKeys.description).isEqualTo("description1")
        assertThat(config.stubKeys.API_KEY).isEqualTo("apikey1")
        assertThat(config.stubKeys.API_KEY2).isEqualTo("apikey2")
        assertThat(config.stubKeys.API_KEY3).isEqualTo("apikey3")
    }


}
