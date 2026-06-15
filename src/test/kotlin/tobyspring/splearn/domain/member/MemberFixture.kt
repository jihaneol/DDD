package tobyspring.splearn.domain.member

class MemberFixture {
    companion object {
        fun createPasswordEncoder(): PasswordEncoder = object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password).equals(passwordHash)
            }
        }

        fun createMemberRegister(): MemberRegisterRequest =
            createMemberRegister("toby@naver.com")

        fun createMemberRegister(email: String): MemberRegisterRequest = MemberRegisterRequest(
            email,
            "haneol",
            "password"
        )
    }

}