package com.github.navratnavats.notifyx.controller;

import com.github.navratnavats.notifyx.dto.UserPreferencesDto;
import com.github.navratnavats.notifyx.service.preference.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @PutMapping("/change/{userId}")
    public ResponseEntity<?> changePreference(@PathVariable String userId, @RequestBody UserPreferencesDto preferencesDto){
        UserPreferencesDto userPreferencesDto = preferenceService.editPreferences(userId, preferencesDto);
        return userPreferencesDto == null ? new ResponseEntity<>("UserId not found", HttpStatus.NOT_FOUND) : new ResponseEntity<>(userPreferencesDto, HttpStatus.CREATED);
    }

    @GetMapping("/get-user-preference/{userId}")
    public ResponseEntity<?> getUserPreference(@PathVariable String userId){

        UserPreferencesDto userPreferences = preferenceService.getUserPreference(userId).isPresent()
                ? preferenceService.getUserPreference(userId).get() : null;
        return userPreferences == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(userPreferences , HttpStatus.OK);
    }
}
