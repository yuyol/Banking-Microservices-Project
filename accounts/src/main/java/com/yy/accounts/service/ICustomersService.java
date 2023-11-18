package com.yy.accounts.service;

import com.yy.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     *
     * @param mobileNumber Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
