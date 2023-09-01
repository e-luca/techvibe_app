package com.projects.techvibe.email

import jakarta.mail.MessagingException
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) : EmailSender {

    companion object {
        private val logger = LoggerFactory.getLogger(EmailService::class.java)
    }

    @Async
    override fun send(to: String, emailContent: String) {
        try {
            createAndSendEmail(to, emailContent)
        } catch (e: MessagingException) {
            logger.debug("Failed to send email to user!", e)
            throw IllegalStateException("Failed to send email to user!")
        }
    }

    private fun createAndSendEmail(to: String, content: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, "utf-8")
        helper.setText(content, true)
        helper.setTo(to)
        helper.setSubject("TechVibe: Registration confirmation token")
        helper.setFrom("user.service@techvibe.com")
    }
}
