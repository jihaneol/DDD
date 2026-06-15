package tobyspring.splearn.application.member.provided

class DuplicateEmailException(
    override val message: String,
) : RuntimeException()