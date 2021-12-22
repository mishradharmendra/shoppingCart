package com.cg.shopping.profileservice.service;

import com.cg.shopping.profileservice.dao.UserProfileRepository;
import com.cg.shopping.profileservice.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile addNewUserProfile(UserProfile userProfile) {
        userProfile.setProfileId(getNextId());
        return userProfileRepository.save(userProfile);
    }

    public UserProfile login(Map<String, String> userProfile) {
        return userProfileRepository.findByEmailAndPassword(userProfile.get("email"), userProfile.get("password"))
                .orElse(UserProfile.builder().build());
    }
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }
    public UserProfile getByProfileId(String profileId) {
        return userProfileRepository.findById(profileId).orElse(UserProfile.builder().build());
    }

    public UserProfile getByMobileNumber(long mobileNumber) {
        return userProfileRepository.findByMobileNumber(mobileNumber).orElse(UserProfile.builder().build());
    }

    public UserProfile getByUserName(String fullName) {
        return userProfileRepository.findByFullName(fullName).orElse(UserProfile.builder().build());
    }

    public void updateUserProfile(UserProfile userProfile) {
        Optional<UserProfile> byProfileId = userProfileRepository.findByProfileId(userProfile.getProfileId());
        if (byProfileId.isPresent()) {
            userProfile.set_id(byProfileId.get().get_id());
            userProfileRepository.save(userProfile);
        }
    }

    public void deleteUserProfile(int profileId) {
        Optional<UserProfile> byProfileId = userProfileRepository.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            userProfileRepository.delete(byProfileId.get());
        }
    }

    @Synchronized
    public int getNextId() {
        UserProfile profile = userProfileRepository.findTopByOrderByProfileIdDesc();
        int id = (profile != null) ? profile.getProfileId() : 0;
        return ++id;
    }

    public boolean existsByEmail(String email) {
        return userProfileRepository.existsByEmail(email);
    }
}
