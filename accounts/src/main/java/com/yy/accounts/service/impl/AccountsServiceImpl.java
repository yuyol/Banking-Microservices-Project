package com.yy.accounts.service.impl;

import com.yy.accounts.constants.AccountsConstants;
import com.yy.accounts.dto.AccountsDto;
import com.yy.accounts.dto.CustomerDto;
import com.yy.accounts.entity.Accounts;
import com.yy.accounts.entity.Customer;
import com.yy.accounts.exception.CustomerAlreadyExistsException;
import com.yy.accounts.exception.ResourceNotFountException;
import com.yy.accounts.mapper.AccountsMapper;
import com.yy.accounts.mapper.CustomerMapper;
import com.yy.accounts.repository.AccountsRepository;
import com.yy.accounts.repository.CustomerRepository;
import com.yy.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * Create an account
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        // save the customer info into customer database
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        // find by mobile number, check if already existed
        Optional<Customer> MobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(MobileNumber.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerDto.getMobileNumber());
        }

        // save into database
        Customer savedCustomer =  customerRepository.save(customer); // Spring Data JPA taking input object and prepare SQL


        // Create Account by Customer
        Accounts newAccount = createNewAccount(savedCustomer);
        accountsRepository.save(newAccount);
    }

    /**
     * initialize the account information
     * @param customer - customer object contains customer info
     * @return - accounts object
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    /**
     * Fetch the account details by mobile number
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFountException("Customer","mobile number",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFountException("Account","customer id",customer.getCustomerId().toString())
        );

        // convert the bean into DTO type
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        // combining the account data with customer data
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;

    }

    /**
     * Update account info
     * @param customerDto - customer details
     * @return - flag
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountsDto newAccountsDto = customerDto.getAccountsDto();

        if(newAccountsDto != null) {
            // update account info
            Accounts accounts = accountsRepository.findById(newAccountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFountException("Account","Account Number",newAccountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(newAccountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            // update customer info
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFountException("Customer","Customer ID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * Delete account
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFountException("Customer", "mobile number", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }


}
