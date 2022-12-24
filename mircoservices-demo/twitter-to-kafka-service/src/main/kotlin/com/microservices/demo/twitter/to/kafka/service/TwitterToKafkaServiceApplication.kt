package com.microservices.demo.twitter.to.kafka.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.sql.DriverManager.println

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@SpringBootApplication
class TwitterToKafkaServiceApplication: CommandLineRunner {

    @Autowired
    private lateinit var twitterToKafkaServiceConfigData: TwitterToKafkaServiceConfigData
    private var log: Logger = LoggerFactory.getLogger(TwitterToKafkaServiceApplication::class.java)

    override fun run(vararg args: String?) {
        println("App Starts")
        log.info(twitterToKafkaServiceConfigData.twitterKeywords.toString())
        log.info(twitterToKafkaServiceConfigData.welcomeMessage)
    }
}

fun main(args: Array<String>) {
    runApplication<TwitterToKafkaServiceApplication>(*args)

}

