package tobyspring.splearn.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import tobyspring.splearn.domain.MemberFixture.Companion.createMemberRegister
import tobyspring.splearn.domain.MemberFixture.Companion.createPasswordEncoder

class MemberTest {

    lateinit var member: Member
    val passwordEncoder: PasswordEncoder =
        createPasswordEncoder()


    @BeforeEach
    fun setUp() {
        member = Member.register(
            createMemberRegister("test1@naver.com"),
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
        assertTrue { member.verifyPassword("password", passwordEncoder) }
        assertFalse { member.verifyPassword("wrongPassword", passwordEncoder) }

    }

    @Test
    fun isInvalidEmail() {
        assertThrows<IllegalArgumentException> {
            Member.register(
                createMemberRegister("test"), passwordEncoder
            )
        }
        Member.register(
            createMemberRegister("test1@naver.com"), passwordEncoder
        )
    }


    @Test
    fun changePassword() {
    }
}