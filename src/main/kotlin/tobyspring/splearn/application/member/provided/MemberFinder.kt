package tobyspring.splearn.application.member.provided

import tobyspring.splearn.domain.member.Member

/**
 * 회원을 찾는다.
 */
interface MemberFinder {
    fun find(memberId: Long): Member
}