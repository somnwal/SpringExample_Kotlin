package com.mircoservices.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "twitter-to-kafka-service")
@ConstructorBinding
data class TwitterToKafkaServiceConfigData(
    val twitterKeywords: List<String>,
    val welcomeMessage: String,
    val enableMockTweets: Boolean,
    val mockSleepMs: Long,
    val mockMinTweetLength: Int,
    val mockMaxTweetLength: Int
)
