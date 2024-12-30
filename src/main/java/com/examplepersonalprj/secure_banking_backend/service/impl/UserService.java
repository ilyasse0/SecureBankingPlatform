package com.examplepersonalprj.secure_banking_backend.service.impl;

import com.examplepersonalprj.secure_banking_backend.dto.BankResponse;
import com.examplepersonalprj.secure_banking_backend.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);


}
