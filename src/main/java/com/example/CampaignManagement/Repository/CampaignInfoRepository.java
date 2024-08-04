package com.example.CampaignManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CampaignManagement.Entity.CampaignInfo;

@Repository
public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Long> {

} 
