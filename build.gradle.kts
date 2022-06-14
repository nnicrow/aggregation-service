import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.vaadin") version "23.0.8"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "nnicrow"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("com.graphql-java:graphql-java:18.1")
//    implementation("com.graphql-java:graphql-java-spring-boot-starter-webmvc:2021-10-25T04-50-54-fbc162f")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:6.0.1")
    implementation("com.graphql-java-kickstart:graphql-java-tools:5.7.1")
    runtimeOnly("com.graphql-java-kickstart:playground-spring-boot-starter:6.0.1")

    implementation("com.vaadin:vaadin-spring-boot-starter:23.0.8")
    implementation("com.github.mvysny.karibudsl:karibu-dsl-v10:0.7.5")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
