import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.github.brunotorrao"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.data:spring-data-r2dbc:1.3.5")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
  implementation("io.arrow-kt:arrow-core:1.0.0")
  implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
  implementation("io.r2dbc:r2dbc-postgresql")
  implementation("io.springfox:springfox-boot-starter:3.0.0")
  implementation("io.springfox:springfox-swagger-ui:3.0.0")
  implementation("org.springframework.boot:spring-boot-starter-cache")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.flywaydb:flyway-core")
  implementation("org.postgresql:postgresql")
  testImplementation("it.ozimov:embedded-redis:0.7.1")
  testImplementation("org.testcontainers:junit-jupiter:1.16.0")
  testImplementation("org.testcontainers:postgresql:1.16.0")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "mockito-core")
  }
  testImplementation("com.ninja-squad:springmockk:3.0.1")
	testImplementation("io.projectreactor:reactor-test")
  testImplementation(  "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
  testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.0.4")
  testImplementation("com.github.tomakehurst:wiremock-jre8:2.31.0")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
