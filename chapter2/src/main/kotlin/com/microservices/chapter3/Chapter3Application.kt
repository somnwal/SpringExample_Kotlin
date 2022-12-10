package com.microservices.chapter3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Chapter2Application

@RestController
class CustomerController {
	@RequestMapping(value=["/customer"], method=[RequestMethod.GET])
	@ResponseBody
	fun getCustomer() = Customer(1, "Kotlin", Telephone("80", "01099999"))
}

fun main(args: Array<String>) {
	runApplication<Chapter2Application>(*args)
}