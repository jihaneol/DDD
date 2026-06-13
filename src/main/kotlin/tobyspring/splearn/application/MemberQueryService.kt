package tobyspring.splearn.application

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.provided.MemberFinder
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member

/**
 * 조회, 변경을 분리하겠다.
 */
@Service
@Transactional
@Validated
class MemberQueryService(
    private val memberRepository: MemberRepository,
) : MemberFinder {

    override fun find(memberId: Long): Member =
        memberRepository.findById(memberId)
            ?: throw IllegalArgumentException("회원을 찾을 수 없습니다.")
}