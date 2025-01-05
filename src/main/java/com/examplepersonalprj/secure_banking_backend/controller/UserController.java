package com.examplepersonalprj.secure_banking_backend.controller;

import com.examplepersonalprj.secure_banking_backend.dto.BankResponse;
import com.examplepersonalprj.secure_banking_backend.dto.CreditDebitRequest;
import com.examplepersonalprj.secure_banking_backend.dto.EnquiryRequest;
import com.examplepersonalprj.secure_banking_backend.dto.UserRequest;
import com.examplepersonalprj.secure_banking_backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @GetMapping("balanceEnquiry")
    public BankResponse BalanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return  userService.balenceEnquiry(enquiryRequest);
    }

    @GetMapping("nameEnquiry")
    public String getNameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return  userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest  request) {
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponse debitAccount (@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }
}

