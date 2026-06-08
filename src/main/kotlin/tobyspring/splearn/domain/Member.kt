package tobyspring.splearn.domain

/**
 * 단계적으로 도메인에서 응집도 있게 접근해 나가야 좋다. 유지보수에
 */
class Member private constructor(
    email: Email,
    nickname: String,
    passwordHash: String,
) {
    var email: Email = email
        private set

    var nickname: String = nickname
        private set

    var passwordHash: String = passwordHash
        private set

    var status: MemberStatus = MemberStatus.PENDING
        private set

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
        fun create(
            createRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder
        ): Member {
            with(createRequest) {
                return Member(Email(email), nickname, passwordEncoder.encode(password))
            }
        }


    }
}