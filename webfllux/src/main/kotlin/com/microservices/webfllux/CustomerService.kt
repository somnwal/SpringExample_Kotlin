package com.microservices.webfllux

import org.springframework.stereotype.Service

interface CustomerService {
    fun getCustomer(id: Int): Customer?
    fun searchCustomers(nameFilter: String): List<Customer>
}