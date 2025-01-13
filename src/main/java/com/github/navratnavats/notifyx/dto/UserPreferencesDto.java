package com.github.navratnavats.notifyx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserPreferencesDto {

    private final String userId;
    private final List<String> preferredChannels;
    private final List<String> mutedPreferences;

    @Override
    public String toString() {
        return "UserPreferences{" +
                "userId='" + userId + '\'' +
                ", preferredChannels=" + preferredChannels +
                ", mutedPreferences=" + mutedPreferences +
                '}';
    }
}
