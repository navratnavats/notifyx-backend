package com.github.navratnavats.notifyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesDto {

    private String userId;
    private List<String> preferredChannels;
    private List<String> mutedPreferences;

    @Override
    public String toString() {
        return "UserPreferences{" +
                "userId='" + userId + '\'' +
                ", preferredChannels=" + preferredChannels +
                ", mutedPreferences=" + mutedPreferences +
                '}';
    }
}
