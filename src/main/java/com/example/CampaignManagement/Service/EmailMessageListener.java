package com.example.CampaignManagement.Service;

import com.example.CampaignManagement.Util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageListener {

    private final EmailService emailService;

    @Autowired
    public EmailMessageListener(EmailService emailService) {
        this.emailService = emailService;
    }

    public void handleMessage(String message) {
        try {
            // Log the received message
            System.out.println("Received message from Redis: " + message);
            
            // Memastikan karakter aneh tidak ada
            message = message.replaceAll("[^\\p{Print}]", "");
            
            String[] parts = message.split(";");
            if (parts.length >= 3) {
                String recipients = parts[0];
                String subject = parts[1];
                String text = parts[2];
    
                // Memvalidasi alamat email
                String[] emailAddresses = recipients.split(",");
                boolean allValid = true;
                for (String email : emailAddresses) {
                    if (!EmailValidator.isValidEmail(email.trim())) {
                        System.err.println("Invalid email address: " + email.trim());
                        allValid = false;
                    }
                }
                if (allValid) {
                    emailService.sendBulkEmail(recipients, subject, text);
                } else {
                    System.err.println("One or more email addresses are invalid.");
                }
            } else {
                System.err.println("Message does not have the expected format: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
