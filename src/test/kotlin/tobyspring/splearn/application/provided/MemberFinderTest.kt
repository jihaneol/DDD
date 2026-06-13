package tobyspring.splearn.application.provided

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.MemberFixture

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@Transactional
class MemberFinderTest(
    private val memberFinder: MemberFinder,
    private val entityManager: EntityManager,
    private val memberRegister: MemberRegister,
) {

    @Test
    fun find() {
        val member = memberRegister.register(MemberFixture.createMemberRegister())
        entityManager.flush()
        entityManager.clear()

        val found = memberFinder.find(member.id)
        entityManager.flush()

        assertEquals(found.id, member.id)
    }
}