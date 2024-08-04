package com.example.CampaignManagement.Controller;

import com.example.CampaignManagement.Dto.MessageLogRequest;
import com.example.CampaignManagement.Dto.MessageLogResponse;
import com.example.CampaignManagement.Service.MessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/message-logs")
public class MessageLogController {

    @Autowired
    private MessageLogService messageLogService;

    @PostMapping
    public ResponseEntity<MessageLogResponse> createMessageLog(@RequestBody MessageLogRequest messageLogRequest) {
        MessageLogResponse messageLogResponse = messageLogService.createMessageLog(messageLogRequest);
        return ResponseEntity.ok(messageLogResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageLogResponse> updateMessageLog(@PathVariable("id") Long messageId, @RequestBody MessageLogRequest messageLogRequest) {
        MessageLogResponse messageLogResponse = messageLogService.updateMessageLog(messageId, messageLogRequest);
        if (messageLogResponse != null) {
            return ResponseEntity.ok(messageLogResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageLog(@PathVariable("id") Long messageId) {
        messageLogService.deleteMessageLog(messageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageLogResponse> getMessageLogById(@PathVariable("id") Long messageId) {
        MessageLogResponse messageLogResponse = messageLogService.getMessageLogById(messageId);
        if (messageLogResponse != null) {
            return ResponseEntity.ok(messageLogResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MessageLogResponse>> getAllMessageLogs() {
        List<MessageLogResponse> messageLogResponses = messageLogService.getAllMessageLogs();
        return ResponseEntity.ok(messageLogResponses);
    }
}
