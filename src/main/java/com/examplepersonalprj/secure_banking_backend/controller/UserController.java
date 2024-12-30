package com.examplepersonalprj.secure_banking_backend.controller;

import com.examplepersonalprj.secure_banking_backend.dto.BankResponse;
import com.examplepersonalprj.secure_banking_backend.dto.UserRequest;
import com.examplepersonalprj.secure_banking_backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }
}
