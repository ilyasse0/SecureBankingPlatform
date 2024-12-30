package com.examplepersonalprj.secure_banking_backend.service.impl;

import com.examplepersonalprj.secure_banking_backend.dto.AccountInfo;
import com.examplepersonalprj.secure_banking_backend.dto.BankResponse;
import com.examplepersonalprj.secure_banking_backend.dto.EmailDetails;
import com.examplepersonalprj.secure_banking_backend.dto.UserRequest;
import com.examplepersonalprj.secure_banking_backend.entity.User;
import com.examplepersonalprj.secure_banking_backend.repository.UserRepository;
import com.examplepersonalprj.secure_banking_backend.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        // Check if the account already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // Create new User
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .stateOrigin(userRequest.getStateOrigin())
                .accountNumber(AccountUtils.createAccountNumber())
                .adress(userRequest.getAdress())
                .accountBalence(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        // Save user into DB
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulation ! your Acount has been Successfully Created.\n Your Acount Details :" +" "+savedUser.getFirstName() +" "+ savedUser.getLastName() +"\n Your Account Number :"+" "+savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

        // Return response with user info
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SECCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SECCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalence(savedUser.getAccountBalence())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }
}
