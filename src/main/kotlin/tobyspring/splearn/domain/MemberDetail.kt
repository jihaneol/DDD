package tobyspring.splearn.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

/**
 * 단계적으로 도메인에서 응집도 있게 접근해 나가야 좋다. 유지보수에
 */
@Entity
class MemberDetail(
    profile: String,
    introduction: String,
    registeredAt: LocalDateTime = LocalDateTime.now(),
    activatedAt: LocalDateTime = LocalDateTime.now(),
    deactivatedAt: LocalDateTime = LocalDateTime.now(),
) : AbstractEntity() {
    var profile: String = profile
        protected set
    var introduction: String = introduction
        protected set
    var registeredAt: LocalDateTime = registeredAt
        protected set
    var activatedAt: LocalDateTime = activatedAt
        protected set
    var deactivatedAt: LocalDateTime = deactivatedAt
        protected set
}