package tobyspring.splearn

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.PasswordEncoder

@TestConfiguration
class SplearnTestConfiguration {

    @Bean
    fun emailSender(): EmailSender =
        EmailSender { email, subject, body ->
            println("Sending email $email to $subject")
        }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return MemberFixture.Companion.createPasswordEncoder()
    }
}