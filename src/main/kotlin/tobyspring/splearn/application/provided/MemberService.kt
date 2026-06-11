package tobyspring.splearn.application.provided

import org.springframework.stereotype.Service
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest
import tobyspring.splearn.domain.PasswordEncoder

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder
) : MemberRegister {
    override fun register(registerRequest: MemberRegisterRequest): Member {
        memberRepository.findByEmail(Email(registerRequest.email))?.let {
            throw DuplicateEmailException("이미 존재합니다.")
        }

        val member = Member.register(registerRequest, passwordEncoder)
        memberRepository.save(member)

        emailSender.send(member.email, subject = "제목을 입력", body = "내용을 입력")

        return member
    }
}