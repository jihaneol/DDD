package tobyspring.splearn.application.member.provided

import jakarta.validation.Valid
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberRegisterRequest

interface MemberRegister {
    fun register(@Valid registerRequest: MemberRegisterRequest): Member

    fun activate(memberId: Long): Member
}