package com.example.CampaignManagement.Dto;
import java.util.List;

import lombok.Data;

@Data
public class EmailRequest {

    private List<String> to; // Changed to a List of strings
    private String subject;
    private String text;

    
}
