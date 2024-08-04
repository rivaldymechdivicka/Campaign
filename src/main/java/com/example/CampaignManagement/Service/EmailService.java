package com.example.CampaignManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendBulkEmail(String recipients, String subject, String text) {
        try {
            // Log data awal
            System.out.println("Original recipients: " + recipients);

            // Membagi string email yang diterima menjadi array alamat email
            String[] emailAddresses = recipients.split(",");

            // Log data setelah pemisahan
            System.out.println("Parsed email addresses: " + Arrays.toString(emailAddresses));

            for (String email : emailAddresses) {
                // Trim dan log setiap email
                email = email.trim();
                System.out.println("Sending to: " + email);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject(subject);
                message.setText(text);
                message.setTo(email);

                emailSender.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

