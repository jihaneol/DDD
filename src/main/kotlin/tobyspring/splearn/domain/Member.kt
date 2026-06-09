package tobyspring.splearn.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * 단계적으로 도메인에서 응집도 있게 접근해 나가야 좋다. 유지보수에
 */
@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    email: Email,
    nickname: String,
    passwordHash: String,
) {
    var email: Email = email
        protected set

    var nickname: String = nickname
        protected set

    var passwordHash: String = passwordHash
        protected set

    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.PENDING
        protected set

    fun active() {
        check(status == MemberStatus.PENDING) { "Pending 상태가 아닙니다." }

        status = MemberStatus.ACTIVE
    }

    fun deactive() {
        check(status == MemberStatus.ACTIVE) { "Active 상태가 아닙니다." }

        status = MemberStatus.DEACTIVATED
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
                    passwordHash = passwordEncoder.encode(password)
                )
            }
        }


    }
}