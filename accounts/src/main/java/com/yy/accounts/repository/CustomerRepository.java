package com.yy.accounts.repository;

import com.yy.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * find user by mobile number
     * Make sure the word after "findBy" is mentioned in the entity class
     * @param mobileNumber - user's mobile number
     * @return - Optional Object, which accept null value
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
