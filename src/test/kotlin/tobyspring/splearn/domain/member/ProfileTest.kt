package tobyspring.splearn.domain.member

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class ProfileTest {

    @Test
    fun equality() {
        val profile1 = Profile("TobySpring123")
        val profile2 = Profile("TobySpring123")

        assertEquals(profile1, profile2)
    }

    @Test
    fun invalidProfile() {
        assertThrows(IllegalArgumentException::class.java) {
            Profile("toby-spring")
        }

        assertThrows(IllegalArgumentException::class.java) {
            Profile("토비스프링")
        }

        assertThrows(IllegalArgumentException::class.java) {
            Profile("a".repeat(156))
        }
    }

    @Test
    fun url() {
        val profile1 = Profile("TobySpring123")
        assertEquals(profile1.url(), "@TobySpring123")
    }
}
