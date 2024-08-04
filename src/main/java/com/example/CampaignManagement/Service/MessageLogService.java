package com.example.CampaignManagement.Service;

import com.example.CampaignManagement.Dto.MessageLogRequest;
import com.example.CampaignManagement.Dto.MessageLogResponse;
import com.example.CampaignManagement.Entity.MessageLog;
import com.example.CampaignManagement.Repository.MessageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageLogService {

    @Autowired
    private MessageLogRepository messageLogRepository;

    public MessageLogResponse createMessageLog(MessageLogRequest messageLogRequest) {
        MessageLog messageLog = new MessageLog();
        messageLog.setSenderEmail(messageLogRequest.getSenderEmail());
        messageLog.setRecipientEmail(messageLogRequest.getRecipientEmail());
        messageLog.setMessage(messageLogRequest.getMessage());
        messageLog.setSendAt(messageLogRequest.getSendAt());
        messageLog.setReceivedAt(messageLogRequest.getReceivedAt());
        MessageLog savedMessageLog = messageLogRepository.save(messageLog);
        return convertToResponse(savedMessageLog);
    }

    public MessageLogResponse updateMessageLog(Long messageId, MessageLogRequest messageLogRequest) {
        Optional<MessageLog> messageLogOptional = messageLogRepository.findById(messageId);
        if (messageLogOptional.isPresent()) {
            MessageLog messageLog = messageLogOptional.get();
            messageLog.setSenderEmail(messageLogRequest.getSenderEmail());
            messageLog.setRecipientEmail(messageLogRequest.getRecipientEmail());
            messageLog.setMessage(messageLogRequest.getMessage());
            messageLog.setSendAt(messageLogRequest.getSendAt());
            messageLog.setReceivedAt(messageLogRequest.getReceivedAt());
            MessageLog updatedMessageLog = messageLogRepository.save(messageLog);
            return convertToResponse(updatedMessageLog);
        }
        return null;
    }

    public void deleteMessageLog(Long messageId) {
        messageLogRepository.deleteById(messageId);
    }

    public MessageLogResponse getMessageLogById(Long messageId) {
        Optional<MessageLog> messageLogOptional = messageLogRepository.findById(messageId);
        return messageLogOptional.map(this::convertToResponse).orElse(null);
    }

    public List<MessageLogResponse> getAllMessageLogs() {
        List<MessageLog> messageLogs = messageLogRepository.findAll();
        return messageLogs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private MessageLogResponse convertToResponse(MessageLog messageLog) {
        if (messageLog == null) {
            return null; 
        }
        MessageLogResponse response = new MessageLogResponse();
        response.setMessageId(messageLog.getMessageId());
        response.setSenderEmail(messageLog.getSenderEmail());
        response.setRecipientEmail(messageLog.getRecipientEmail());
        response.setMessage(messageLog.getMessage());
        response.setSendAt(messageLog.getSendAt());
        response.setReceivedAt(messageLog.getReceivedAt());
        return response;
    }
}
