package tobyspring.splearn.application.member

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.member.provided.DuplicateEmailException
import tobyspring.splearn.application.member.provided.MemberFinder
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.application.member.required.MemberRepository
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest
import tobyspring.splearn.domain.member.MemberRegisterRequest
import tobyspring.splearn.domain.member.PasswordEncoder
import tobyspring.splearn.domain.shared.Email

/**
 * 조회, 변경을 분리하겠다.
 */
@Service
@Transactional
@Validated
class MemberModifyService(
    private val memberFinder: MemberFinder,
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder
) : MemberRegister {
    override fun register(registerRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(registerRequest)

        val member = Member.register(registerRequest, passwordEncoder)

        memberRepository.save(member)

        sendWelcomeEmail(member)

        return member
    }

    override fun activate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.active()

        return memberRepository.save(member)
    }

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(email = member.email, subject = "제목을 입력", body = "내용을 입력")
    }

    private fun checkDuplicateEmail(registerRequest: MemberRegisterRequest) {
        memberRepository.findByEmail(Email(registerRequest.email))?.let {
            throw DuplicateEmailException("이미 회원이 존재합니다.")
        }
    }

    override fun deactivate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.deactive()

        return memberRepository.save(member)
    }

    override fun updateInfo(
        memberId: Long,
        memberInfoUpdateRequest: MemberInfoUpdateRequest
    ): Member {
        val member = memberFinder.find(memberId)

        member.updateInfo(memberInfoUpdateRequest)

        return memberRepository.save(member)
    }
}