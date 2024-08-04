package com.example.CampaignManagement.Dto;

import lombok.Data;

@Data
public class CustomerResponse {
    
    private Long customerId;
    private String name;
    private String email;
    private String countryCode;

    // Constructors, getters, setters
}
