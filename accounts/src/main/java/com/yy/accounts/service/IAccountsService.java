package com.yy.accounts.service;

import com.yy.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create an account
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetch the account details by mobile number
     * @param mobileNumber
     * @return
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Update account information
     * @param customerDto - customer details
     * @return - boolean flag to indicate if the update of account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Delete account
     * @param mobileNumber
     * @return
     */
    boolean deleteAccount(String mobileNumber);
}
