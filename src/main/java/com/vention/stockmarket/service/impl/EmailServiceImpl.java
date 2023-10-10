package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    @Scheduled(cron = "0 24 13 * * ?")
    public void sendEmailToCompanies() {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo("abboskhujaakramov@gmail.com");
        mailMessage.setText("This is a test message");
        mailMessage.setSubject("Stock user details");

        javaMailSender.send(mailMessage);

    }
}
