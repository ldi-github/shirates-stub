package shirates.stub.models

import java.util.*

object Crypt {

    /**
     * Replace this lambda with your own encryption logic.
     */
    var encryptLogic: (data: String, key: String) -> String = { data, key ->
        // This is just a demonstration. Do not use in production system as-is.
        Base64.getEncoder().encodeToString(data.toByteArray())
    }

    /**
     * Replace this lambda with your own decryption logic.
     */
    var decryptLogic: (encryptedData: String, key: String) -> String = { encryptedData, key ->
        // This is just a demonstration. Do not use in production system as-is.
        Base64.getDecoder().decode(encryptedData).toString(Charsets.UTF_8)
    }

    /**
     * encryptionEnabled
     */
    var encryptionEnabled: Boolean = true

    /**
     * encrypt
     */
    fun encrypt(targetString: String, encKey: String = StubConfig.API_KEY): String {
        if (encryptionEnabled) {
            val encrypted = encryptLogic(targetString, encKey)
            return encrypted
        } else {
            return targetString
        }
    }

    /**
     * decrypt
     */
    fun decrypt(encodedString: String, encKey: String = StubConfig.API_KEY): String {

        if (encryptionEnabled) {
            val decrypted = decryptLogic(encodedString, encKey)
            return decrypted
        } else {
            return encodedString
        }
    }
}
