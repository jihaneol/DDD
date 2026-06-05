package tobyspring.splearn.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class MemberTest {
    @Test
    fun createMember() {
        val member = Member("toby@naver.com", "toby", "secret")

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun active() {
        val member = Member("toby@naver.com", "toby", "secret")
        member.active()
    }
}