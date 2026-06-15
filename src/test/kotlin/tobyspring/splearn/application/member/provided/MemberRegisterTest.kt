package tobyspring.splearn.application.member.provided

import jakarta.persistence.EntityManager
import jakarta.validation.ConstraintViolationException
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.member.MemberFixture
import tobyspring.splearn.domain.member.MemberRegisterRequest

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@Transactional
class MemberRegisterTest(
    private val memberRegister: MemberRegister,
    private val entityManager: EntityManager,
) {
    @Test
    fun register() {
        val member = memberRegister.register(MemberFixture.createMemberRegister())

        assertNotNull(member.id)
        assertNotNull(member.detail)
    }

    @Test
    fun duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegister())

        assertThrows<DuplicateEmailException> { memberRegister.register(MemberFixture.createMemberRegister()) }
    }

    @Test
    fun activate() {
        var member = memberRegister.register(MemberFixture.createMemberRegister())
        entityManager.flush()
        entityManager.clear()

        member = memberRegister.activate(member.id)
        entityManager.flush()

        assertNotNull(member)
    }

    @Test
    fun memberRegisterRequestFail() {
        val invalid = MemberRegisterRequest("toby@naver.com", "haeo", "secret")

        assertThrows<ConstraintViolationException> {
            memberRegister.register(invalid)
        }
    }
}
