package com.examplepersonalprj.secure_banking_backend.service.impl;

import com.examplepersonalprj.secure_banking_backend.dto.*;
import com.examplepersonalprj.secure_banking_backend.entity.User;
import com.examplepersonalprj.secure_banking_backend.repository.UserRepository;
import com.examplepersonalprj.secure_banking_backend.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

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
                .messageBody("Congratulation ! your Acount has been Successfully Created.\n Your Acount Details :" + " " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\n Your Account Number :" + " " + savedUser.getAccountNumber())
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

    @Override
    public BankResponse balenceEnquiry(EnquiryRequest enquiryRequest) {
        // first we cheeck if the account number existe or not!
        if (!userRepository.existsByaccountNumber(enquiryRequest.getAccountNumber())) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTE_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTE_CODE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_EXISTE_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_EXISTE_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalence(foundUser.getAccountBalence())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUser.getLastName() + " " + foundUser.getFirstName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        if (!userRepository.existsByaccountNumber(enquiryRequest.getAccountNumber())) {
            return AccountUtils.ACCOUNT_NOT_EXISTE_MESSAGE + "CODE ERROR :" + AccountUtils.ACCOUNT_NOT_EXISTE_CODE;

        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        // check if the account exist
        if(!userRepository.existsByaccountNumber(creditDebitRequest.getAccount())){
            //System.out.println(creditDebitRequest.getAccount());
            //  System.out.println("here");
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTE_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTE_CODE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccount());
        System.out.println(userToCredit.toString());
        userToCredit.setAccountBalence(userToCredit.getAccountBalence().add(creditDebitRequest.getAmount()));
        userRepository.save(userToCredit);
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_EXISTE_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_EXISTE_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalence(userToCredit.getAccountBalence())
                        .accountName(userToCredit.getLastName() +userToCredit.getFirstName())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build()
                )
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        //cheeking if the account exist
        if (!userRepository.existsByaccountNumber(creditDebitRequest.getAccount())) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTE_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTE_CODE)
                    .accountInfo(null)
                    .build();
        }
        User userDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccount());
        BigInteger availbleBalence = userDebit.getAccountBalence().toBigInteger();
        BigInteger debitAmount = creditDebitRequest.getAmount().toBigInteger();
        if( availbleBalence.intValue() < debitAmount.intValue()){
                return BankResponse.builder()
                        .responseMessage(AccountUtils.INSUFFUCIENT_BALANCE_MESSAGE)
                        .responseCode(AccountUtils.INSUFFUCIENT_BALANCE_CODE)
                        .accountInfo(null)
                        .build();
        }
        userDebit.setAccountBalence(userDebit.getAccountBalence().subtract(creditDebitRequest.getAmount()));
        userRepository.save(userDebit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MASSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(creditDebitRequest.getAccount())
                        .accountName(userDebit.getLastName() +userDebit.getFirstName())
                        .accountBalence(userDebit.getAccountBalence())
                        .build())
                .build();
    }
}
