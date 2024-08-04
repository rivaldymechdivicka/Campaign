package com.example.CampaignManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CampaignManagement.Entity.MessageLog;

@Repository
public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {

    
} 
