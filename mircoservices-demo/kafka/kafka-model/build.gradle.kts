plugins {
    kotlin("jvm") version "1.7.21"
    id("com.commercehub.gradle.plugin.avro") version "1.1.0"
}

group = "com.microservices.demo"
version = "1.0.0"

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.commercehub.gradle.plugin:gradle-avro-plugin:1.0.0")
    }
}

sourceCompatibility = 17
targetCompatibility = 17

apply(plugin = "com.commercehub.gradle.plugin.avro")

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.avro:avro:1.10.0")
}