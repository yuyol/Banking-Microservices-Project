package com.yy.accounts.service.impl;

import com.yy.accounts.dto.AccountsDto;
import com.yy.accounts.dto.CardsDto;
import com.yy.accounts.dto.CustomerDetailsDto;
import com.yy.accounts.dto.LoansDto;
import com.yy.accounts.entity.Accounts;
import com.yy.accounts.entity.Customer;
import com.yy.accounts.exception.ResourceNotFountException;
import com.yy.accounts.mapper.AccountsMapper;
import com.yy.accounts.mapper.CustomerMapper;
import com.yy.accounts.repository.AccountsRepository;
import com.yy.accounts.repository.CustomerRepository;
import com.yy.accounts.service.ICustomersService;
import com.yy.accounts.service.client.CardsFeignClient;
import com.yy.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     *
     * @param mobileNumber Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFountException("Customer","mobile number",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFountException("Account","customer id",customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
