plugins {
    application
    jacoco
    alias(libs.plugins.lombok)
    alias(libs.plugins.shadow)
    alias(libs.plugins.sonarqube)
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
    implementation(libs.h2)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.hikaricp)
    implementation(libs.jte)
    implementation(libs.javalin)
    implementation(libs.javalin.rendering.jte)
    implementation(libs.unirest.java)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.assertj.core)
    testImplementation(libs.javalin.testtools)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

sonar {
  properties {
    property("sonar.projectKey", "rendleks_java-project-72")
    property("sonar.organization", "rendleks")

    val buildDir = project.layout.buildDirectory.get().asFile
    property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml")
  }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
