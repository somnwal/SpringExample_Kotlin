import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
}

plugins {
	id("org.springframework.boot") version "2.7.7" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	kotlin("jvm") version "1.7.21" apply false
	kotlin("plugin.spring") version "1.7.21" apply false
}

allprojects {
	group = "com.microservices.demo"
	version = "1.0.0"

	repositories {
		mavenCentral()
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
}

subprojects {
	repositories {
		mavenCentral()
	}

	apply {
		plugin("io.spring.dependency-management")
	}
}