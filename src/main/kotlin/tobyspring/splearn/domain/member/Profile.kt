package tobyspring.splearn.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Profile(
    @Column(name = "address", length = MAX_LENGTH)
    val address: String
) {
    init {
        require(PROFILE_REGEX.matches(address)) {
            "프로필 주소는 영문과 숫자로만 구성된 ${MAX_LENGTH}자 이하의 값이어야 합니다: $address"
        }
    }

    fun url(): String = "@$address"

    companion object {
        private const val MAX_LENGTH = 155
        private val PROFILE_REGEX = Regex("^[A-Za-z0-9]{1,$MAX_LENGTH}$")
    }
}
