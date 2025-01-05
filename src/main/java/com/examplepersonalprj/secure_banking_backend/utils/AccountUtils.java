package com.examplepersonalprj.secure_banking_backend.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
    /**
     *
     * the algorithme to create account number :
     *          THE YEAR + random 6 digits
     *  note : we can change later the the first 2 number based on the region !!!!!
     *
     */
    public static String createAccountNumber() {
        Year currentYear = Year.now();

        // Use Random class for better randomness
        Random random = new Random();
        int min = 1000000;
        int max = 9999999; // Make sure max is greater than min
        int randomNumber = random.nextInt(max - min + 1) + min;

        String year = String.valueOf(currentYear.getValue());
        String randomnumber = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomnumber).toString();
    }


    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MESSAGE="This account already exists!";

    public static final String ACCOUNT_CREATION_SECCESS_CODE="105";
    public static final String ACCOUNT_CREATION_SECCESS_MESSAGE="ACCOUNT HAS BEEN CREATED SECCESSFULLY! THANK YOU";


    public static final String ACCOUNT_EXISTE_CODE="106";
    public static final String ACCOUNT_EXISTE_MESSAGE="USER ACCOUNT FOUND";


    public static final String ACCOUNT_NOT_EXISTE_CODE="404";
    public static final String ACCOUNT_NOT_EXISTE_MESSAGE ="NO USER WWITH THIS ACCOUNT NUMBER FOUND";


    public static final String INSUFFUCIENT_BALANCE_CODE="107   ";
    public static final String INSUFFUCIENT_BALANCE_MESSAGE="OPERATION FAILD__INSUFFUCIENT BALANCE ";

    public static final String ACCOUNT_DEBITED_SUCCESS_CODE="108";
    public static final String ACCOUNT_DEBITED_SUCCESS_MASSAGE="OPERATION FAILD__INSUFFUCIENT BALANCE";

}
