package com.examplepersonalprj.secure_banking_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String adress;
    private String stateOrigin;
    private String email ;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;


}

