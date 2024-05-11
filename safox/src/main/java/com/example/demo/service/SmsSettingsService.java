package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.SmsSettingsDto;
import com.example.demo.model.SmsSettings;
import com.example.demo.repository.SmsSettingsRepository;
import java.util.List;
@Service
public class SmsSettingsService {
    
    private final SmsSettingsRepository SmsSettingsRepository;

    public SmsSettingsService(SmsSettingsRepository SmsSettingsRepository) {
        this.SmsSettingsRepository = SmsSettingsRepository;
    }


    public SmsSettingsDto getSmsSettings(){
       List<SmsSettings> ListSmsSettings = SmsSettingsRepository.findAll();
        if (ListSmsSettings.size() == 0)
            return new SmsSettingsDto("", "", "");
        SmsSettings SmsSettings = ListSmsSettings.get(0);
        return new SmsSettingsDto(SmsSettings.getAccountSid(), SmsSettings.getAuthToken(), SmsSettings.getFromNumber());
    }

    public void updateSmsSettings(SmsSettingsDto SmsSettingsDto){
        List<SmsSettings> ListSmsSettings = SmsSettingsRepository.findAll();
        if (ListSmsSettings.size() == 0) {
            SmsSettings SmsSettings = new SmsSettings(SmsSettingsDto.getAccountSid(), SmsSettingsDto.getAuthToken(), SmsSettingsDto.getFromNumber());
            SmsSettingsRepository.save(SmsSettings);
            return;
        }
        SmsSettings SmsSettings = SmsSettingsRepository.findAll().get(0);
        
        SmsSettings.setAccountSid(SmsSettingsDto.getAccountSid());
        SmsSettings.setAuthToken(SmsSettingsDto.getAuthToken());
        SmsSettings.setFromNumber(SmsSettingsDto.getFromNumber());
        SmsSettingsRepository.save(SmsSettings);
        
    }
}
