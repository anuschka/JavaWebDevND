package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
}
