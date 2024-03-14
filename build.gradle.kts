plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.telegrambot"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    //developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    //runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // mapstruct
    implementation("org.mapstruct:mapstruct:1.5.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.0.Beta1")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.0.Beta1")
    //telegram
    implementation("org.telegram:telegrambots-spring-boot-starter:6.7.0")
    implementation("org.telegram:telegrambots-abilities:6.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
