package tobyspring.splearn.adapter.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import tobyspring.splearn.domain.PasswordEncoder

@Component
class SecurePasswordEncoder : PasswordEncoder {
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()
    override fun encode(password: String): String {
        return requireNotNull(bCryptPasswordEncoder.encode(password)) {
            "Password should not be null"
        }
    }

    override fun matches(password: String, passwordHash: String): Boolean {
        return bCryptPasswordEncoder.matches(password, passwordHash)
    }
}
