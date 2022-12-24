package com.microservices.demo.twitter.to.kafka.service.runner.impl

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData
import com.microservices.demo.twitter.to.kafka.service.exception.TwitterToKafkaServiceException
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import twitter4j.Status
import twitter4j.TwitterException
import twitter4j.TwitterObjectFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadLocalRandom

@Component
@ConditionalOnProperty(name = ["twitter-to-kafka-service.enable-mock-tweets"], havingValue = "true")
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

    private var tweetAsRawJson =
        "{" + "\"created_at\":\"{0}\"," + "\"id\":\"{1}\"," + "\"text\":\"{2}\"," + "\"user\":{\"id\":\"{3}\"}" + "}";
    private var TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy"

    override fun start() {
        val keywords: List<String> = twitterToKafkaServiceConfigData.twitterKeywords
        val minTweetLength = twitterToKafkaServiceConfigData.mockMinTweetLength
        val maxTweetLength = twitterToKafkaServiceConfigData.mockMaxTweetLength
        val sleepTimeMs = twitterToKafkaServiceConfigData.mockSleepMs

        log.info("Started filtering twitter Stream for keywords {}", keywords.toString())

        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs)
    }

    private fun simulateTwitterStream(
        keywords: List<String>, minTweetLength: Int, maxTweetLength: Int, sleepTimeMs: Long
    ) {
        Executors.newSingleThreadExecutor().submit {
            try {
                while (true) {
                    var formattedTweetAsJson = getFormattedTweet(keywords, minTweetLength, maxTweetLength)

                    log.info(formattedTweetAsJson)
                    var status: Status = TwitterObjectFactory.createStatus(formattedTweetAsJson)

                    twitterKafkaStatusListener.onStatus(status)
                    sleep(sleepTimeMs)
                }
            } catch (e: TwitterException) {
                log.error("오류났다!", e)
            }

        }

    }

    private fun sleep(sleepTimeMs: Long) {
        try {
            Thread.sleep(sleepTimeMs)
        } catch (e: InterruptedException) {
            throw TwitterToKafkaServiceException("상태 생성 중 오류")
        }
    }

    private fun getFormattedTweet(keywords: List<String>, minTweetLength: Int, maxTweetLength: Int): String {
        val params = arrayOf<String>(
            ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
            ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(),
            getRandomTweetContent(keywords, minTweetLength, maxTweetLength),
            ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString()
        )
        return formatTwitterAsJsonWithParams(params)
    }

    private fun formatTwitterAsJsonWithParams(params: Array<String>): String {
        var tweet: String = tweetAsRawJson

        for (i in params.indices) {
            tweet = tweet.replace("{$i}", params[i])
        }

        return tweet
    }

    private fun getRandomTweetContent(keywords: List<String>, minTweetLength: Int, maxTweetLength: Int): String {
        val tweet = StringBuilder()
        val tweetLength = random.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength

        return constructRandomTweet(tweetLength, tweet, keywords)
    }

    private fun constructRandomTweet(
        tweetLength: Int, tweet: StringBuilder, keywords: List<String>
    ): String {
        for (i in 0 until tweetLength) {
            tweet.append(words[random.nextInt(words.size)]).append(" ")

            if (i == tweetLength / 2) {
                tweet.append(keywords[random.nextInt(keywords.size)]).append(" ")
            }
        }

        return tweet.toString().trim()
    }
}