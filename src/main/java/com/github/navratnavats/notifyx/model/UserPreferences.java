package com.github.navratnavats.notifyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_preferences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences {

    @Id
    private String id;
    private List<String> preferredChannels;
    private List<String> mutedPreferences;

    @Override
    public String toString() {
        return "UserPreferences{" +
                "id='" + id + '\'' +
                ", preferredChannels=" + preferredChannels +
                ", mutedPreferences=" + mutedPreferences +
                '}';
    }
}
