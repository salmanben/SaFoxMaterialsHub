package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.StripeSettingsDto;
import com.example.demo.model.StripeSettings;
import com.example.demo.repository.StripeSettingsRepository;
import java.util.List;

@Service
public class StripeSettingsService {

    private final StripeSettingsRepository StripeSettingsRepository;

    public StripeSettingsService(StripeSettingsRepository StripeSettingsRepository) {
        this.StripeSettingsRepository = StripeSettingsRepository;
    }

    public StripeSettingsDto getStripeSettings() {
        List<StripeSettings> ListStripeSettings = StripeSettingsRepository.findAll();
        if (ListStripeSettings.size() == 0)
            return new StripeSettingsDto("", "", "");
        StripeSettings StripeSettings = ListStripeSettings.get(0);
        return new StripeSettingsDto(StripeSettings.getClientId(), StripeSettings.getClientSecret(),
                StripeSettings.getMode());
    }

    public void updateStripeSettings(StripeSettingsDto StripeSettingsDto) {
        List<StripeSettings> ListStripeSettings = StripeSettingsRepository.findAll();
        if (ListStripeSettings.size() == 0) {
            StripeSettings StripeSettings = new StripeSettings();
            StripeSettings.setClientId(StripeSettingsDto.getClientId());
            StripeSettings.setClientSecret(StripeSettingsDto.getClientSecret());
            StripeSettings.setMode(StripeSettingsDto.getMode());
            StripeSettingsRepository.save(StripeSettings);
            return;
        }
        StripeSettings StripeSettings = StripeSettingsRepository.findAll().get(0);
        StripeSettings.setClientId(StripeSettingsDto.getClientId());
        StripeSettings.setClientSecret(StripeSettingsDto.getClientSecret());
        StripeSettings.setMode(StripeSettingsDto.getMode());
        StripeSettingsRepository.save(StripeSettings);

    }
}
