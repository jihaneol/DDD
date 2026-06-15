package tobyspring.splearn.domain.shared

import kotlin.test.Test
import org.junit.jupiter.api.Assertions

class EmailTest {

    @Test
    fun equality() {
        val email1 = Email("tobyspring@naver.com")
        val email2 = Email("tobyspring@naver.com")
        Assertions.assertEquals(email1, email2)
    }
}