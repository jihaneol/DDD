package tobyspring.splearn.domain

class Member(
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
        this.status = MemberStatus.ACTIVE
    }
}