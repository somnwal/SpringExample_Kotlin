package com.microservices.demo.twitter.to.kafka.service.runner.impl

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import twitter4j.FilterQuery
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import javax.annotation.PreDestroy

@Component
@ConditionalOnProperty(name = ["twitter-to-kafka-service.enable-mock-tweets"], havingValue = "false")
class TwitterKafkaStreamRunner : StreamRunner {

    private var log: Logger = LoggerFactory.getLogger(TwitterKafkaStreamRunner::class.java)

    @Autowired
    private lateinit var twitterToKafkaServiceConfigData: TwitterToKafkaServiceConfigData

    @Autowired
    private lateinit var twitterKafkaStatusListener: TwitterKafkaStatusListener

    private lateinit var twitterStream: TwitterStream

    override fun start() {
        twitterStream = TwitterStreamFactory.getSingleton()
        twitterStream.addListener(twitterKafkaStatusListener)

        addFilter()
    }

    @PreDestroy
    fun shutdown() {
        log.info("Closing twitter Stream")
        twitterStream.shutdown()
    }

    private fun addFilter() {
        var keywords: List<String> = twitterToKafkaServiceConfigData.twitterKeywords

        var filterQuery: FilterQuery = FilterQuery(keywords.toString())
        twitterStream.filter(filterQuery)

        log.info("Started filtering twitter Stream for keywords {}", keywords.toString())
    }

}