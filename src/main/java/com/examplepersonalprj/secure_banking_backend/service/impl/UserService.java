package com.examplepersonalprj.secure_banking_backend.service.impl;

import com.examplepersonalprj.secure_banking_backend.dto.BankResponse;
import com.examplepersonalprj.secure_banking_backend.dto.CreditDebitRequest;
import com.examplepersonalprj.secure_banking_backend.dto.EnquiryRequest;
import com.examplepersonalprj.secure_banking_backend.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balenceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);


}
