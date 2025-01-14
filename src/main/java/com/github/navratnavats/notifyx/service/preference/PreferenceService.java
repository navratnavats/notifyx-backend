package com.github.navratnavats.notifyx.service.preference;

import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.model.UserPreferences;
import com.github.navratnavats.notifyx.repository.UserPreferencesRepository;
import com.github.navratnavats.notifyx.utils.NotificationTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.navratnavats.notifyx.utils.NotificationTransformer.toUserPreferencesDTO;

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
        return toUserPreferencesDTO(savedPreference);
    }

    public Optional<UserPreferencesDto> getUserPreference(String userId){
        Optional<UserPreferences> userPreferences = preferencesRepository.findById(userId);
        return userPreferences.map(NotificationTransformer::toUserPreferencesDTO);
    }

    public UserPreferencesDto editPreferences(String userId, UserPreferencesDto preferencesDto){
        Optional<UserPreferences> userPreferencesOptional = preferencesRepository.findById(userId);
        if(userPreferencesOptional.isPresent()){
            UserPreferences userPreferences = UserPreferences.builder()
                    .userId(userId)
                    .preferredChannels(preferencesDto.getPreferredChannels())
                    .mutedPreferences(preferencesDto.getMutedPreferences()).build();

            UserPreferences savedPreference = preferencesRepository.save(userPreferences);
            return toUserPreferencesDTO(savedPreference);
        }
        return null;
    }

    public List<String> createDefaultPreferences(String userId) {
        UserPreferencesDto defaultUserPreference =UserPreferencesDto.builder()
                .preferredChannels(Arrays.asList("WEB_PUSH", "WEBSOCKET", "EMAIL"))
                .userId(userId)
                .mutedPreferences(List.of())
                .build();

        savePreferences(defaultUserPreference);
        log.info("Default preferences created for userId={}", userId);
        return defaultUserPreference.getPreferredChannels();
    }
}
