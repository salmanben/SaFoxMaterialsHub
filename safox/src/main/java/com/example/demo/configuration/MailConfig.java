package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.example.demo.DTO.EmailSettingsDto;
import com.example.demo.service.EmailSettingsService;

@Configuration
public class MailConfig {

    private final EmailSettingsService emailSettingsService;

    public MailConfig(EmailSettingsService emailSettingsService) {
        this.emailSettingsService = emailSettingsService;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        EmailSettingsDto emailSettings = emailSettingsService.getEmailSettings();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        if(emailSettings == null) {
            return null;
        }
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setHost(emailSettings.getHost());
        mailSender.setPort(emailSettings.getPort());
        mailSender.setUsername(emailSettings.getUsername());
        mailSender.setPassword(emailSettings.getPassword());


        return mailSender;

    }

}