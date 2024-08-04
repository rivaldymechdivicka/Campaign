package com.example.CampaignManagement.Dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageLogRequest {

    private String senderEmail;
    private String recipientEmail;
    private String message;
    private LocalDateTime sendAt;
    private LocalDateTime receivedAt;

}
