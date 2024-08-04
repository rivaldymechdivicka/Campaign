package com.example.CampaignManagement.Controller;

import com.example.CampaignManagement.Dto.CampaignInfoRequest;
import com.example.CampaignManagement.Dto.CampaignInfoResponse;
import com.example.CampaignManagement.Service.CampaignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:5173")
public class CampaignInfoController {

    private final CampaignInfoService campaignInfoService;

    @Autowired
    public CampaignInfoController(CampaignInfoService campaignInfoService) {
        this.campaignInfoService = campaignInfoService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignInfoResponse>> getAllCampaigns() {
        List<CampaignInfoResponse> campaigns = campaignInfoService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignInfoResponse> getCampaignById(@PathVariable("id") Long campaignId) {
        CampaignInfoResponse campaign = campaignInfoService.getCampaignById(campaignId);
        return ResponseEntity.ok(campaign);
    }

    @PostMapping
    public ResponseEntity<CampaignInfoResponse> addCampaign(@RequestBody CampaignInfoRequest campaignRequest) {
        CampaignInfoResponse newCampaign = campaignInfoService.addCampaign(campaignRequest);
        return new ResponseEntity<>(newCampaign, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignInfoResponse> updateCampaign(
            @PathVariable("id") Long campaignId,
            @RequestBody CampaignInfoRequest campaignRequest
    ) {
        CampaignInfoResponse updatedCampaign = campaignInfoService.updateCampaign(campaignId, campaignRequest);
        return ResponseEntity.ok(updatedCampaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("id") Long campaignId) {
        campaignInfoService.deleteCampaign(campaignId);
        return ResponseEntity.noContent().build();
    }
}
