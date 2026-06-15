package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import java.time.LocalDateTime
import tobyspring.splearn.domain.AbstractEntity

/**
 * 멤버의 상세 정보들
 */
@Entity
class MemberDetail() : AbstractEntity() {
    var profile: Profile? = null
        protected set

    var introduction: String? = null
        protected set

    var registeredAt: LocalDateTime = LocalDateTime.now()
        protected set

    var activatedAt: LocalDateTime? = null
        protected set

    var deactivatedAt: LocalDateTime? = null
        protected set

    internal fun activateAt() {
        require(activatedAt == null) { "이미 ActivateAt이 설정되어 있습니다." }
        activatedAt = LocalDateTime.now()
    }

    internal fun deactivate() {
        require(deactivatedAt == null) { "이미 deactivateAtdl 설정되어 있습니다." }
        deactivatedAt = LocalDateTime.now()
    }

    internal fun updateInfo(updateInfo: MemberUpdateInfoRequest) {
        this.profile = Profile(updateInfo.profileAddress)
        this.introduction = updateInfo.introduction
    }

    companion object {
        internal fun create(): MemberDetail {
            return MemberDetail()
        }
    }
}
