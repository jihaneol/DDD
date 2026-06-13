package tobyspring.splearn.application.provided

import kotlin.test.Test
import kotlin.test.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import tobyspring.splearn.SplearnTestConfiguration
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.MemberRegisterRequest

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@Transactional
class MemberRegisterTest(
    private val memberRegister: MemberRegister
) {
    @Test
    fun register() {
        val member = memberRegister.register(MemberFixture.createMemberRegister())

        assertNotNull(member.id)
    }

    @Test
    fun duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegister())

        assertThrows<DuplicateEmailException> { memberRegister.register(MemberFixture.createMemberRegister()) }
    }

    @Test
    fun memberRegisterRequestFail() {
        val invalid = MemberRegisterRequest("todbyna.ap", "han", "secret")

        memberRegister.register(invalid)
        
    }
}
