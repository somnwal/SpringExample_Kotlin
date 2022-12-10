package com.microservices.webfllux

import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl: CustomerService {
    companion object {
        val initialCustomers = arrayOf(
            Customer(1, "Kotlin"),
            Customer(1, "Kotlin", Telephone("+82", "01012341234")),
            Customer(1, "Kotlin"),
        )
    }

    override fun getCustomer(id: Int): Customer? {
        TODO("Not yet implemented")
    }

    override fun searchCustomers(nameFilter: String): List<Customer> {
        TODO("Not yet implemented")
    }

}