package com.microservices.webfllux

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body

@Component
class CustomerHandler(val customerService: CustomerService) {
    fun get(req: ServerRequest) = ok().body(customerService.getCustomer(req.pathVariable("id").toInt()))
        .flatMap { ok().body(BodyInserters.fromObject(it)) }.switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun search(req: ServerRequest) =
        ok().body(customerService.searchCustomers(req.queryParam("nameFilter").orElse("")))
}