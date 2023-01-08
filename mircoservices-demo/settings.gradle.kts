pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "mircoservices-demo"
include("twitter-to-kafka-service")
include("app-config-data")
include("kafka")
include("kafka-model")
include("kafka-admin")
include("kafka-producer")
