package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.model.GeneralSettings;
import com.example.demo.repository.GeneralSettingsRepository;
import java.util.List;
@Service
public class GeneralSettingsService {
    
    private final GeneralSettingsRepository generalSettingsRepository;

    public GeneralSettingsService(GeneralSettingsRepository generalSettingsRepository) {
        this.generalSettingsRepository = generalSettingsRepository;
    }


    public GeneralSettingsDto getGeneralSettings(){
      List<GeneralSettings> ListGeneralSettings = generalSettingsRepository.findAll();
        if (ListGeneralSettings.size() == 0)
            return new GeneralSettingsDto("", "",  0, 0, 0);
        GeneralSettings generalSettings = ListGeneralSettings.get(0);
        return new GeneralSettingsDto(generalSettings.getContact_email(), 
        generalSettings.getContact_phone(), 
        generalSettings.getInsurance_price(), generalSettings.getPromotion_threshold()
        , generalSettings.getPromotion_discount());
    }

    public void updateGeneralSettings(GeneralSettingsDto generalSettingsDto){
        List<GeneralSettings> ListGeneralSettings = generalSettingsRepository.findAll();
        if (ListGeneralSettings.size() == 0) {
            GeneralSettings generalSettings = new GeneralSettings();
            generalSettings.setContact_email(generalSettingsDto.getContact_email());
            generalSettings.setContact_phone(generalSettingsDto.getContact_phone());
            generalSettings.setInsurance_price(generalSettingsDto.getInsurance_price());
            generalSettings.setPromotion_threshold(generalSettingsDto.getPromotion_threshold());
            generalSettings.setPromotion_discount(generalSettingsDto.getPromotion_discount());

            generalSettingsRepository.save(generalSettings);
            return;
        }
        GeneralSettings generalSettings = generalSettingsRepository.findAll().get(0);
        generalSettings.setContact_email(generalSettingsDto.getContact_email());
        generalSettings.setContact_phone(generalSettingsDto.getContact_phone());
        generalSettings.setInsurance_price(generalSettingsDto.getInsurance_price());
        generalSettings.setPromotion_threshold(generalSettingsDto.getPromotion_threshold());
        generalSettings.setPromotion_discount(generalSettingsDto.getPromotion_discount());
        generalSettingsRepository.save(generalSettings);
    }
}
