package com.example.CampaignManagement.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageLogResponse {

    private Long messageId;
    private String senderEmail;
    private String recipientEmail;
    private String message;
    private LocalDateTime sendAt;
    private LocalDateTime receivedAt;
    
}
