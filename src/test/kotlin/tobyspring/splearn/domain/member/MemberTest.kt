package tobyspring.splearn.domain.member

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class MemberTest {
    
    lateinit var member: Member
    val passwordEncoder: PasswordEncoder =
        MemberFixture.Companion.createPasswordEncoder()


    @BeforeEach
    fun setUp() {
        member = Member.register(
            MemberFixture.Companion.createMemberRegister("test1@naver.com"),
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
        assertThat(member.detail.activatedAt).isNull()
        member.active()
        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
        assertThat(member.detail.activatedAt).isNotNull()
    }

    @Test
    fun deactivated() {
        member.active()

        member.deactive()

        assertThrows<IllegalStateException> { member.deactive() }
        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
        assertThat(member.detail.deactivatedAt).isNotNull()

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
                MemberFixture.Companion.createMemberRegister("test"), passwordEncoder
            )
        }
        Member.register(
            MemberFixture.Companion.createMemberRegister("test1@naver.com"), passwordEncoder
        )
    }

    @Test
    fun updateInfo() {
        member.active()

        val request = MemberUpdateInfoRequest("Leo", "toby", "자기소개")
        member.updateInfo(request)

        assertThat(member.nickname).isEqualTo(request.nickname)
        assertEquals(member.detail.profile?.address, request.profileAddress)
    }

    @Test
    fun changePassword() {
    }
}