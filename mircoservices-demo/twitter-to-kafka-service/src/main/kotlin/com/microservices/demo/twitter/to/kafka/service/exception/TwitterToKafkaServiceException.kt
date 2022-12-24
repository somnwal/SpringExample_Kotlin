package com.microservices.demo.twitter.to.kafka.service.exception

class TwitterToKafkaServiceException : RuntimeException {

    constructor() : super() {}
    constructor(message: String) : super(message) {}
    constructor(message: String, cause: Throwable) : super(message, cause) {}
}