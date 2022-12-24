package com.microservices.demo.twitter.to.kafka.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.sql.DriverManager.println

import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner
import com.mircoservices.demo.config.TwitterToKafkaServiceConfigData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(TwitterToKafkaServiceConfigData::class)
@ComponentScan(basePackages = ["com.microservices.demo"])
class TwitterToKafkaServiceApplication : CommandLineRunner {

    @Autowired
    private lateinit var twitterToKafkaServiceConfigData: TwitterToKafkaServiceConfigData
    @Autowired
    private lateinit var streamRunner: StreamRunner

    private var log: Logger = LoggerFactory.getLogger(TwitterToKafkaServiceApplication::class.java)

    override fun run(vararg args: String?) {
        println("App Starts")
        log.info(twitterToKafkaServiceConfigData.twitterKeywords.toString())
        log.info(twitterToKafkaServiceConfigData.welcomeMessage)

        streamRunner.start()
    }
}

fun main(args: Array<String>) {
    runApplication<TwitterToKafkaServiceApplication>(*args)

}

