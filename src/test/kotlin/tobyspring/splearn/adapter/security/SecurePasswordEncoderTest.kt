package tobyspring.splearn.adapter.security

import kotlin.test.Test
import kotlin.test.assertTrue

class SecurePasswordEncoderTest {

    @Test
    fun encode() {
        val encoder = SecurePasswordEncoder()
        val password = "secure"

        val hash = encoder.encode(password)

        assertTrue { encoder.matches(password, hash) }
    }

}