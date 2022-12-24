package com.microservices.demo.twitter.to.kafka.service.runner.impl

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData
import com.microservices.demo.twitter.to.kafka.service.exception.TwitterToKafkaServiceException
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import twitter4j.Status
import twitter4j.TwitterObjectFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Random
import java.util.concurrent.ThreadLocalRandom

class MockKafkaStreamRunner : StreamRunner {

    private var log: Logger = LoggerFactory.getLogger(MockKafkaStreamRunner::class.java)

    @Autowired
    private lateinit var twitterToKafkaServiceConfigData: TwitterToKafkaServiceConfigData

    @Autowired
    private lateinit var twitterKafkaStatusListener: TwitterKafkaStatusListener

    private var random: Random = Random()

    private var words: Array<String> = arrayOf(
        "Lorem",
        "ipsum",
        "dolor",
        "sit",
        "amet",
        "consectetur",
        "adipiscing",
        "elit",
        "Nulla",
        "gravida",
        "tellus",
        "nisi",
        "ut",
        "ullamcorper",
        "arcu",
        "blandit",
        "quis",
        "Curabitur",
        "varius",
        "egestas"
    )

    private var tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":\"{3}\"," +
            "}"
    private var TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy"

    override fun start() {
        val keywords: List<String> = twitterToKafkaServiceConfigData.twitterKeywords
        var minTweetLength = twitterToKafkaServiceConfigData.mockMinTweetLength
        var maxTweetLength = twitterToKafkaServiceConfigData.mockMaxTweetLength
        var sleepTimeMs = twitterToKafkaServiceConfigData.mockSleepMs

        log.info("Started filtering twitter Stream for keywords {}", keywords.toString())

        while (true) {
            var formattedTweetAsJson = getFormattedTweet(keywords, minTweetLength, maxTweetLength)
            var status: Status = TwitterObjectFactory.createStatus(formattedTweetAsJson)

            sleep(sleepTimeMs)

            twitterKafkaStatusListener.onStatus(status)

        }
    }

    private fun sleep(sleepTimeMs: Long) {
        try {
            Thread.sleep(sleepTimeMs)
        } catch (e: InterruptedException) {
            throw TwitterToKafkaServiceException("상태 생성 중 오류")
        }
    }

    private fun getFormattedTweet(keywords: List<String>, minTweetLength: Int, maxTweetLength: Int) {
        val params = arrayOf(
            ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT)),
            ThreadLocalRandom.current().nextLong(Long.MAX_VALUE),
            getRandomTweetContent(keywords, minTweetLength, maxTweetLength),
            ThreadLocalRandom.current().nextLong(Long.MAX_VALUE),
        )
        var tweet: String = tweetAsRawJson

        for(i in 0 until params.size) {
            tweet = tweet.replace()
        }
    }

    private fun getRandomTweetContent(keywords: List<String>, minTweetLength: Int, maxTweetLength: Int): Comparable<*> {

    }
}