package com.example.CampaignManagement.Entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "campaign")
@Getter
@Setter
public class CampaignInfo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "campaign_code")
    private String campaignCode;

    @Column(name = "campaign_address")
    private String campaignAddress;

    @Column(name = "campaign_email")
    private String campaignEmail;

    @Column(name = "campaign_phone")
    private String campaignPhone;

    @Column(name = "campaign_fax")
    private String campaignFax;

    @Column(name = "campaign_npwp")
    private String campaignNpwp;

    @Column(name = "campaign_site")
    private String campaignSite;

    @Column(name = "development_board")
    private String developmentBoard;

    @Column(name = "business_field")
    private String businessField;

    @Column(name = "sector")
    private String sector;

    @Column(name = "sub_sector")
    private String subSector;

    @Column(name = "industry")
    private String industry;

    @Column(name = "sub_industry")
    private String subIndustry;

    @Column(name = "securities_administration_bureau")
    private String securitiesAdministrationBureau;

    @Column(name = "campaign_recording")
    private Date campaignRecording;

    @Column(name = "is_active")
    private Boolean isActive;

}