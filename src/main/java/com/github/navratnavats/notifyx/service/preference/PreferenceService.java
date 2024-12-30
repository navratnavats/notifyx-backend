package com.github.navratnavats.notifyx.service.preference;

import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.model.UserPreferences;
import com.github.navratnavats.notifyx.repository.UserPreferencesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PreferenceService {

    @Autowired
    UserPreferencesRepository preferencesRepository;

    public UserPreferencesDto savePreferences(UserPreferencesDto preferencesDto) {
        log.info("Saving user preferences: userId={}", preferencesDto.getUserId());
        UserPreferences preferences = new UserPreferences();
        preferences.setPreferredChannels(preferencesDto.getPreferredChannels());
        preferences.setUserId(preferencesDto.getUserId());
        preferences.setMutedPreferences(preferencesDto.getMutedPreferences());

        UserPreferences savedPreference = preferencesRepository.save(preferences);
        log.info("Preferences saved: userId={}", preferencesDto.getUserId());
        return toDTO(savedPreference);
    }

    public Optional<UserPreferencesDto> getUserPreference(String userId){
        Optional<UserPreferences> userPreferences = preferencesRepository.findById(userId);
        return userPreferences.map(this::toDTO);
    }

    public UserPreferencesDto editPreferences(String userId, UserPreferencesDto preferencesDto){
        Optional<UserPreferences> userPreferencesOptional = preferencesRepository.findById(userId);
        if(userPreferencesOptional.isPresent()){
            UserPreferences userPreferences = new UserPreferences();

            userPreferences.setUserId(userId);
            userPreferences.setPreferredChannels(preferencesDto.getPreferredChannels());
            userPreferences.setMutedPreferences(preferencesDto.getMutedPreferences());

            UserPreferences savedPreference = preferencesRepository.save(userPreferences);
            return toDTO(savedPreference);

        }
        return null;

    }

    private UserPreferencesDto toDTO(UserPreferences userPreferences) {
        UserPreferencesDto preferencesDto = new UserPreferencesDto();

        preferencesDto.setUserId(userPreferences.getUserId());
        preferencesDto.setMutedPreferences(userPreferences.getMutedPreferences());
        preferencesDto.setPreferredChannels(userPreferences.getPreferredChannels());

        return preferencesDto;
    }

    public List<String> createDefaultPreferences(String userId) {
        UserPreferencesDto defaultUserPreference = new UserPreferencesDto();
        defaultUserPreference.setUserId(userId);
        defaultUserPreference.setMutedPreferences(List.of());
        defaultUserPreference.setPreferredChannels(Arrays.asList("WEB_PUSH", "WEBSOCKET", "EMAIL"));

        savePreferences(defaultUserPreference);
        log.info("Default preferences created for userId={}", userId);
        return defaultUserPreference.getPreferredChannels();
    }
}
