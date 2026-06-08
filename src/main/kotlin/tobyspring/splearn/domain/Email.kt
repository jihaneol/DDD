package tobyspring.splearn.domain

@JvmInline
value class Email(val value: String) {
    init {
        require(EMAIL_REGEX.matches(value)) {
            "유효하지 않은 이메일 형식입니다: $value"
        }
    }

    companion object {
        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}