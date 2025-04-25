package com.example.miniapp.repositories;

import com.example.miniapp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Retrieve customers whose email ends with the specified domain.
     */
    List<Customer> findByEmailEndingWith(String domain);

    /**
     * Retrieve customers whose phone number starts with the specified prefix.
     */
    List<Customer> findByPhoneNumberStartingWith(String prefix);
}