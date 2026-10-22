plugins {
    application
    id("io.freefair.lombok") version "9.8.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("gg.jte:jte:3.2.4")
    implementation("io.javalin:javalin-rendering-jte:7.2.3")
    implementation("io.javalin:javalin:7.2.3")
    implementation("io.javalin:javalin-rendering:6.1.3")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}