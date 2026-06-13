package tobyspring.splearn.domain

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EmailTest {

    @Test
    fun equality() {
        val email1 = Email("tobyspring@naver.com")
        val email2 = Email("tobyspring@naver.com")
        assertEquals(email1, email2)
    }
}