plugins {
    application
    id("io.freefair.lombok") version "9.8.0"
//    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.gradleup.shadow") version "8.3.11"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.5.250")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("gg.jte:jte:3.2.4")
    implementation("io.javalin:javalin-rendering-jte:7.2.3")
    implementation("io.javalin:javalin:7.2.3")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("com.konghq:unirest-java:3.14.5")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.assertj:assertj-core:3.25.1")
    testImplementation("io.javalin:javalin-testtools:6.1.4")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}