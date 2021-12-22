package com.cg.shopping.profileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "userProfile")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserProfile {
    @Id
    private String _id;

    private int profileId;
    private String fullName;
    private String image;
    private String email;
    private long mobileNumber;
    private String about;
    private LocalDate dateOfBirth;
    private String gender;
    private String role;
    private String password;
    private Boolean isAdmin;

    private Address address;
}
