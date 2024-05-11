package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.EmailSettingsDto;
import com.example.demo.model.EmailSettings;
import com.example.demo.repository.EmailSettingsRepository;
import java.util.List;
@Service
public class EmailSettingsService {
    
    private final EmailSettingsRepository EmailSettingsRepository;

    public EmailSettingsService(EmailSettingsRepository EmailSettingsRepository) {
        this.EmailSettingsRepository = EmailSettingsRepository;
    }


    public EmailSettingsDto getEmailSettings(){
       List<EmailSettings> ListEmailSettings = EmailSettingsRepository.findAll();
        if (ListEmailSettings.size() == 0)
            return new EmailSettingsDto("", 0, "", "", "");
        EmailSettings EmailSettings = ListEmailSettings.get(0);
        return new EmailSettingsDto(EmailSettings.getHost(), EmailSettings.getPort(), EmailSettings.getUsername(), EmailSettings.getPassword(), EmailSettings.getEncryption());
    }

    public void updateEmailSettings(EmailSettingsDto EmailSettingsDto){
        List<EmailSettings> ListEmailSettings = EmailSettingsRepository.findAll();
        if (ListEmailSettings.size() == 0) {
            EmailSettings EmailSettings = new EmailSettings();
            EmailSettings.setHost(EmailSettingsDto.getHost());
            EmailSettings.setPort(EmailSettingsDto.getPort());
            EmailSettings.setUsername(EmailSettingsDto.getUsername());
            EmailSettings.setPassword(EmailSettingsDto.getPassword());
            EmailSettings.setEncryption(EmailSettingsDto.getEncryption());
            EmailSettingsRepository.save(EmailSettings);
            return;
        }
        EmailSettings EmailSettings = EmailSettingsRepository.findAll().get(0);
        EmailSettings.setHost(EmailSettingsDto.getHost());
        EmailSettings.setPort(EmailSettingsDto.getPort());
        EmailSettings.setUsername(EmailSettingsDto.getUsername());
        EmailSettings.setPassword(EmailSettingsDto.getPassword());
        EmailSettings.setEncryption(EmailSettingsDto.getEncryption());
        EmailSettingsRepository.save(EmailSettings);
        
    }
}
