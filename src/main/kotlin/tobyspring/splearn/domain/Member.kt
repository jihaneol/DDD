package tobyspring.splearn.domain

class Member private constructor(
    email: String,
    nickname: String,
    passwordHash: String,
) {
    var email: String = email
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
            email: String, nickname: String, password: String, passwordEncoder: PasswordEncoder
        ): Member =
            Member(email, nickname, passwordEncoder.encode(password))
    }
}