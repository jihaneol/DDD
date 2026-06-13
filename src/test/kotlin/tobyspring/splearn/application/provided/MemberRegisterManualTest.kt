package tobyspring.splearn.application.provided

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.util.ReflectionTestUtils
import tobyspring.splearn.application.MemberModifyService
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.MemberStatus


/**
 * 기능이 달라지면 테스트도 변경되야한다?
 * 그럼 이상함을 느껴야한다.
 * service가 아닌 interface에 대해 테스트를 진행한다.
 * 스프링 사용하지 않은 테스트.
 */
class MemberRegisterManualTest {
    @Test
    fun registerTestStub() {
        val register: MemberRegister = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = EmailSenderStub(),
            passwordEncoder = MemberFixture.createPasswordEncoder(),
            memberFinder = MemberFinderStub()
        )

        val member = register.register(MemberFixture.createMemberRegister())

        assertEquals(member.id, 1L)
        assertEquals(member.status, MemberStatus.PENDING)
    }

    @Test
    fun registerTestMock() {
        val emailSenderMock = EmailSenderMock()

        val register: MemberRegister = MemberModifyService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder(),
            memberFinder = MemberFinderStub()
        )

        val member = register.register(MemberFixture.createMemberRegister())

        assertEquals(member.id, 1L)
        assertEquals(member.status, MemberStatus.PENDING)

        assertEquals(emailSenderMock.emails.first(), member.email)
        assertEquals(emailSenderMock.emails.size, 1)
    }

    /**
     * 코틀린에서는 eq는 반환값을 Null로 보내는데
     * 자바에서는 null을 보지 않으니까 괜찮다.
     * 그렇기 때문에 코틀린에서는 kotlin mockito를 사용해야 한다.
     * -> value 때문에 String으로 보기 때문에 문제가 발생한다..
     * mockito 대신 mockk 로 사용하자.
     */
    @Test
    fun registerTestMockito() {
        val emailSenderMock = mock<EmailSender>()
        val memberFinderMock = mock<MemberFinder>()

        val register: MemberRegister = MemberModifyService(
            memberFinder = memberFinderMock,
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberRegister())

        assertEquals(member.id, 1L)
        assertEquals(member.status, MemberStatus.PENDING)

        verify(emailSenderMock).send(
            eq(member.email),
            any(),
            any()
        )
    }
    class MemberFinderStub : MemberFinder {
        override fun find(memberId: Long): Member {
           return Member.register(
               MemberFixture.createMemberRegister(),
               MemberFixture.createPasswordEncoder()
           )
        }
    }

    class MemberRepositoryStub : MemberRepository {
        override fun save(member: Member): Member {
            ReflectionTestUtils.setField(member, "id", 1L)
            return member
        }

        override fun findByEmail(email: Email): Member? {
            return null
        }

        override fun findById(memberId: Long): Member? {
            return null
        }
    }

    class EmailSenderStub : EmailSender {
        override fun send(email: Email, subject: String, body: String) {
        }
    }

    class EmailSenderMock : EmailSender {
        val emails = mutableListOf<Email>()

        override fun send(email: Email, subject: String, body: String) {
            emails.add(email)
        }
    }
}