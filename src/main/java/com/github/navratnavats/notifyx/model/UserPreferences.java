package com.github.navratnavats.notifyx.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_preferences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreferences {

    @Id
    private String userId;
    private List<String> preferredChannels;
    private List<String> mutedPreferences;

    @Override
    public String toString() {
        return "UserPreferences{" +
                "id='" + userId + '\'' +
                ", preferredChannels=" + preferredChannels +
                ", mutedPreferences=" + mutedPreferences +
                '}';
    }
}
