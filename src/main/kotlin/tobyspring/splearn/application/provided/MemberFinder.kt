package tobyspring.splearn.application.provided

import tobyspring.splearn.domain.Member

/**
 * 회원을 찾는다.
 */
interface MemberFinder {
    fun find(memberId: Long): Member
}