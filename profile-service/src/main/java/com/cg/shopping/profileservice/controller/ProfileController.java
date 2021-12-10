package com.cg.shopping.profileservice.controller;

import com.cg.shopping.profileservice.entity.UserProfile;
import com.cg.shopping.profileservice.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(value = "/createNewCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> addNewCustomer(@RequestBody UserProfile userProfile) {
        userProfile.setRole("ROLE_USER");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.addNewUserProfile(userProfile));
    }

    @PostMapping(value = "/createNewMerchant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> addNewMerchant(@RequestBody UserProfile userProfile) {
        userProfile.setRole("USER_MERCHANT");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.addNewUserProfile(userProfile));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> login(@RequestBody Map<String, String> userProfile) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfileService.login(userProfile));
    }

    @PostMapping(value = "/createNewDeliveryProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> addNewDeliveryProfile(@RequestBody UserProfile userProfile) {
        userProfile.setRole("ROLE_MERCHANT");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.addNewUserProfile(userProfile));
    }
    @GetMapping(value = "/allUserProfiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
        return ResponseEntity.ok(userProfileService.getAllUserProfiles());
    }


    @GetMapping(value = "/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getById(@RequestParam (value = "profileId") int profileId ) {
        return ResponseEntity.ok(userProfileService.getByProfileId(profileId));
    }

    @GetMapping(value = "/mobile/{mobilePhone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getAllByMobilePhone(@RequestParam (value = "mobilePhone") long mobilePhone ) {
        return ResponseEntity.ok(userProfileService.getByMobileNumber(mobilePhone));
    }

    @GetMapping(value = "/username/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getByName(@RequestParam (value = "userName") String userName ) {
        return ResponseEntity.ok(userProfileService.getByUserName(userName));
    }

    @PutMapping(value = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProfile(@RequestBody UserProfile userProfile) {
        userProfileService.updateUserProfile(userProfile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{profileId}")
    public ResponseEntity<Void> deleteProfile(@RequestParam(value = "profileId") int profileId) {
        userProfileService.deleteUserProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
