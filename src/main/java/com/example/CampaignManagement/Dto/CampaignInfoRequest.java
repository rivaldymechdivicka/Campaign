package com.example.CampaignManagement.Dto;
import java.sql.Date;
import lombok.Data;

@Data
public class CampaignInfoRequest {
    
    private String campaignName;
    private String campaignCode;
    private String campaignAddress;
    private String campaignEmail;
    private String campaignPhone;
    private String campaignFax;
    private String campaignNpwp;
    private String campaignSite;
    private String developmentBoard;
    private String businessField;
    private String sector;
    private String subSector;
    private String industry;
    private String subIndustry;
    private String securitiesAdministrationBureau;
    private Date campaignRecording;
    private Boolean isActive;

}
