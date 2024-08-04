package com.example.CampaignManagement.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "message_logs")
@Getter
@Setter
public class MessageLog implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "message_id")
    private Long messageId;


    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "message")
    private String message;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;
}
