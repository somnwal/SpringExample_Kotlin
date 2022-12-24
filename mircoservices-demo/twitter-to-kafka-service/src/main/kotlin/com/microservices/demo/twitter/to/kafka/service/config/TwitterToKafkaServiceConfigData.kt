package com.microservices.demo.twitter.to.kafka.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
data class TwitterToKafkaServiceConfigData (
    var twitterKeywords: List<String>,
    var welcomeMessage: String
)