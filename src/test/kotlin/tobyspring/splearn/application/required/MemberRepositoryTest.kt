package tobyspring.splearn.application.required

import jakarta.persistence.EntityManager
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture.Companion.createMemberRegister
import tobyspring.splearn.domain.MemberFixture.Companion.createPasswordEncoder
import tobyspring.splearn.domain.MemberStatus

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createMember() {
        val member = Member.register(
            createMemberRegister(),
            createPasswordEncoder()
        )

        memberRepository.save(member)

        entityManager.flush()

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
        assertThat(member.id).isNotNull()

    }
}