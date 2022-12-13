import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.3.50"

    id("java")
    id("war")

    id("com.github.gmazzo.buildconfig") version "2.0.2"
}

group = "shirates"
version = "2.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.google.code.gson:gson:2.9.0")

    // lombok
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")

    // (exclude)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // AssertJ
    testImplementation("org.assertj:assertj-core:3.23.1")

    // Apache Commons IO
    implementation("commons-io:commons-io:2.11.0")
    testImplementation("commons-io:commons-io:2.11.0")

    // Apache Commons Lang
    implementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("org.apache.commons:commons-lang3:3.12.0")

    // Apache Commons Codec
    implementation("commons-codec:commons-codec:1.15")
    testImplementation("commons-codec:commons-codec:1.15")

    // Apache Commons Validator
    implementation("commons-validator:commons-validator:1.7")
    testImplementation("commons-validator:commons-validator:1.7")

    testImplementation("io.mockk:mockk:1.12.0")
}

/**
 * buildConfig
 *
 * gradle-buildconfig-plugin
 * https://github.com/gmazzo/gradle-buildconfig-plugin
 */
buildConfig {
    buildConfigField("String", "appName", "\"${project.name}\"")
    buildConfigField("String", "version", "\"${project.version}\"")
    buildConfigField("String", "packageName", "\"${project.group}\"")
    buildConfigField("String", "charset", "\"UTF-8\"")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
