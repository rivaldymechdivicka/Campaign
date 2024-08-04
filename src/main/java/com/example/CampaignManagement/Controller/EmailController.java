package com.example.CampaignManagement.Controller;

import com.example.CampaignManagement.Dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    @PostMapping("/sendEmail")
public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
    try {
        // Format the message as "to;subject;text"
        String to = String.join(",", emailRequest.getTo()); // Join emails with commas
        String message = String.format("%s;%s;%s", to, emailRequest.getSubject(), emailRequest.getText());

        // Send the message to the Redis topic
        redisTemplate.convertAndSend(topic.getTopic(), message);

        // Return a success response
        return ResponseEntity.status(HttpStatus.OK).body("Email request sent to queue!");
    } catch (Exception e) {
        // Log the error
        e.printStackTrace();
        // Return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing email request");
    }
}
}
