package com.microservices.webfllux

data class Customer(var id: Int = 0, val name: String = "", val telephone: Telephone?= null)
data class Telephone(var contryCode: String = "", var phoneNumber: String = "")
