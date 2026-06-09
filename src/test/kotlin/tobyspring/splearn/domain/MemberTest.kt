package tobyspring.splearn.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class MemberTest {

    lateinit var member: Member
    val passwordEncoder: PasswordEncoder =
        object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password).equals(passwordHash)
            }
        }

    @BeforeEach
    fun setUp() {
        member = Member.register(
            MemberRegisterRequest(
                "toby@naver.com", "toby",
                "secret"
            ),
            passwordEncoder
        )
    }

    @Test
    fun createMember() {
        val member = member

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun active() {
        member.active()
        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun deactivated() {
        member.active()

        member.deactive()

        assertThrows<IllegalStateException> { member.deactive() }
        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivatedFail() {

        assertThrows<IllegalStateException> { member.deactive() }
    }

    @Test
    fun verifyPassword() {
        assertTrue { member.verifyPassword("secret", passwordEncoder) }
        assertFalse { member.verifyPassword("sdfkj", passwordEncoder) }

    }

    @Test
    fun isInvalidEmail() {
        assertThrows<IllegalArgumentException> {
            Member.register(
                MemberRegisterRequest(
                    "asdkjf",
                    "haneol",
                    "ksdjf"
                ), passwordEncoder
            )
        }
        Member.register(
            MemberRegisterRequest(
                "234@naver.com",
                "haneol",
                "ksdjf"
            ), passwordEncoder
        )
    }

    @Test
    fun changePassword() {
    }
}