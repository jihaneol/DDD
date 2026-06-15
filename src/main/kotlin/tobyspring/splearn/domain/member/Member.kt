package tobyspring.splearn.domain.member

import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import lombok.ToString
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache
import tobyspring.splearn.domain.AbstractEntity
import tobyspring.splearn.domain.shared.Email

/**
 * 단계적으로 도메인에서 응집도 있게 접근해 나가야 좋다. 유지보수에
 * memberDetail 과 함께 포함되는 멤버 에그리거트 루트
 * 데이터 변경의 목적을 위해 하나의 단위로 취급되는 연관된 객체들의 클러스터
 */
@Entity
@ToString(callSuper = true, exclude = ["detail"])
@NaturalIdCache
class Member private constructor(
    email: Email,
    nickname: String,
    passwordHash: String,
    detail: MemberDetail,
) : AbstractEntity() {
    /**
     * 영속성 컨텍스트에서 찾아서 성능 개선
     * db unique와 같은 효과
     * 하이버네이트에서 제공
     */
    @Embedded
    @NaturalId
    var email: Email = email
        protected set

    var nickname: String = nickname
        protected set

    var passwordHash: String = passwordHash
        protected set

    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.PENDING
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    var detail: MemberDetail = detail
        protected set

    fun active() {
        check(status == MemberStatus.PENDING) { "Pending 상태가 아닙니다." }

        status = MemberStatus.ACTIVE
        detail.activateAt()
    }

    fun deactive() {
        check(status == MemberStatus.ACTIVE) { "Active 상태가 아닙니다." }

        status = MemberStatus.DEACTIVATED
        detail.deactivate()
    }

    fun updateInfo(updateInfo: MemberUpdateInfoRequest) {
        this.nickname = updateInfo.nickname

        detail.updateInfo(updateInfo)
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, passwordHash)
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    companion object {
        /**
         * 정적 팩토리 메소드의 장점
         */
        fun register(
            createRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder
        ): Member {
            with(createRequest) {
                return Member(
                    email = Email(email),
                    nickname = nickname,
                    passwordHash = passwordEncoder.encode(password),
                    detail = MemberDetail.create()
                )
            }
        }
    }
}
