package tobyspring.splearn.application.provided

class DuplicateEmailException(
    override val message: String,
): RuntimeException()