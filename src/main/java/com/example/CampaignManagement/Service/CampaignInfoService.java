package com.example.CampaignManagement.Service;

import com.example.CampaignManagement.Dto.CampaignInfoRequest;
import com.example.CampaignManagement.Dto.CampaignInfoResponse;
import com.example.CampaignManagement.Entity.CampaignInfo;
import com.example.CampaignManagement.Repository.CampaignInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CampaignInfoService {

    private final CampaignInfoRepository campaignInfoRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CampaignInfoService(CampaignInfoRepository campaignInfoRepository,
                               RedisTemplate<String, Object> redisTemplate) {
        this.campaignInfoRepository = campaignInfoRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<CampaignInfoResponse> getAllCampaigns() {
        List<CampaignInfo> campaigns = campaignInfoRepository.findAll();
        return campaigns.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CampaignInfoResponse getCampaignById(Long campaignId) {
        // Periksa apakah data ada di Redis
        String redisKey = "campaign:" + campaignId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            CampaignInfoResponse cachedCampaign = (CampaignInfoResponse) redisTemplate.opsForValue().get(redisKey);
            return cachedCampaign;
        }

        // Ambil data dari database jika tidak ada di Redis
        CampaignInfo campaign = campaignInfoRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + campaignId));
        CampaignInfoResponse campaignResponse = convertToDto(campaign);

        // Simpan data ke Redis
        redisTemplate.opsForValue().set(redisKey, campaignResponse, 10, TimeUnit.MINUTES);

        return campaignResponse;
    }

    public CampaignInfoResponse addCampaign(CampaignInfoRequest campaignRequest) {
        CampaignInfo campaign = new CampaignInfo();
        BeanUtils.copyProperties(campaignRequest, campaign);
        CampaignInfo savedCampaign = campaignInfoRepository.save(campaign);
        CampaignInfoResponse campaignResponse = convertToDto(savedCampaign);

        // Simpan data ke Redis
        redisTemplate.opsForValue().set("campaign:" + campaignResponse.getCampaignId(), campaignResponse, 10, TimeUnit.MINUTES);

        return campaignResponse;
    }

    public CampaignInfoResponse updateCampaign(Long campaignId, CampaignInfoRequest campaignRequest) {
        CampaignInfo campaign = campaignInfoRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + campaignId));

        BeanUtils.copyProperties(campaignRequest, campaign);
        CampaignInfo updatedCampaign = campaignInfoRepository.save(campaign);
        CampaignInfoResponse campaignResponse = convertToDto(updatedCampaign);

        // Update data di Redis
        redisTemplate.opsForValue().set("campaign:" + campaignResponse.getCampaignId(), campaignResponse, 10, TimeUnit.MINUTES);

        return campaignResponse;
    }

    public void deleteCampaign(Long campaignId) {
        CampaignInfo campaign = campaignInfoRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found with ID: " + campaignId));

        campaignInfoRepository.delete(campaign);

        // Hapus data dari Redis
        redisTemplate.delete("campaign:" + campaignId);
    }

    private CampaignInfoResponse convertToDto(CampaignInfo campaign) {
        CampaignInfoResponse dto = new CampaignInfoResponse();
        BeanUtils.copyProperties(campaign, dto);
        return dto;
    }
}
