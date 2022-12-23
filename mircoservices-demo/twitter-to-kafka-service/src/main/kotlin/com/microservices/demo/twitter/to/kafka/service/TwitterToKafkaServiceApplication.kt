package com.microservices.demo.twitter.to.kafka.service

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Scope
import java.sql.DriverManager.println
import javax.annotation.PostConstruct

@SpringBootApplication
class TwitterToKafkaServiceApplication: CommandLineRunner {
    fun main(args: Array<String>) {
        runApplication<TwitterToKafkaServiceApplication>(*args)

    }

    override fun run(vararg args: String?) {
        println("App Starts")
    }
}

