package tobyspring.splearn.application.member.required

import jakarta.persistence.EntityManager
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberFixture.Companion.createMemberRegister
import tobyspring.splearn.domain.member.MemberFixture.Companion.createPasswordEncoder
import tobyspring.splearn.domain.member.MemberStatus

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
        entityManager.clear()

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
        assertThat(member.id).isNotNull()

        val member2 = memberRepository.findById(member.id)!!

        assertNotNull(member2.detail.registeredAt)
    }

    @Test
    fun duplicateMemberEmail() {
        val member = Member.register(
            createMemberRegister(),
            createPasswordEncoder()
        )
        memberRepository.save(member)

        val member2 = Member.register(
            createMemberRegister(),
            createPasswordEncoder()
        )


        assertThrows<DataIntegrityViolationException> { memberRepository.save(member2) }
    }
}