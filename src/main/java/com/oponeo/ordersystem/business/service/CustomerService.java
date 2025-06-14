package com.oponeo.ordersystem.business.service;

import com.oponeo.ordersystem.business.domain.Customer;

public interface CustomerService {

    Customer registerCustomer(Customer customer);

    Customer getCustomerById(Long customerId);
}